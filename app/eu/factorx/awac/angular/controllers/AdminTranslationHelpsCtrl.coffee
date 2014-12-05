angular
.module('app.controllers')
.controller "AdminTranslationHelpsCtrl", ($scope, $compile, $timeout, downloadService, displayLittleFormMenu, modalService, messageFlash, translationService, codeLabelHelper) ->
    $scope.displayLittleFormMenu = displayLittleFormMenu

    $scope.files = [];


    if $scope.files.length > 0
        $scope.selectedFile = $scope.files[0]

    $scope.select = (file) ->
        $scope.selectedFile = file

    $scope.isModified = (file) ->
        return file.content != file.original

    $scope.isSelected = (file) ->
        return $scope.selectedFile == file

    $scope.load = () ->
        downloadService.getJson "/awac/admin/translations/wysiwyg/all", (result) ->
            if result.success
                $scope.files = result.data.files
                for f in $scope.files
                    f.original = f.content

    $scope.categories = [
        {key: 'enterprise', label: 'Entreprise'},
        {key: 'municipality', label: 'Communes'},
        {key: 'household', label: 'Ménages'},
        {key: 'verification', label: 'Vérification'},
        {key: 'event', label: 'Evènements'},
        {key: 'littleemitter', label: 'Petits émetteurs '},
    ]

    $scope.save = (f) ->
        downloadService.postJson "/awac/admin/translations/wysiwyg/update", _.omit(f, 'original'), (result) ->
            if result.success
                f.original = f.content


    $scope.editorOptions =
        language: 'fr'
        skin: 'moono'
        uiColor: '#CFCDC0'
        height: '500px',
        toolbar: 'Wysisyg'

    # Handle CTRL+S to save
    $(window).keydown (event) ->

        if (!( String.fromCharCode(event.which).toLowerCase() == 's' && event.ctrlKey) && !(event.which == 19)) || !$scope.selectedFile
            return true

        $scope.save($scope.selectedFile);

        event.preventDefault();
        return false;

    $scope.load()

