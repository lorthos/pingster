$(function () {
    $("#submitBtn").click(function () {
            $.post("/schedules", {
                frequency: $.trim($("#frequency").val()),
                address: $.trim($("#address").val())
            }).done(function (data) {
                window.location.reload();
                }).fail(function () {
                	window.location.reload();
                });
        });
});

var Schedule = Backbone.Model.extend({
  "_id":"0",
  "frequency":"0",
  "address":""
});

var Schedules = Backbone.Collection.extend({
  model: Schedule,
  url: "/schedules"
});

var schedules = new Schedules()
var columns = [{
    name: "",
    cell: "select-row",
    headerCell: "select-all",   
  },
  {
  name: "_id", // The key of the model attribute
  label: "ID", // The name to display in the header
  editable: false, // By default every cell in a column is editable, but *ID* shouldn't be
  // Defines a cell type, and ID is displayed as an integer without the ',' separating 1000s.
    cell: "string"
}, {
  name: "frequency",
  label: "frequency",
  editable: false,
  cell: "string" // This is converted to "StringCell" and a corresponding class in the Backgrid package namespace is looked up
}, {
  name: "address",
  label: "address",
  editable: false,
  cell: "string" // This is converted to "StringCell" and a corresponding class in the Backgrid package namespace is looked up
},];

// Initialize a new Grid instance
var grid = new Backgrid.Grid({
  columns: columns,
  collection: schedules
});

// Render the grid and attach the root to your HTML document
$("#schedules").append(grid.render().$el);

// Fetch some countries from the url
schedules.fetch();

$(function () {
    $("#deleteBtn").click(function () {
          grid.getSelectedModels().map(function(item) {
               $.ajax({
                  type: "DELETE",
                  url: "/schedule/"+item.attributes._id,
                  async: false,
                  success: function(result) {
                        console.log("deleted");
                    }
                });
          });
          window.location.reload();
    });
});