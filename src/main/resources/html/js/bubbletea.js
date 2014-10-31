var app = angular.module('bubbletea', ['ngResource', 'ui.bootstrap']);

app.service('LocalBubbleTeaShop', function() {
  var localBubbleTeaShop;

  this.setShop = function(shop) {
    localBubbleTeaShop = shop;
  };

  this.getShop = function() {
    return localBubbleTeaShop;
  }
});s

app.controller('BubbleTeaShopController', function($scope, $window, $resource, LocalBubbleTeaShop) {
  var bubbleTeaShopLocator = $resource('/service/BubbleTeaShop/nearest/:latitude/:longitude',
    {latitude: '@latitude', longitude: '@longitude'}, {});
    window.navigator.geolocation.getCurrentPosition(function (position) {
      bubbleTeaShopLocator.get({latitude: position.coords.latitude, longitude: position.coords.longitude},
        function (value) {
          $scope.nearestBubbleTeaShop = value;
          LocalBubbleTeaShop.setShop(value);
          $scope.googleMapLink = "https://maps.google.com/maps/place/"+$scope.nearestBubbleTeaShop.location.coordinates[1]+","
            +$scope.nearestBubbleTeaShop.location.coordinates[0]
          console.log("link:"+$scope.googleMapLink);
          console.log("location:"+$scope.nearestBubbleTeaShop.location.coordinates[1]+' '+$scope.nearestBubbleTeaShop.location.coordinates[0]);
        });
    });
});

app.controller('BubbleTeaController', function($scope, $resource, LocalBubbleTeaShop) {
  $scope.types = [
    {name: "Honey", family:"Original"},
    {name: "Green Apple", family:"Original"},
    {name: "Lychee", family:"Original"},
    {name: "Mango", family:"Original"}
  ];
  $scope.sizes = ['small', 'medium', 'large'];

  $scope.messages = [];

  $scope.orderBubbleTea = function() {
    $scope.drink.shopId = LocalBubbleTeaShop.getShop().openStreetMapId;
    var order = $resource('/service/BubbleTeaShop/order/');
    order.save($scope.drink, function(acceptedOrder) {
      $scope.messages.push({type: 'success', msg: 'Order sent! Order id:'+acceptedOrder.id});
    });
  }

  $scope.closeAlert = function (index) {
    $scope.messages.splice(index, 1);
  }
});