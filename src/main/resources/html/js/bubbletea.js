var app = angular.module('bubbletea', ['ngResource', 'ui.bootstrap']);

app.factory('BubbleTeaOrder', function($resource) {
  return $resource('/service/BubbleTeaShop/:id/order/',
    {id: '@bubbleteaShopId'}, {}
  );
});

app.controller('BubbleTeaController', function($scope, BubbleTeaOrder) {
  $scope.types = [
    {name: "Honey", family:"Original"},
    {name: "Green Apple", family:"Original"},
    {name: "Lychee", family:"Original"},
    {name: "Mango", family:"Original"}
  ];
  $scope.sizes = ['small', 'medium', 'large'];

  $scope.messages = [];

  $scope.orderBubbleTea = function() {
    BubbleTeaOrder.save({id: 1}, $scope.drink, function(order) {
      $scope.messages.push({type: 'success', msg: 'Order sent! Order id:'+order.id})
    });
  }

  $scope.closeAlert = function (index) {
    $scope.messages.splice(index, 1);
  }
});