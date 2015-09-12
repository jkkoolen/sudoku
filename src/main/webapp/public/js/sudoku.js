var sudokuApp = angular.module('sudokuApp', []);

sudokuApp.controller('Sudoku', function($scope, $http) {
    $scope.initialField = {"values":[
        [0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0],
        [0,0,0,0,0,0,0,0,0]
    ]};
    $scope.clone = angular.copy($scope.initialField);
    $http.get('/playfields').
        success(function(data) {
            $scope.playfields = data;
            $scope.item = data.fields[0];//default selected
        });
    $scope.solve = function(step) {
        $http.post('/solve?step='+step, $scope.item).
            success(function(data) {
                $scope.item = data;
            }).
            error(function(data) {
                alert(data.message);
            })
    }
    $scope.add = function() {
        $http.post('/add', $scope.clone).
            success(function(data) {
              $scope.playfields = data;
              $scope.item = data.fields[0];//default selected
              $scope.clone = angular.copy($scope.initialField);
            })
            .error(function(data) {
                alert(data.message);
            });
    }
    $scope.tabs = [{
        title: 'Read',
        url: '../static/read.html'
    }, {
        title: 'Edit',
        url: '../static/edit.html'
    }];

    $scope.currentTab = '../static/read.html';

    $scope.onClickTab = function (tab) {
        $scope.currentTab = tab.url;
    }

    $scope.isActiveTab = function(tabUrl) {
        return tabUrl == $scope.currentTab;
    }
});


