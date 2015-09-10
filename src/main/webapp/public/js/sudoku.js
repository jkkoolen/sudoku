function Sudoku($scope, $http) {
    $scope.field = {"values":[
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
    $scope.original = angular.copy($scope.field);
    $http.get('/playfields').
        success(function(data) {
            $scope.playfields = data;
        });
    $scope.solve = function() {
        $http.post('/solve', $scope.item).
            success(function(data) {
                $scope.item = data;
            }).
            error(function(data) {
                alert(data.message);
            })
    }
    $scope.add = function() {
        $http.post('/add', $scope.field).
            success(function(data) {
              $scope.playfields = data;
              $scope.field = $scope.original;

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
}


