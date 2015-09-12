<!doctype html>
<html ng-app="sudokuApp">
<head>
    <script src="public/angular/angular.min.js"></script>
    <script src="public/js/sudoku.js"></script>
    <link rel="stylesheet" href="public/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="public/css/sudoku.css">
<body>
<h3>
    <pre>I made this project for solving sudoku's
        tooling used:
           * maven
           * spring boot, with controller sending and receiving json (REST)
           * angularjs for presentation and getting posting data to the server
           * resource file containing all unsolved sudoku's
           * common sence for solving sudokus
    </pre>
</h3>
<div ng-controller="Sudoku" class="modal-content">
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" ng-repeat="tab in tabs"
            ng-class="{active:isActiveTab(tab.url)}"
            ng-click="onClickTab(tab)"><a href="\#{{tab.title}}" role="tab" data-toggle="tab">{{tab.title}}</a></li>
    </ul>
    <div id="mainView" class="tab-content">
        <div ng-include="currentTab" id="current"></div>
    </div>
</div>
</body>
</html>