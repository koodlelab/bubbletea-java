var app = angular.module('bubbletea', ['ngResource']);

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

  $scope.orderBubbleTea = function() {
    console.log("order...")
    BubbleTeaOrder.save({id: 1}, $scope.drink);
  }
});