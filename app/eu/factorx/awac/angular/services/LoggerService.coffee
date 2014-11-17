angular
.module('app.services')
.service 'loggerService', () ->
    svc = this

    svc.initialize = () ->
        log = log4javascript.getLogger('main')
        svc.appender = new log4javascript.InPageAppender('logger', true, false, true, '100%', '100%')
        layout = new log4javascript.PatternLayout("%d [%-5p] %15c - %m")
        svc.appender.setLayout layout
        log.addAppender svc.appender
        log.info "Logger initialized"


    svc.get = (name) ->
        log = log4javascript.getLogger(name)
        log.addAppender svc.appender
        return log

    return