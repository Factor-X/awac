angular
.module('app.controllers')
.controller "MainCtrl", ($scope, downloadService, translationService, $sce) ->
    $scope.isLoading = ->
        for k of $scope.initialLoad
            return true  unless $scope.initialLoad[k]
        false

    $scope.initialLoad =
        translations: false

    $scope.$on "LOAD_FINISHED", (event, args) ->
        if args.type is "TRANSLATIONS"
            $scope.initialLoad.translations = args.success
        return

    translationService.initialize('fr')

    $scope.language = 'fr';

    $scope.$watch 'language', (lang) ->
        translationService.initialize(lang)

    #$scope.prettyPrint = (o) ->
    #    return $sce.trustAsHtml(hljs.highlight('json', JSON.stringify(o, null, '  ')).value);

    downloadService.getJson "dummy/household/2014", (data) ->
        $scope.o = data
        $scope.o.consumption.units = $scope.volumeUnits;

        $scope.addOtherFuel = () ->
            console.log 'ok'
            $scope.o.otherFuels.push
                value: 0,
                unit: 0,
                units: $scope.volumeUnits,
                reviewers:
                    dataOwner: null
                    dataValidator: null
                    dataVerifier: null
                    dataLocker: null
            return false