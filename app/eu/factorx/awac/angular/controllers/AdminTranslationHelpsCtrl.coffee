angular
.module('app.controllers')
.controller "AdminTranslationHelpsCtrl", ($scope, $compile, $timeout, downloadService, modalService, messageFlash, translationService, codeLabelHelper) ->
    $scope.files = [];

    for f in $scope.files
        f.original = f.content

    if $scope.files.length > 0
        $scope.selectedFile = $scope.files[0]

    $scope.select = (file) ->
        $scope.selectedFile = file
        for k,v of CKEDITOR.instances
            $timeout () ->
                v.resetUndo()
            , 50

    $scope.isModified = (file) ->
        return file.content != file.original

    $scope.isSelected = (file) ->
        return $scope.selectedFile == file

    $scope.load = () ->
        downloadService.getJson "/awac/admin/translations/wysiwyg/all", (result) ->
            if result.success
                $scope.files = result.data.files

    $scope.editorOptions =
        language: 'fr'
        skin: 'moono'
        uiColor: '#CFCDC0'
        height: '300px'


    $scope.load()

