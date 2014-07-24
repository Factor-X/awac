angular
.module('app.directives')
.directive "mmAwacDocumentQuestion", (directiveService, translationService,$upload) ->
    restrict: "E"
    scope: directiveService.autoScope
        ngQuestionCode: '='
        ngCondition: '='
        ngObject: '='
    templateUrl: "$/angular/templates/mm-awac-document-question.html"
    replace: true
    compile: () ->
        pre: (scope) ->
            directiveService.autoScopeImpl scope

            scope.getAnswerByQuestionCode = (code) ->
                if scope.ngObject
                    for qv in scope.ngObject.answersSaveDTO.listAnswers
                        if qv.questionKey == code
                            return qv
                return null

            scope.hasDescription = () ->
                return translationService.get(scope.getQuestionCode() + '_DESC') != null


            scope.$watch 'ngCondition', () ->
                #if scope.getCondition() == false
                #  scope.getAnswerValue().value = null



            scope.onFileSelect = ($files) ->

                #$files: an array of files selected, each file has name, size, and type.
                i = 0

                while i < $files.length
                    file = $files[i]
                    #upload.php script, node.js route, or servlet url
                    #method: 'POST' or 'PUT',
                    #headers: {'header-key': 'header-value'},
                    #withCredentials: true,
                    # or list of files ($files) for html5 only
                    #fileName: 'doc.jpg' or ['1.jpg', '2.jpg', ...] // to modify the name of the file(s)
                    # customize file formData name ('Content-Desposition'), server side file variable name.
                    #fileFormDataName: myFile, //or a list of names for multiple files (html5). Default is 'file'
                    # customize how data is added to formData. See #40#issuecomment-28612000 for sample code
                    #formDataAppender: function(formData, key, val){}
                    scope.upload = $upload.upload(
                        url: "server/upload/url"
                        data:
                            myObj: scope.myModelObj

                        file: file
                    ).progress((evt) ->
                        console.log "percent: " + parseInt(100.0 * evt.loaded / evt.total)
                        return
                    ).success((data, status, headers, config) ->

                        # file is uploaded successfully
                        console.log data
                        return
                    )
                    i++
                return

                #.error(...)
                #.then(success, error, progress);
                # access or attach event listeners to the underlying XMLHttpRequest.
                #.xhr(function(xhr){xhr.upload.addEventListener(...)})

                # alternative way of uploading, send the file binary with the file's content-type.
                #       Could be used to upload files to CouchDB, imgur, etc... html5 FileReader is needed.
                #       It could also be used to monitor the progress of a normal http post/put request with large data

                # scope.upload = $upload.http({...})  see 88#issuecomment-31366487 for sample code.
