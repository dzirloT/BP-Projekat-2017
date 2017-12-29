angular.module('go-js', [])
  .directive('goDiagram', function($rootScope) {
    return {
      restrict: 'E',
      template: '<div></div>',  // just an empty DIV element
      replace: true,
      scope: { model: '=goModel' },
      link: function(scope, element, attrs) {

        if (window.goSamples) goSamples(); // init for these samples -- you don't need to call this

        var $ = go.GraphObject.make;

        var lightgrad = $(go.Brush, "Linear", { 1: "#E6E6FA", 0: "#FFFAF0" });

        var itemTempl =
          $(go.Panel, "Horizontal",
            $(go.Shape,
              { desiredSize: new go.Size(5, 5) },
              new go.Binding("figure", "figure"),
              new go.Binding("fill", "color")),
            $(go.TextBlock,
              { stroke: "#333333",
                font: "bold 14px sans-serif" },
              new go.Binding("text", "name"))
          );
        var diagram =  // create a Diagram for the given HTML DIV element
          $(go.Diagram, element[0],
            {
              nodeTemplate:       $(go.Node, "Auto",  // the whole node panel
                      { selectionAdorned: true,
                        resizable: true,
                        layoutConditions: go.Part.LayoutStandard & ~go.Part.LayoutNodeSized,
                        fromSpot: go.Spot.AllSides,
                        toSpot: go.Spot.AllSides,
                        isShadowed: true,
                        shadowColor: "#C5C1AA" },
                      new go.Binding("location", "location").makeTwoWay(),
                      // whenever the PanelExpanderButton changes the visible property of the "LIST" panel,
                      // clear out any desiredSize set by the ResizingTool.
                      new go.Binding("desiredSize", "visible", function(v) { return new go.Size(NaN, NaN); }).ofObject("LIST"),
                      // define the node's outer shape, which will surround the Table
                      $(go.Shape, "Rectangle",
                        { fill: lightgrad, stroke: "#756875", strokeWidth: 3 }),
                      $(go.Panel, "Table",
                        { margin: 8, stretch: go.GraphObject.Fill },
                        $(go.RowColumnDefinition, { row: 0, sizing: go.RowColumnDefinition.None }),
                        // the table header
                        $(go.TextBlock,
                          {
                            row: 0, alignment: go.Spot.Center,
                            margin: new go.Margin(0, 15, 0, 2),  // leave room for Button
                            font: "bold 16px sans-serif"
                          },
                          new go.Binding("text", "key")),
                        // the collapse/expand button
                        $("PanelExpanderButton", "LIST",  // the name of the element whose visibility this button toggles
                          { row: 0, alignment: go.Spot.TopRight }),
                        // the list of Panels, each showing an attribute
                        $(go.Panel, "Vertical",
                          {
                            name: "LIST",
                            row: 1,
                            padding: 3,
                            alignment: go.Spot.TopLeft,
                            defaultAlignment: go.Spot.Left,
                            stretch: go.GraphObject.Horizontal,
                            itemTemplate: itemTempl
                          },
                          new go.Binding("itemArray", "items"))
                      )  // end Table Panel
                    ),  // end Node,
              linkTemplate: $(go.Link,  // the whole link panel
                {
                  selectionAdorned: true,
                  layerName: "Foreground",
                  reshapable: true,
                  routing: go.Link.AvoidsNodes,
                  corner: 5,
                  curve: go.Link.JumpOver
                },
                $(go.Shape,  // the link shape
                  { stroke: "#303B45", strokeWidth: 2.5 }),
                $(go.TextBlock,  // the "from" label
                  {
                    textAlign: "center",
                    font: "bold 14px sans-serif",
                    stroke: "#1967B3",
                    segmentIndex: 0,
                    segmentOffset: new go.Point(NaN, NaN),
                    segmentOrientation: go.Link.OrientUpright
                  },
                  new go.Binding("text", "text")),
                $(go.TextBlock,  // the "to" label
                  {
                    textAlign: "center",
                    font: "bold 14px sans-serif",
                    stroke: "#1967B3",
                    segmentIndex: -1,
                    segmentOffset: new go.Point(NaN, NaN),
                    segmentOrientation: go.Link.OrientUpright
                  },
                  new go.Binding("text", "toText"))
              ),
              initialContentAlignment: go.Spot.Center,
              "ModelChanged": updateAngular,
              "ChangedSelection": updateSelection,
              "undoManager.isEnabled": true
            });
            $rootScope.diagram = diagram
        // whenever a GoJS transaction has finished modifying the model, update all Angular bindings
        function updateAngular(e) {
          if (e.isTransactionFinished) {
            scope.$apply();
          }
        }

        // update the Angular model when the Diagram.selection changes
        function updateSelection(e) {
          diagram.model.selectedNodeData = null;
          var it = diagram.selection.iterator;
          while (it.next()) {
            var selnode = it.value;
            // ignore a selected link or a deleted node
            if (selnode instanceof go.Node && selnode.data !== null) {
              diagram.model.selectedNodeData = selnode.data;
              break;
            }
          }
          scope.$apply();
        }

        // notice when the value of "model" changes: update the Diagram.model
        scope.$watch("model", function(newmodel) {
          var oldmodel = diagram.model;
          if (oldmodel !== newmodel) {
            diagram.removeDiagramListener("ChangedSelection", updateSelection);
            diagram.model = newmodel;
            diagram.addDiagramListener("ChangedSelection", updateSelection);
          }
        });
      }
    };
  });
