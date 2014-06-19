angular
.module('app')
.controller "MainCtrl", ($scope) ->
    $scope.isLoading = ->
        for k of $scope.initialLoad
            return true  unless $scope.initialLoad[k]
        false

    $scope.initialLoad = translations: false
    $scope.$on "LOAD_FINISHED", (event, args) ->
        $scope.initialLoad.translations = args.success  if args.type is "TRANSLATIONS"
        return

    $scope.o =
        myDouble:
            value: 17.97
            owner: "XM"
            validator: "JC"
            verifier: "FC"

        myDoubleWithUnit:
            value: 17.97
            unit: 0
            owner: "XM"
            validator: null
            verifier: null
            units: [
                {
                    key: 12
                    value: "UNITS-KILOGRAM"
                }
                {
                    key: 14
                    value: "UNITS-GRAM"
                }
                {
                    key: 135
                    value: "UNITS-TON"
                }
            ]
