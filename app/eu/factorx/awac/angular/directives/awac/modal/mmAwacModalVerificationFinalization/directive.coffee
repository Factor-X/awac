angular
.module('app.directives')
.directive "mmAwacModalVerificationFinalization", (directiveService, downloadService, translationService, messageFlash,$upload) ->
    restrict: "E"

    scope: directiveService.autoScope
        ngParams: '='
    templateUrl: "$/angular/templates/mm-awac-modal-verification-finalization.html"

    controller: ($scope, modalService) ->
        directiveService.autoScopeImpl $scope

        $scope.comment =
            fieldTitle: "MODAL_QUESTION_COMMENT_TITLE"
            validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
            fieldType: 'textarea'
            hidden:$scope.getParams().verificationSuccess == true
            focus: ->
                return true

        $scope.save = ()->
            if $scope.allFieldValid()
                $scope.isLoading=true

                dto =
                    scopeId: $scope.getParams().request.scope.id
                    periodKey: $scope.getParams().request.period.key

                if $scope.getParams().verificationSuccess == true
                    dto.newStatus ='VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_SUCCESS'
                    dto.verificationFinalizationFileId = $scope.file.id
                else
                    dto.newStatus ='VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_REJECT'



                downloadService.postJson "/awac/verification/setStatus", dto, (result) ->
                    $scope.isLoading=false
                    if not result.success
                        messageFlash.displayError(result.data.message)
                    else
                        $scope.getParams().removeRequest()
                        $scope.close()

        $scope.onFileSelect = ($files) ->
            $scope.inDownload = true


            #$files: an array of files selected, each file has name, size, and type.
            i = 0

            while i < $files.length
                file = $files[i]
                $scope.upload = $upload.upload(
                    url: "/awac/file/upload/"
                    data:
                        myObj: $scope.myModelObj

                    file: file
                ).progress((evt) ->
                    $scope.percent = parseInt(100.0 * evt.loaded / evt.total)
                    #console.log "percent: " + scope.percent
                    return
                ).success((data, status, headers, config) ->
                    # file is uploaded successfully
                    $scope.percent = 0
                    $scope.inDownload = false
                    fileName = data.name
                    messageFlash.displaySuccess("The file " + fileName + " was upload successfully")
                    #console.log data

                    #add the file to the answer
                    $scope.file =  data

                    return
                ).error((data, status, headers, config) ->
                    $scope.percent = 0
                    $scope.inDownload = false
                    messageFlash.displayError("The upload of the file was faild")
                    return
                )
                i++
            return

        $scope.allFieldValid = ->
            return $scope.getParams().verificationSuccess != true || $scope.file?

        $scope.close = ->
            modalService.close modalService.VERIFICATION_FINALIZATION


