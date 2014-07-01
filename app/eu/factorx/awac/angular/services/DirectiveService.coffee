# simple download service
angular
.module('app.services')
.service "directiveService", ($sce) ->
    @autoScope = (s) ->
        res = {}

        for k, v of s
            res[k] = v
            if k[0..1] == 'ng' and v == '='
                res[ k[2].toLowerCase() + k[3..] ] = '@'

        return res

    @autoScopeImpl = (s, name) ->
        s.$$NAME = name

        for key, val of s
            if key[0..1] == 'ng'

                fget = (scope, k) ->
                    return () ->
                        v = 0

                        if scope[k] == undefined || scope[k] == null || scope[k] == ''
                            v = scope[k[2].toLowerCase() + k[3..]]
                        else
                            v = scope[k]

                        if scope['decorate' + k[2..]]
                            return scope['decorate' + k[2..]](v)
                        else
                            return v

                s['get' + key[2..]] = fget(s, key)

        s.isTrue = (v) ->
            v == true || v == 'true' || v == 'y'

        s.isFalse = (v) ->
            v == false || v == 'false' || v == 'n'

        s.isNull = (v) ->
            v == null

        s.html = (v) ->
            $sce.trustAsHtml(v)


    return
