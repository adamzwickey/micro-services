function ApplicationModel() {
	var self = this;

	self.dataModel = ko.observable(new DataModel());
	self.tempValue = ko.observable();
	self.img = ko.observable("img/sun.png");

	var timer = setInterval(function refresh() {
		console.log("Refreshing Status...");
		$.getJSON("/proxy/caps/a", function (data) {
			if(data.circuitBreaker.toString() === "true") {  //Not sure why this is behaving wierd????
				self.img("img/rain.png");
			} else {
				self.img("img/sun.png");
			}
		})
			.error(function() {
				self.img("img/rain.png");
			});
	}, 2000);

	self.add = function() {
		console.log("Sending message: " + self.tempValue());

		$.getJSON("/proxy/caps/" + self.tempValue(), function(data) {
			console.log("Response: " + JSON.stringify(data));
			self.tempValue("");
			self.dataModel().add(data);
		});
	};
}

function DataModel() {
	var self = this;
	self.rows = ko.observableArray();
	self.add = function(data) {
		self.rows.push(new ResultRow(data));
	};
};

function ResultRow(json) {
	var self = this;
	console.log("Error: " + json.circuitBreaker);
	if(json.circuitBreaker.toString() === "true") {  //Not sure why this is behaving wierd????
		self.icon = ko.observable("<img width=\"40\" height=\"40\" src=\"img/rain.png\">");
	} else {
		self.icon = ko.observable("<img width=\"40\" height=\"40\" src=\"img/sun.png\">");
	}
	self.message = ko.observable(json.message);
};