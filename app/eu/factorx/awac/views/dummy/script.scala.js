
var app = angular.module('app', ['ngAnimate']);

// ctrl
app.controller('MainCtrl', function ($scope) {
    $scope.isLoading = function () {
        for (var k in $scope.initialLoad) {
            if (!$scope.initialLoad[k]) return true;
        }
        return false;
    };

    $scope.initialLoad = {
        translations: false
    };

    $scope.$on('LOAD_FINISHED', function (event, args) {
        if (args.type == 'TRANSLATIONS') {
            $scope.initialLoad.translations = args.success;
        }
    });


});

// simple download service
app.service('downloadService', function ($http) {

    this.downloadsInProgress = 0;

    this.getDownloadsInProgress = function () {
        return this.downloadsInProgress;
    };

    this.getJson = function (url, callback) {

        this.downloadsInProgress++;

        var promise = $http({
            method: 'GET',
            url: url,
            headers: {
                "Content-Type": "application/json"
            }
        });
        promise.success(function (data, status, headers, config) {
            this.downloadsInProgress--;
            callback(data, status, headers, config);
        });
        promise.error(function (data, status, headers, config) {
            this.downloadsInProgress--;
            callback(null, status, headers, config);
            console.log("error when loading to " + property)
        });
        return promise;

    };
});

// i18n
app.service('translationService', function ($http, $rootScope, downloadService) {

    var svc = this;
    svc.elements = null;
    downloadService.getJson("/awac/translations/fr", function (data) {
        svc.elements = data.lines;
        $rootScope.$broadcast('LOAD_FINISHED', { type: 'TRANSLATIONS', success: data != null });
    });
    svc.get = function (code) {
        if (svc.elements == null) return '';
        return svc.elements[code];
    }
});

app.filter("translate", function ($sce, translationService) {
    return function (input) {

        var text = translationService.get(input);

        if (text != null) {
            return  $sce.trustAsHtml("<span class=\"translated-text\" data-code=\"" + input + "\">" + text + "</span>");
        } else {
            return  $sce.trustAsHtml("<span class=\"translated-text translation-missing\" data-code=\"" + input + "\">[" + input + "]</span>");
        }
    };
});
