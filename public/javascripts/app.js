var initializeMunicipalityRoutes;
initializeMunicipalityRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    $rootScope.getFormPath = function() {
      return '/form/TAB_C1';
    };
    return $rootScope.getDefaultRoute = function() {
      return $rootScope.getFormPath();
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/form/:form/:period/:scope', {
      templateUrl: function($routeParams) {
        return '$/angular/views/municipality/' + $routeParams.form + '.html';
      },
      controller: 'FormCtrl',
      resolve: angular.extend({
        formIdentifier: function($route) {
          return $route.current.params.form;
        },
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:form';
        }
      }, formResolve)
    }).when('/results/:period/:scope', {
      templateUrl: '$/angular/views/results.html',
      controller: 'ResultsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:results';
        }
      }, defaultResolve)
    }).when('/actions/:period/:scope', {
      templateUrl: '$/angular/views/actions.html',
      controller: 'ActionsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:actions';
        }
      }, defaultResolve)
    });
    return;
  });
};var initializeHouseholdRoutes;
initializeHouseholdRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    $rootScope.getFormPath = function() {
      return '/form/TAB_M1';
    };
    return $rootScope.getDefaultRoute = function() {
      return $rootScope.getFormPath();
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/form/:form/:period/:scope', {
      templateUrl: function($routeParams) {
        return '$/angular/views/household/' + $routeParams.form + '.html';
      },
      controller: 'FormCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help:form';
        }
      }, formResolve)
    }).when('/results/:period/:scope', {
      templateUrl: '$/angular/views/results.html',
      controller: 'ResultsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:results';
        }
      }, resultResolve)
    }).when('/actions/:period/:scope', {
      templateUrl: '$/angular/views/actions.html',
      controller: 'ActionsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:actions';
        }
      }, defaultResolve)
    });
    return;
  });
};var initializeCommonRoutes;
initializeCommonRoutes = function(defaultResolve) {
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/login', {
      templateUrl: '$/angular/views/login.html',
      controller: 'LoginCtrl',
      anonymousAllowed: true,
      resolve: {
        testConnection: function($http, $rootScope, $location, downloadService) {
          if ($rootScope.currentPerson != null) {
            return $rootScope.toDefaultForm();
          } else {
            console.log("testAuthentication to initializeCommonRoutes ");
            return downloadService.postJson('/awac/testAuthentication', {
              interfaceName: $rootScope.instanceName
            }, function(result) {
              if (result.success) {
                $rootScope.loginSuccess(result.data, !$rootScope.isLogin());
                $rootScope.toDefaultForm();
                return true;
              } else {
                return true;
              }
            });
          }
        }
      }
    }).when('/admin', {
      templateUrl: '$/angular/views/admin.html',
      controller: 'AdminCtrl',
      resolve: defaultResolve
    }).when('/user_data', {
      templateUrl: '$/angular/views/user_data.html',
      controller: 'UserDataCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help:user:data';
        }
      }, defaultResolve)
    }).when('/organization_manager', {
      templateUrl: '$/angular/views/organization_manager.html',
      controller: 'OrganizationManagerCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help:organization:manager';
        }
      }, defaultResolve)
    }).when('/user_manager', {
      templateUrl: '$/angular/views/user_manager.html',
      controller: 'UserManagerCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help:user:manager';
        }
      }, defaultResolve)
    }).when('/site_manager', {
      templateUrl: '$/angular/views/site_manager.html',
      controller: 'SiteManagerCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help:site:manager';
        }
      }, defaultResolve)
    }).when('/product_manager', {
      templateUrl: '$/angular/views/product_manager.html',
      controller: 'ProductManagerCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help_product_manager';
        }
      }, defaultResolve)
    }).when('/registration/:key', {
      templateUrl: '$/angular/views/user_registration.html',
      controller: 'RegistrationCtrl',
      anonymousAllowed: true
    }).when('/logout', {
      resolve: angular.extend({
        helpPage: function() {
          return 'help_product_manager';
        }
      }, logoutResolve)
    }).when('/noScope', {
      templateUrl: '$/angular/views/no_scope.html',
      controller: 'NoScopeCtrl',
      anonymousAllowed: true,
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).otherwise({
      redirectTo: '/login'
    });
    return;
  });
};var initializeLittleEmitterRoutes;
initializeLittleEmitterRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    $rootScope.getFormPath = function() {
      return '/form/TAB_P1';
    };
    return $rootScope.getDefaultRoute = function() {
      return $rootScope.getFormPath();
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/form/:form/:period/:scope', {
      templateUrl: function($routeParams) {
        return '$/angular/views/littleemitter/' + $routeParams.form + '.html';
      },
      controller: 'FormCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help:form';
        }
      }, formResolve)
    }).when('/results/:period/:scope', {
      templateUrl: '$/angular/views/results.html',
      controller: 'ResultsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:results';
        }
      }, resultResolve)
    }).when('/actions/:period/:scope', {
      templateUrl: '$/angular/views/actions.html',
      controller: 'ActionsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:actions';
        }
      }, defaultResolve)
    });
    return;
  });
};var initializeAdminRoutes;
initializeAdminRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    return $rootScope.getDefaultRoute = function() {
      return '/driver';
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/driver', {
      templateUrl: '$/angular/views/admin/driver.html',
      controller: 'AdminDriverManageCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).when('/factors', {
      templateUrl: '$/angular/views/admin/factors.html',
      controller: 'AdminFactorsManagerCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).when('/advice', {
      templateUrl: '$/angular/views/admin/manage_advices.html',
      controller: 'AdminAdvicesManagerCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).when('/translation', {
      redirectTo: '/translation/interface'
    }).when('/translation/interface', {
      templateUrl: '$/angular/views/admin/translation_interface.html',
      controller: "AdminTranslationInterfaceCtrl",
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).when('/translation/error-messages', {
      templateUrl: '$/angular/views/admin/translation_single_list.html',
      controller: "AdminTranslationSingleListCtrl",
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        },
        state: function() {
          return 'error-messages';
        }
      }, defaultResolve)
    }).when('/translation/emails', {
      templateUrl: '$/angular/views/admin/translation_single_list.html',
      controller: "AdminTranslationSingleListCtrl",
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        },
        state: function() {
          return 'emails';
        }
      }, defaultResolve)
    }).when('/translation/forms', {
      templateUrl: '$/angular/views/admin/translation_surveys.html',
      controller: "AdminTranslationSurveysLabelsCtrl",
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).when('/translation/base-lists', {
      templateUrl: '$/angular/views/admin/translation_base_lists.html',
      controller: "AdminTranslationBaseListCtrl",
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).when('/translation/linked-lists', {
      templateUrl: '$/angular/views/admin/translation_linked_lists.html',
      controller: "AdminTranslationLinkedListCtrl",
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).when('/translation/sub-lists', {
      templateUrl: '$/angular/views/admin/translation_sub_lists.html',
      controller: "AdminTranslationSubListCtrl",
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).when('/translation/helps', {
      templateUrl: '$/angular/views/admin/translation_helps.html',
      controller: "AdminTranslationHelpsCtrl",
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).when('/average', {
      templateUrl: '$/angular/views/admin/average.html',
      controller: 'AdminAverageCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).when('/organization_data', {
      templateUrl: '$/angular/views/admin/organization_data.html',
      controller: 'AdminOrganizationDataCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        }
      }, defaultResolve)
    }).otherwise({
      redirectTo: '/login'
    });
    return;
  });
};var initializeEnterpriseRoutes;
initializeEnterpriseRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    $rootScope.getFormPath = function() {
      return '/form/TAB2';
    };
    return $rootScope.getDefaultRoute = function() {
      return $rootScope.getFormPath();
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/form/:form/:period/:scope', {
      templateUrl: function($routeParams) {
        return '$/angular/views/enterprise/' + $routeParams.form + '.html';
      },
      controller: 'FormCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help:form';
        }
      }, formResolve)
    }).when('/results/:period/:scope', {
      templateUrl: '$/angular/views/results.html',
      controller: 'ResultsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:results';
        }
      }, resultResolve)
    }).when('/actions/:period/:scope', {
      templateUrl: '$/angular/views/actions.html',
      controller: 'ActionsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:actions';
        }
      }, defaultResolve)
    });
    return;
  });
};var initializeVerificationRoutes;
initializeVerificationRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    return $rootScope.getDefaultRoute = function() {
      return '/verification';
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/manage', {
      templateUrl: '$/angular/views/verification/manage.html',
      controller: 'VerificationManageCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:manage';
        }
      }, defaultResolve)
    }).when('/verification', {
      templateUrl: '$/angular/views/verification/verification.html',
      controller: 'VerificationVerificationCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:verification';
        }
      }, defaultResolve)
    }).when('/submit', {
      templateUrl: '$/angular/views/verification/submit.html',
      controller: 'VerificationSubmitCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:submit';
        }
      }, defaultResolve)
    }).when('/verification_registration/:key', {
      templateUrl: '$/angular/views/verification_registration.html',
      controller: 'VerificationRegistrationCtrl',
      anonymousAllowed: true
    }).when('/archive', {
      templateUrl: '$/angular/views/verification/archive.html',
      controller: 'VerificationArchiveCtrl',
      resolve: angular.extend({
        displayLittleFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:archive';
        }
      }, defaultResolve)
    }).otherwise({
      redirectTo: '/login'
    });
    return;
  });
};var initializeEventRoutes;
initializeEventRoutes = function(defaultResolve) {
  angular.module('app').run(function($rootScope, $location) {
    $rootScope.onFormPath = function(period, scope) {
      return $location.path($rootScope.getFormPath() + '/' + period + '/' + scope);
    };
    $rootScope.getFormPath = function() {
      return '/form/TAB_EV1';
    };
    return $rootScope.getDefaultRoute = function() {
      return $rootScope.getFormPath();
    };
  });
  return angular.module('app').config(function($routeProvider) {
    $routeProvider.when('/form/:form/:period/:scope', {
      templateUrl: function($routeParams) {
        return '$/angular/views/event/' + $routeParams.form + '.html';
      },
      controller: 'FormCtrl',
      resolve: angular.extend({
        helpPage: function() {
          return 'help:form';
        }
      }, formResolve)
    }).when('/results/:period/:scope', {
      templateUrl: '$/angular/views/results.html',
      controller: 'ResultsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:results';
        }
      }, resultResolve)
    }).when('/actions/:period/:scope', {
      templateUrl: '$/angular/views/actions.html',
      controller: 'ActionsCtrl',
      resolve: angular.extend({
        displayFormMenu: function() {
          return true;
        },
        helpPage: function() {
          return 'help:actions';
        }
      }, defaultResolve)
    });
    return;
  });
};var defaultResolve, formResolve, iName, logoutResolve, resultResolve;
angular.module('app.directives', ['ngAnimate', 'ngSanitize', 'ui.bootstrap', 'ui.bootstrap.datetimepicker', 'ngCkeditor', 'ui.sortable', 'RecursionHelper']);
angular.module('app.filters', []);
angular.module('app.services', []);
angular.module('app.controllers', ['app.services', 'ngRoute', 'angularFileUpload', 'tmh.dynamicLocale', 'ngTable']);
angular.module('app', ['app.directives', 'app.filters', 'app.services', 'app.controllers']);
angular.module("tmh.dynamicLocale").config(function(tmhDynamicLocaleProvider) {
  return tmhDynamicLocaleProvider.localeLocationPattern('assets/components/angular-i18n/angular-locale_{{locale}}.js');
});
logoutResolve = {
  logout: function($rootScope, downloadService) {
    console.log('logout !! ');
    $rootScope.currentPerson = null;
    $rootScope.periodSelectedKey = null;
    $rootScope.scopeSelectedId = null;
    $rootScope.mySites = null;
    $rootScope.organizationName = null;
    return downloadService.postJson('/awac/logout', null, function(result) {
      return location.href = '/' + $rootScope.instanceName;
    });
  }
};
defaultResolve = {
  testConnection: function($http, $rootScope, $location, downloadService) {
    if (!$rootScope.currentPerson) {
      console.log('test authentication because not curennt person : defaultResolve');
      return downloadService.postJson('/awac/testAuthentication', {
        interfaceName: $rootScope.instanceName
      }, function(result) {
        if (result.success) {
          return $rootScope.loginSuccess(result.data, !$rootScope.isLogin());
        } else {
          console.log('to login  : defaultResolve');
          return $location.path('/login');
        }
      });
    }
  }
};
formResolve = {
  testConnection: function($http, $rootScope, $location, downloadService, $route) {
    if (!$rootScope.currentPerson) {
      console.log('test authentication because not curennt person : formResolve');
      return downloadService.postJson('/awac/testAuthentication', {
        interfaceName: $rootScope.instanceName
      }, function(result) {
        if (result.success) {
          $rootScope.loginSuccess(result.data, !$rootScope.isLogin());
          if ($rootScope.testForm($route.current.params.period, $route.current.params.scope) === false) {
            return $location.path("/noScope");
          }
        } else {
          console.log('to login  : formResolve');
          return $location.path('/login');
        }
      });
    } else {
      if ($rootScope.testForm($route.current.params.period, $route.current.params.scope) === false) {
        return $location.path('/noScope');
      }
    }
  }
};
resultResolve = {
  testConnection: function($http, $rootScope, $location, downloadService, $route) {
    if (!$rootScope.currentPerson) {
      console.log('test authentication because not curennt person defaultResolve : resultResolve');
      return downloadService.postJson('/awac/testAuthentication', {
        interfaceName: $rootScope.instanceName
      }, function(result) {
        if (result.success) {
          $rootScope.loginSuccess(result.data, !$rootScope.isLogin());
          if ($rootScope.testForm($route.current.params.period, $route.current.params.scope) === false) {
            return $location.path('/noScope');
          }
        } else {
          console.log('to login  : resultResolve');
          return $location.path('/login');
        }
      });
    }
  }
};
if (document.querySelector("meta[name=app]") != null) {
  iName = document.querySelector("meta[name=app]").getAttribute("content");
  if (iName === "municipality") {
    initializeMunicipalityRoutes(defaultResolve);
  } else if (iName === "enterprise") {
    initializeEnterpriseRoutes(defaultResolve);
  } else if (iName === "verification") {
    initializeVerificationRoutes(defaultResolve);
  } else if (iName === "littleemitter") {
    initializeLittleEmitterRoutes(defaultResolve);
  } else if (iName === "event") {
    initializeEventRoutes(defaultResolve);
  } else if (iName === "household") {
    initializeHouseholdRoutes(defaultResolve);
  } else if (iName === "admin") {
    initializeAdminRoutes(defaultResolve);
  }
}
initializeCommonRoutes(defaultResolve);
angular.module('app').run(function($rootScope) {
  $rootScope.instanceName = iName;
  return window.rs = $rootScope;
});
angular.module('app').config(function($httpProvider) {
  console.log($httpProvider);
  return $httpProvider.interceptors.push(function() {
    return {
      'request': function(config) {
        return config;
      },
      'response': function(response) {
        if (response.data.__type === "eu.factorx.awac.dto.awac.get.DownloadFileDTO") {
          setTimeout(function() {
            var blob, byteArray, byteCharacters, byteNumbers, filename, i, _ref;
            byteCharacters = atob(response.data.base64);
            byteNumbers = new Array(byteCharacters.length);
            for (i = 0, _ref = byteCharacters.length; (0 <= _ref ? i < _ref : i > _ref); (0 <= _ref ? i += 1 : i -= 1)) {
              byteNumbers[i] = byteCharacters.charCodeAt(i);
            }
            byteArray = new Uint8Array(byteNumbers);
            blob = new Blob([byteArray], {
              type: response.data.mimeType
            });
            filename = response.data.filename;
            return saveAs(blob, filename);
          }, 0);
        }
        if (response.data.__type === "eu.factorx.awac.dto.awac.get.PromiseDTO") {
          window.rs.$broadcast('PROMISE', response.data.uuid);
        }
        return response;
      }
    };
  });
});angular.module('app.services').service("messageFlash", function(translateTextFilter) {
  this.display = function(type, message, opts) {
    var options;
    options = {
      message: translateTextFilter(message),
      type: type,
      hideAfter: 5,
      showCloseButton: true
    };
    return Messenger().post(angular.extend(options, angular.copy(opts)));
  };
  this.displaySuccess = function(message, opts) {
    return this.display('success', message, opts);
  };
  this.displayInfo = function(message, opts) {
    return this.display('info', message, opts);
  };
  this.displayError = function(message, opts) {
    return this.display('error', message, opts);
  };
  return;
});angular.module('app.services').service("directiveService", function($sce) {
  this.autoScope = function(s) {
    var k, res, v;
    res = {};
    for (k in s) {
      v = s[k];
      res[k] = v;
      if (k.slice(0, 2) === 'ng' && v === '=') {
        res[k[2].toLowerCase() + k.slice(3)] = '@';
      }
    }
    return res;
  };
  this.autoScopeImpl = function(s, name) {
    var fget, key, val;
    s.$$NAME = name;
    for (key in s) {
      val = s[key];
      if (key.slice(0, 2) === 'ng') {
        fget = function(scope, k) {
          return function() {
            var v;
            v = 0;
            if (scope[k] === void 0 || scope[k] === null || scope[k] === '') {
              v = scope[k[2].toLowerCase() + k.slice(3)];
            } else {
              v = scope[k];
            }
            if (scope['decorate' + k.slice(2)]) {
              return scope['decorate' + k.slice(2)](v);
            } else {
              return v;
            }
          };
        };
        s['get' + key.slice(2)] = fget(s, key);
      }
    }
    s.isTrue = function(v) {
      return v === true || v === 'true' || v === 'y';
    };
    s.isFalse = function(v) {
      return v === false || v === 'false' || v === 'n';
    };
    s.isNull = function(v) {
      return v === null;
    };
    return s.html = function(v) {
      return $sce.trustAsHtml(v);
    };
  };
  return;
});angular.module('app.services').service('loggerService', function() {
  var svc;
  svc = this;
  svc.initialize = function() {
    var layout, log;
    log = log4javascript.getLogger('main');
    svc.appender = new log4javascript.InPageAppender('logger', true, false, true, '100%', '100%');
    layout = new log4javascript.PatternLayout("%d [%-5p] %15c - %m");
    svc.appender.setLayout(layout);
    log.addAppender(svc.appender);
    return log.info("Logger initialized");
  };
  svc.get = function(name) {
    var log;
    log = log4javascript.getLogger(name);
    log.addAppender(svc.appender);
    return log;
  };
  return;
});angular.module('app.services').service("downloadService", function($http, $q, messageFlash, translationService) {
  this.downloadsInProgress = 0;
  this.getDownloadsInProgress = function() {
    return this.downloadsInProgress;
  };
  this.getJson = function(url, callback) {
    var deferred, promise;
    deferred = $q.defer();
    this.downloadsInProgress++;
    promise = $http({
      method: "GET",
      url: url,
      headers: {
        "Content-Type": "application/json",
        "Pragma": "no-cache"
      }
    });
    promise.success(function(data, status, headers, config) {
      this.downloadsInProgress--;
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: true
      }));
    });
    promise.error(function(data, status, headers, config) {
      this.downloadsInProgress--;
      if (!!data) {
        messageFlash.displayError(translationService.translateExceptionsDTO(data));
      }
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: false
      }));
    });
    return deferred.promise;
  };
  this.postJson = function(url, data, callback, options) {
    var deferred, promise;
    deferred = $q.defer();
    if (data === null) {
      data = {};
    }
    this.downloadsInProgress++;
    promise = $http({
      method: "POST",
      url: url,
      data: data,
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json,application/octet-stream"
      }
    });
    promise.success(function(data, status, headers, config) {
      this.downloadsInProgress--;
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: true
      }));
    });
    promise.error(function(data, status, headers, config) {
      this.downloadsInProgress--;
      if (!!data) {
        messageFlash.displayError(translationService.translateExceptionsDTO(data));
      }
      return deferred.resolve(callback({
        data: data,
        status: status,
        headers: headers,
        config: config,
        success: false
      }));
    });
    return deferred.promise;
  };
  return;
});angular.module('app.services').service("translationService", function($rootScope, $filter, $http) {
  var svc;
  svc = this;
  svc.elements = null;
  svc.lang = null;
  svc.reinitialize = function() {
    return svc.initialize(svc.lang);
  };
  svc.initialize = function(lang) {
    console.log("translationService.initialize('" + lang + "')");
    $http({
      method: "GET",
      url: "/awac/translations/" + lang,
      headers: {
        "Content-Type": "application/json"
      }
    }).success(function(data, status, headers, config) {
      svc.elements = data.lines;
      svc.lang = lang;
      return $rootScope.$broadcast("LOAD_FINISHED", {
        type: "TRANSLATIONS",
        success: true
      });
    }).error(function(data, status, headers, config) {
      svc.elements = [];
      svc.lang = null;
      return $rootScope.$broadcast("LOAD_FINISHED", {
        type: "TRANSLATIONS",
        success: false
      });
    });
    return;
  };
  svc.get = function(code, count) {
    var txt, v;
    if (svc.elements == null) {
      return "";
    }
    v = svc.elements[code];
    if (!(v != null)) {
      return null;
    }
    if (count != null) {
      if ("" + count === "0") {
        txt = v.zero || v.fallback;
      } else if ("" + count === "1") {
        txt = v.one || v.fallback;
      } else {
        txt = v.more || v.fallback;
      }
      txt = txt.replace(/\{0\}/g, count);
    } else {
      txt = v.fallback || '';
    }
    return txt;
  };
  svc.translateExceptionsDTO = function(exception) {
    if ((exception.params != null) && Object.keys(exception.params).length > 0) {
      return $filter('translateTextWithVars')(exception.messageToTranslate, exception.params);
    } else if (exception.messageToTranslate != null) {
      return $filter('translate')(exception.messageToTranslate);
    } else {
      return exception.message;
    }
  };
  return;
});angular.module('app.services').service("codeLabelHelper", function() {
  this.getLabelByKey = function(codeLabels, codeKey) {
    return _.findWhere(codeLabels, {
      key: codeKey
    }).label;
  };
  this.sortCodeLabelsByKey = function(codeLabels) {
    return _.sortBy(codeLabels, function(codeLabel) {
      return codeLabel.key;
    });
  };
  this.sortCodeLabelsByNumericKey = function(codeLabels) {
    return _.sortBy(codeLabels, function(codeLabel) {
      return parseInt(codeLabel.key.match(/\d+/), 10);
    });
  };
  this.sortCodeLabelsByOrder = function(codeLabels) {
    return _.sortBy(codeLabels, function(codeLabel) {
      return codeLabel.orderIndex;
    });
  };
  this.removeCodeLabelsByKeys = function(codeLabels, keysToRemove) {
    return _.reject(codeLabels, function(codeLabel) {
      return _.contains(keysToRemove, codeLabel.key);
    });
  };
  return;
});angular.module('app.services').service("modalService", function($rootScope) {
  this.LOADING = 'loading';
  this.DOCUMENT_MANAGER = 'DOCUMENT_MANAGER';
  this.CONFIRMATION_EXIT_FORM = 'confirmation-exit-form';
  this.QUESTION_COMMENT = 'question-comment';
  this.EMAIL_CHANGE = 'email-change';
  this.PASSWORD_CHANGE = 'password-change';
  this.CONNECTION_PASSWORD_CHANGE = 'connection-password-change';
  this.INVITE_USER = 'invite-user';
  this.EDIT_SITE = 'edit-site';
  this.ADD_USER_SITE = 'add-user-site';
  this.EDIT_EVENT = 'edit-event';
  this.CONFIRM_CLOSING = 'confirm-closing';
  this.HELP = 'help';
  this.CREATE_REDUCTION_ACTION = 'create-reduction-action';
  this.EDIT_OR_CREATE_REDUCTION_ACTION = 'edit-or-create-reduction-action';
  this.REQUEST_VERIFICATION = 'request_verification';
  this.VERIFICATION_ASSIGN = 'verification_assign';
  this.VERIFICATION_REJECT = 'verification_reject';
  this.VERIFICATION_REJECT = 'verification_reject';
  this.CONSULT_EVENT = 'consult-event';
  this.VERIFICATION_FINALIZATION = 'verification-finalization';
  this.PASSWORD_CONFIRMATION = 'password-confirmation';
  this.VERIFICATION_CONFIRMATION = 'verification-confirmation';
  this.VERIFICATION_DOCUMENT = 'verification-document';
  this.VERIFICATION_FINALIZATION_VISUALIZATION = 'verification-finalization-visualization';
  this.DRIVER_ADD_VALUE = 'driver-add-value';
  this.CONFIRM_DIALOG = 'confirm-dialog';
  this.AGREEMENT = 'agreement';
  this.CREATE_OR_EDIT_REDUCTION_ACTION_ADVICE = 'create-or-edit-reduction-action-advice';
  this.EDIT_SUB_LIST = 'edit-sub-list';
  this.SEND_EMAIL = 'send-email';
  this.EDIT_PRODUCT = 'edit-product';
  this.ADD_USER_PRODUCT = 'add-user-product';
  this.show = function(modalName, params) {
    var args;
    args = [];
    args.show = true;
    args.params = params;
    args.target = modalName;
    $rootScope.displayModalBackground = true;
    return $rootScope.$broadcast('SHOW_MODAL', args);
  };
  this.close = function(modalName) {
    var args;
    args = [];
    args.show = false;
    args.target = modalName;
    $rootScope.displayModalBackground = false;
    return $rootScope.$broadcast('SHOW_MODAL', args);
  };
  return;
});angular.module('app.services').service("generateId", function($rootScope) {
  this.generate = function() {
    var i, possible, text;
    text = "";
    possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    i = 0;
    while (i < 20) {
      text += possible.charAt(Math.floor(Math.random() * possible.length));
      i++;
    }
    return text;
  };
  return;
});angular.module('app.filters').filter("translate", function($sce, translationService) {
  return function(input, count) {
    var text;
    text = translationService.get(input, count);
    if (text != null) {
      return $sce.trustAsHtml("<span class=\"translated-text\" data-code=\"" + input + "\">" + text + "</span>");
    } else {
      return $sce.trustAsHtml("<span class=\"translated-text translation-missing\" data-code=\"" + input + "\">[" + input + "]</span>");
    }
  };
});angular.module('app.filters').filter("trustAsHtml", function($sce) {
  return function(input) {
    return $sce.trustAsHtml(input);
  };
});angular.module('app.filters').filter("numberToI18NRoundedOrFullIfLessThanOne", function($filter) {
  return function(input) {
    var original;
    if (input != null) {
      original = parseFloat(input);
      if (original < 1) {
        if (original === 0) {
          return 0;
        } else {
          return $filter("number")(original, 12);
        }
      } else {
        return $filter("number")(original, 3);
      }
    }
    return "";
  };
});angular.module('app.filters').filter("translateText", function($sce, translationService) {
  return function(input, count) {
    var text;
    text = translationService.get(input, count);
    if (text != null) {
      return text;
    }
    return input;
  };
});angular.module('app.filters').filter("numberToI18N", function($filter) {
  return function(input) {
    var original, rounded;
    if (input != null) {
      original = parseFloat(input);
      rounded = Math.round(original * 100.0) / 100.0;
      return $filter("number")(rounded, 2);
    }
    return "";
  };
});angular.module('app.filters').filter("numberToI18NOrLess", function($filter, translationService) {
  return function(input) {
    var original, rounded;
    if (input != null) {
      original = parseFloat(input);
      rounded = Math.round(original * 1000.0) / 1000.0;
      if (rounded < 0.001) {
        if (original > 0) {
          return translationService.get('LESS_THAN_MINIMUM');
        } else {
          return '';
        }
      } else {
        return $filter("number")(rounded, 3);
      }
    }
    return "";
  };
});angular.module('app.filters').filter("stringToFloat", function($sce, translationService) {
  return function(input) {
    if (input != null) {
      return parseFloat(input);
    }
  };
});angular.module('app.filters').filter("translateTextWithVars", function($sce, translationService) {
  return function(input, vars) {
    var k, text, v;
    text = translationService.get(input, null);
    if (text != null) {
      for (k in vars) {
        v = vars[k];
        text = text.replace('{' + k + '}', v);
      }
      return text;
    }
    return input;
  };
});angular.module('app.filters').filter("nullToZero", function($sce, translationService) {
  return function(input) {
    if (input === void 0 || input === null) {
      return 0;
    } else {
      return parseFloat(input);
    }
  };
});angular.module('app.filters').filter("translateWithVars", function($sce, translationService) {
  return function(input, vars) {
    var k, text, v;
    text = translationService.get(input, null);
    if (text != null) {
      for (k in vars) {
        v = vars[k];
        text = text.replace('{' + k + '}', v);
      }
      return $sce.trustAsHtml("<span class=\"translated-text\" data-code=\"" + input + "\">" + text + "</span>");
    } else {
      return $sce.trustAsHtml("<span class=\"translated-text translation-missing\" data-code=\"" + input + "\">[" + input + "]</span>");
    }
  };
});
angular.module('app.directives').directive('mmPatternValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmPatternValidator'.length) == 'mmPatternValidator' && k.length > 'mmPatternValidator'.length) {
                        arg = k.substring('mmPatternValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(value, args) {
    var re = new RegExp(eval(args.regexp));
    return re.test(value);
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('pattern', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('pattern', false);
                  return undefined;
                }

            });
        }
    };
});
            
angular.module('app.directives').directive('mmNotNullValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmNotNullValidator'.length) == 'mmNotNullValidator' && k.length > 'mmNotNullValidator'.length) {
                        arg = k.substring('mmNotNullValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(v) {
    return v != null
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('not-null', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('not-null', false);
                  return undefined;
                }

            });
        }
    };
});
            
angular.module('app.directives').directive('mmRangeValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmRangeValidator'.length) == 'mmRangeValidator' && k.length > 'mmRangeValidator'.length) {
                        arg = k.substring('mmRangeValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(value, args) {
    if(parseFloat(value)==null || parseFloat(value)==NaN)
        return false;
    return parseFloat(value)>=parseInt(args.min) && parseFloat(value)<=parseInt(args.max);
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('range', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('range', false);
                  return undefined;
                }

            });
        }
    };
});
            
angular.module('app.directives').directive('mmNullValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmNullValidator'.length) == 'mmNullValidator' && k.length > 'mmNullValidator'.length) {
                        arg = k.substring('mmNullValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(v) {
    return v == null
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('null', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('null', false);
                  return undefined;
                }

            });
        }
    };
});
            
angular.module('app.directives').directive('mmSizeValidator', function(){
    return {
        restrict: 'A',
        require: 'ngModel',
        link: function(scope, elm, attrs, ctrl) {
            console.log(attrs);

            ctrl.$parsers.unshift(function(viewValue) {

                var o = {};

                for(k in attrs) {
                    if( k.substring(0, 'mmSizeValidator'.length) == 'mmSizeValidator' && k.length > 'mmSizeValidator'.length) {
                        arg = k.substring('mmSizeValidator'.length);
                        o[arg.toLowerCase()] = attrs[k];
                    }
                }

                ;

                function validate(value, args) {
    if(value == null)
        if(args.min > 0)
            return false;
        return true;
    var re = value.length >= parseInt(args.min) && value.length <= parseInt(args.max);
    return re;
}

                ;

                var result = validate(viewValue, o);

                if (result) {
                  ctrl.$setValidity('size', true);
                  return viewValue;
                } else {
                  ctrl.$setValidity('size', false);
                  return undefined;
                }

            });
        }
    };
});
            angular.module('app.directives').directive("mmNotImplemented", function(directiveService) {
  return {
    restrict: "A",
    scope: {},
    link: function(scope, elem, attrs) {
      elem.css('opacity', '0.25');
      return elem.bind('click', function(e) {
        e.preventDefault();
        e.stopPropagation();
        return false;
      });
    }
  };
});angular.module('app.directives').directive("mmAwacRepetitionName", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-name.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacQuestion", function(directiveService, translationService, $compile, $timeout, modalService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '=',
      ngCondition: '=',
      ngRepetitionMap: '=',
      ngAggregation: '=',
      ngTabSet: '=',
      ngTab: '=',
      ngMapIndex: '=',
      ngOptional: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-question.html",
    replace: true,
    compile: function() {
      return {
        post: function(scope, element) {
          directiveService.autoScopeImpl(scope);
          /*
          if scope.$parent.$parent? && scope.$parent.$parent.getQuestionSetCode?
             console.log scope.getQuestionCode()+"/2=>"+scope.$parent.$parent.$index+"/"+scope.$parent.$parent.getQuestionSetCode()
          if scope.$parent.$parent.$parent.$parent? && scope.$parent.$parent.$parent.$parent.$$childScopeClass.getQuestionSetCode?
              console.log scope.getQuestionCode()+"/4=>"+scope.$parent.$parent.$parent.$parent.$index+"/"+scope.$parent.$parent.$parent.$parent.$$childScopeClass.getQuestionSetCode()
          */
          if (scope.$parent.getQuestionSetCode != null) {
            console.log("----------------- : " + scope.getQuestionCode());
            console.log(scope.$parent.getQuestionSetCode());
          }
          scope.$watch('ngAggregation', function() {
            if (scope.getAggregation() != null) {
              return scope.getAnswer().value = scope.getAggregation();
            }
          });
          scope.$watch('$parent.o', function() {
            scope.getTemplate(true);
            return scope.getTemplate(false);
          });
          scope.getTemplate = function(dataToCompare) {
            var answerType, directive, directiveName, isAggregation, params, toCompare;
            if ($('.inject-data:first', element).html() === '') {
              toCompare = "";
              isAggregation = "false";
              directiveName = "";
              if (dataToCompare === true) {
                toCompare = "true";
              } else {
                toCompare = "false";
              }
              if (scope.getAggregation() != null) {
                isAggregation = "true";
              }
              if (scope.getQuestion() !== null) {
                answerType = scope.getQuestion().answerType;
                if (answerType === 'BOOLEAN') {
                  directiveName = "boolean-question";
                } else if (answerType === 'INTEGER') {
                  directiveName = "integer-question";
                } else if (answerType === 'DOUBLE') {
                  if (!scope.getQuestion().unitCategoryId) {
                    directiveName = "real-question";
                  } else {
                    directiveName = "real-with-unit-question";
                  }
                } else if (answerType === 'PERCENTAGE') {
                  directiveName = "percentage-question";
                } else if (answerType === 'STRING') {
                  directiveName = "string-question";
                } else if (answerType === 'VALUE_SELECTION') {
                  directiveName = "select-question";
                } else if (answerType === 'DOCUMENT') {
                  directiveName = "document-question";
                } else if (answerType === 'DATE_TIME') {
                  directiveName = "date-question";
                } else {
                  console.log("TYPE NOT FOUND: " + answerType + " for question " + scope.getQuestion().code);
                }
                params = " ";
                params += " ng-data-to-compare=\"" + toCompare + "\"";
                params += " ng-is-aggregation=\"" + isAggregation + "\"";
                directive = $compile("<mm-awac-" + directiveName + params + " ></mm-awac-" + directiveName + ">")(scope);
                if (dataToCompare === true) {
                  return $('.inject-data-to-compare:first', element).append(directive);
                } else {
                  return $('.inject-data:first', element).append(directive);
                }
              }
            }
          };
          scope.getQuestion = function() {
            return scope.$parent.getQuestion(scope.getQuestionCode());
          };
          scope.hasDescription = function() {
            return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
          };
          scope.getAnswer = function(forDataToCompare) {
            if (forDataToCompare == null) {
              forDataToCompare = false;
            }
            if (forDataToCompare) {
              return scope.$parent.getAnswerToCompare(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getMapIndex());
            } else {
              if ((scope.ngAggregation != null) && scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab()).value === null) {
                scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab(), scope.getOptional()).value = scope.getAggregation();
                scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab(), scope.getOptional()).isAggregation = true;
              }
              return scope.$parent.getAnswerOrCreate(scope.getQuestionCode(), scope.getRepetitionMap(), scope.getTabSet(), scope.getTab(), scope.getOptional());
            }
          };
          scope.getUnitCategories = function() {
            return scope.$parent.getUnitCategories(scope.getQuestionCode());
          };
          scope.getCodeList = function() {
            return scope.$parent.getCodeList(scope.getQuestionCode());
          };
          scope.displayOldDatas = function() {
            if (scope.$parent.dataToCompare === null) {
              return false;
            }
            return true;
          };
          scope.edited = function() {
            if (scope.getAggregation() != null) {
              return;
            } else {
              if (scope.getAnswer().value !== null) {
                if (scope.getAnswer().value.length === 0) {
                  scope.getAnswer().value = null;
                }
              }
              scope.getAnswer().wasEdited = true;
              return scope.getAnswer().lastUpdateUser = scope.$root.currentPerson;
            }
          };
          scope.$watch('ngCondition', function(n, o) {
            if (n !== o) {
              return scope.$root.$broadcast('CONDITION');
            }
          });
          scope.$on('FORM_LOADING_FINISH', function(event, args) {
            return scope.testVisibility(element);
          });
          scope.$on('CONDITION', function(event, args) {
            return scope.testVisibility(element);
          });
          scope.firstComputecondition = true;
          scope.testVisibility = function(elementToTest) {
            if (elementToTest.hasClass('condition-false') === true || ((scope.getCondition() != null) && scope.getCondition() === false)) {
              if (scope.getAnswer().hasValidCondition !== false) {
                scope.getAnswer().hasValidCondition = false;
                if (scope.getAnswer().value !== null) {
                  if (scope.$parent.loading === false) {
                    scope.getAnswer().value = null;
                    scope.edited();
                    scope.$root.$broadcast('CONDITION');
                  } else {
                    scope.getAnswer().wasEdited = false;
                  }
                }
              }
              return false;
            }
            if ((elementToTest.parent() != null) && elementToTest.parent().length > 0 && elementToTest.parent()[0].tagName !== 'BODY') {
              return scope.testVisibility(elementToTest.parent());
            } else {
              if (scope.getAnswer().hasValidCondition !== true) {
                scope.getAnswer().hasValidCondition = true;
                if (scope.getQuestion() !== null && scope.getQuestion().defaultValue !== null && scope.getAnswer().value === null) {
                  scope.getAnswer().value = scope.getQuestion().defaultValue;
                  if (scope.$parent.loading === false) {
                    scope.edited();
                  }
                }
              }
              return true;
            }
          };
          scope.getUserName = function(forDataToCompare, initialOnly) {
            var initial, name, user;
            user = null;
            if (forDataToCompare === true) {
              if (scope.displayOldDatas() && scope.getAnswer(true) !== null) {
                user = scope.getAnswer(true).lastUpdateUser;
              }
            } else {
              if (scope.getAnswer() !== null) {
                user = scope.getAnswer().lastUpdateUser;
              }
            }
            if (user === null) {
              return "";
            }
            if (initialOnly) {
              initial = user.firstName.substring(0, 1) + user.lastName.substring(0, 1);
              return initial;
            } else {
              name = user.firstName + " " + user.lastName;
              return name;
            }
          };
          scope.getStatusClass = function() {
            var answer;
            if (scope.getAggregation() != null) {
              return;
            }
            answer = scope.getAnswer();
            if (answer.value !== null) {
              if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
                return 'answer_temp';
              }
              return 'answer';
            } else {
              if (scope.getQuestion() !== null && scope.getOptional() === true) {
                return "";
              }
              if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
                return 'pending_temp';
              }
              return 'pending';
            }
          };
          scope.copyDataToCompare = function() {
            if (scope.getAnswer(true) !== null) {
              scope.getAnswer().value = scope.getAnswer(true).value;
              if (scope.getAnswer(true).unitCode != null) {
                scope.getAnswer().unitCode = scope.getAnswer(true).unitCode;
              }
              if (scope.getAnswer(true).comment != null) {
                scope.getAnswer().comment = scope.getAnswer(true).comment;
              }
              return scope.edited();
            }
          };
          scope.logQuestionCode = function() {
            console.log(scope.getQuestionCode() + ",value:" + scope.getAnswer().value + ",wasEdited:" + scope.getAnswer().wasEdited);
            return console.log(scope.getAnswer());
          };
          scope.errorMessage = "";
          scope.getIcon = function() {
            if (scope.ngAggregation != null) {
              return 'glyphicon-cog';
            }
            if ((scope.getQuestion() != null) && scope.getQuestion().answerType === 'DOCUMENT') {
              return 'glyphicon-file';
            }
            if (scope.getOptional() === true) {
              return 'glyphicon-paperclip';
            }
            return 'glyphicon-share-alt';
          };
          scope.setErrorMessage = function(errorMessage) {
            scope.errorMessage = errorMessage;
            if (scope.lastTimeOut != null) {
              $timeout.cancel(scope.lastTimeOut);
            }
            return scope.lastTimeOut = $timeout(function() {
              scope.errorMessage = "";
              return scope.lastTimeOut = null;
            }, 2000);
          };
          scope.saveComment = function(comment) {
            scope.getAnswer().comment = comment;
            return scope.getAnswer().wasEdited = true;
          };
          scope.editComment = function(canBeEdited) {
            var args;
            if (canBeEdited == null) {
              canBeEdited = true;
            }
            args = {};
            args.comment = scope.getAnswer().comment;
            args.save = scope.saveComment;
            args.canBeEdited = canBeEdited;
            return modalService.show(modalService.QUESTION_COMMENT, args);
          };
          scope.isDisabled = function() {
            if (scope.$parent.isQuestionLocked(scope.getQuestionCode()) || scope.$parent.isQuestionValidate(scope.getQuestionCode()) || scope.$root.instanceName === 'verification') {
              return true;
            }
            return false;
          };
          return scope.getUserClass = function() {
            if (scope.getAnswer() !== null && (scope.$parent.getValidatorByQuestionCode(scope.getQuestionCode()) != null)) {
              if (scope.$parent.getValidatorByQuestionCode(scope.getQuestionCode()).identifier === scope.getAnswer().lastUpdateUser.identifier) {
                return 'user_icon_red';
              }
              return 'user_icon_green';
            }
            return '';
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacSubTitle", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-sub-title.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRealQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-real-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
      return scope.setErrorMessage = function(errorMessage) {
        return scope.$parent.setErrorMessage(errorMessage);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacPercentageQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-percentage-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
      return scope.setErrorMessage = function(errorMessage) {
        return scope.$parent.setErrorMessage(errorMessage);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacIntegerQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-integer-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
      return scope.setErrorMessage = function(errorMessage) {
        return scope.$parent.setErrorMessage(errorMessage);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRepetitionAddButton", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionSetCode: '=',
      ngIteration: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-add-button.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.isQuestionLocked = function() {
        if ((scope.$parent.getQuestionSetLocker(scope.getQuestionSetCode()) != null) || (scope.$parent.getValidatorByQuestionCode(scope.getQuestionSetCode()) != null)) {
          return true;
        }
        return false;
      };
      return scope.addIteration = function() {
        return scope.$parent.addIteration(scope.getQuestionSetCode(), scope.getIteration());
      };
    }
  };
});angular.module('app.directives').directive("mmAwacStringQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-string-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        return scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
    }
  };
});angular.module('app.directives').directive("mmAwacRealWithUnitQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-real-with-unit-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
        scope.$watch('getAnswer().unitId', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
      scope.getUnits = function() {
        var unitCategory;
        unitCategory = scope.$parent.getUnitCategories();
        if (unitCategory === null) {
          return null;
        }
        return unitCategory.units;
      };
      return scope.setErrorMessage = function(errorMessage) {
        return scope.$parent.setErrorMessage(errorMessage);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRepetitionQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionSetCode: '=',
      ngIteration: '=',
      ngRepetitionMap: '=',
      ngCondition: '=',
      ngTabSet: '=',
      ngTab: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-repetition-question.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      if (scope.getTabSet() != null) {
        scope.$parent.addTabSet(scope.getTabSet(), scope.getTab(), scope.getRepetitionMap());
      }
      scope.isQuestionLocked = function() {
        if ((scope.$parent.getQuestionSetLocker(scope.getQuestionSetCode()) != null) || (scope.$parent.getValidatorByQuestionCode(scope.getQuestionSetCode()) != null)) {
          return true;
        }
        return false;
      };
      scope.getQuestionSet = function() {
        return scope.$parent.getQuestionSet(scope.getQuestionSetCode());
      };
      scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
      scope.removeAnwser = function() {
        return scope.$parent.removeIteration(scope.getQuestionSetCode(), scope.getIteration(), scope.getRepetitionMap());
      };
      return scope.$watch('ngCondition', function() {
        return scope.$root.$broadcast('CONDITION');
      });
    }
  };
});angular.module('app.directives').directive("mmAwacBooleanQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-boolean-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.radioName = "name_" + (Math.floor(Math.random() * 1000000));
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        return scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
    }
  };
});angular.module('app.directives').directive("mmAwacSelectQuestion", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-select-question.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.getDisabled = function() {
        return scope.$parent.isDisabled();
      };
      scope.getQuestionCode = function() {
        return scope.$parent.getQuestionCode();
      };
      scope.getAnswer = function() {
        return scope.$parent.getAnswer(scope.getDataToCompare());
      };
      if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
        scope.$watch('getAnswer().value', function(o, n) {
          if ("" + n !== "" + o) {
            return scope.$parent.edited();
          }
        });
      }
      return scope.getOptions = function() {
        var codeList;
        codeList = scope.$parent.getCodeList();
        if (codeList === null) {
          return null;
        }
        return codeList.codeLabels;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacDocumentQuestion", function(directiveService, translationService, $upload, messageFlash, modalService, $filter) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-document-question.html",
    replace: true,
    compile: function() {
      return {
        pre: function(scope) {
          directiveService.autoScopeImpl(scope);
          scope.getDisabled = function() {
            return scope.$parent.isDisabled();
          };
          scope.getQuestionCode = function() {
            return scope.$parent.getQuestionCode();
          };
          scope.getAnswer = function() {
            return scope.$parent.getAnswer(scope.getDataToCompare());
          };
          if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
            scope.$watch('getAnswer().value', function(o, n) {
              if ("" + n !== "" + o) {
                return scope.$parent.edited();
              }
            });
          }
          scope.inDownload = false;
          scope.percent = 0;
          scope.$watch('percent', function() {
            var _ref;
            return scope.style = {
              "width": scope.percent + "%",
              "color": (_ref = scope.percent > 50) != null ? _ref : {
                "white": "black"
              }
            };
          });
          scope.getDisabled = function() {
            return scope.$parent.isDisabled();
          };
          scope.openDocumentManager = function() {
            var args;
            if (scope.getAnswer() !== null) {
              args = {};
              args.listDocuments = scope.getAnswer().value;
              args.readyOnly = scope.getDataToCompare() === true || scope.getIsAggregation() === true || scope.getDisabled() === true;
              args.wasEdited = function() {
                return scope.$parent.edited();
              };
              return modalService.show(modalService.DOCUMENT_MANAGER, args);
            }
          };
          scope.getFileNumber = function() {
            if (scope.getAnswer() === null || scope.getAnswer().value === null || scope.getAnswer().value === void 0) {
              return 0;
            }
            return Object.keys(scope.getAnswer().value).length;
          };
          return scope.onFileSelect = function($files) {
            var file, i;
            scope.inDownload = true;
            i = 0;
            while (i < $files.length) {
              file = $files[i];
              scope.upload = $upload.upload({
                url: "/awac/file/upload/",
                data: {
                  myObj: scope.myModelObj
                },
                file: file
              }).progress(function(evt) {
                scope.percent = parseInt(100.0 * evt.loaded / evt.total);
                return;
              }).success(function(data, status, headers, config) {
                var fileName;
                scope.percent = 0;
                scope.inDownload = false;
                fileName = data.name;
                messageFlash.displaySuccess($filter('translateText')('FILE_UPLOAD_SUCCESS'));
                if (scope.getAnswer().value === null || scope.getAnswer().value === void 0) {
                  scope.getAnswer().value = {};
                }
                scope.getAnswer().value[data.id] = data.name;
                scope.$parent.edited();
                return;
              }).error(function(data, status, headers, config) {
                scope.percent = 0;
                scope.inDownload = false;
                messageFlash.displayError($filter('translateText')('FILE_UPLOAD_FAIL'));
                return;
              });
              i++;
            }
            return;
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacDateQuestion", function(directiveService, translationService, generateId, $filter) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngDataToCompare: '=',
      ngIsAggregation: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-date-question.html",
    replace: true,
    compile: function() {
      return {
        pre: function(scope) {
          directiveService.autoScopeImpl(scope);
          scope.id = generateId.generate();
          return scope.idHtag = '#' + scope.id;
        },
        post: function(scope, $filter) {
          directiveService.autoScopeImpl(scope);
          scope.$watch('result', function(o, n) {
            if (o !== n) {
              if (scope.getAnswer() != null) {
                return scope.getAnswer().value = scope.result.getTime();
              }
            }
          });
          scope.getDisabled = function() {
            return scope.$parent.isDisabled();
          };
          scope.getQuestionCode = function() {
            return scope.$parent.getQuestionCode();
          };
          scope.getAnswer = function() {
            return scope.$parent.getAnswer(scope.getDataToCompare());
          };
          if (scope.getDataToCompare() === false && scope.getIsAggregation() === false) {
            scope.result = new Date(scope.getAnswer().value);
            if (scope.result.getTime() === 0) {
              scope.result = new Date();
            }
            scope.$watch('getAnswer().value', function(o, n) {
              if ("" + n !== "" + o) {
                return scope.$parent.edited();
              }
            });
          }
          return scope.setErrorMessage = function(errorMessage) {
            return scope.$parent.setErrorMessage(errorMessage);
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacSubSubTitle", function(directiveService, translationService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngQuestionCode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-sub-sub-title.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.hasDescription = function() {
        return translationService.get(scope.getQuestionCode() + '_DESC') !== null;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacBlock", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngCondition: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-block.html",
    replace: true,
    transclude: true,
    link: function(scope, element) {
      directiveService.autoScopeImpl(scope);
      return scope.$watch('ngCondition', function(n, o) {
        return scope.$root.$broadcast('CONDITION');
      });
    }
  };
});angular.module('app.directives').directive("mmAwacSection", function(directiveService, downloadService, messageFlash, modalService, $location, $filter) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngTitleCode: '=',
      ngMode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-section.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.decorateMode = function(v) {
        if (v) {
          return 'element_' + v;
        } else {
          return 'element_table';
        }
      };
      scope.lock = function() {
        var data;
        if ((!scope.isLocked() || scope.isLockedByMyself() || scope.$root.currentPerson.isAdmin) && !scope.isValidate() && scope.$root.closedForms === false) {
          data = {};
          data.questionSetKey = scope.getTitleCode();
          data.periodCode = scope.$root.periodSelectedKey;
          data.scopeId = scope.$root.scopeSelectedId;
          data.lock = scope.isLocked() ? false : true;
          return downloadService.postJson('/awac/answer/lockQuestionSet', data, function(result) {
            if (result.success) {
              return scope.$parent.lockQuestionSet(scope.getTitleCode(), data.lock);
            }
          });
        }
      };
      scope.isLockedByMyself = function() {
        if (scope.$parent.getQuestionSetLocker(scope.getTitleCode()) != null) {
          if (scope.$parent.getQuestionSetLocker(scope.getTitleCode()).identifier === scope.$root.currentPerson.identifier) {
            return true;
          }
        }
        return false;
      };
      scope.isLocked = function() {
        if (scope.$parent.getQuestionSetLocker(scope.getTitleCode()) != null) {
          return true;
        }
        return false;
      };
      scope.getLocker = function() {
        return scope.$parent.getQuestionSetLocker(scope.getTitleCode());
      };
      scope.getLockClass = function() {
        if (scope.isLocked()) {
          if ((scope.isLockedByMyself() || scope.$root.currentPerson.isAdmin) && scope.isValidate() === false && scope.$root.closedForms === false) {
            return 'lock_close_by_myself';
          }
          return 'lock_close';
        } else if (scope.isValidate()) {
          return 'lock_open_disabled';
        } else {
          return 'lock_open';
        }
      };
      scope.valide = function() {
        var data;
        if (scope.$root.closedForms === false) {
          if (scope.$parent.validNavigation().valid === false) {
            return messageFlash.displayError($filter('translateText')('SAVE_BEFORE_LOCK_OR_VALID'));
          } else {
            if (!scope.isValidate() || scope.isValidateByMyself() || scope.$root.currentPerson.isAdmin) {
              if (!scope.$root.verificationRequest || (scope.$root.verificationRequest.status === 'VERIFICATION_STATUS_CORRECTION' && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'rejected')) {
                data = {};
                data.questionSetKey = scope.getTitleCode();
                data.periodCode = scope.$root.periodSelectedKey;
                data.scopeId = scope.$root.scopeSelectedId;
                data.lock = scope.isValidate() ? false : true;
                return downloadService.postJson('/awac/answer/validateQuestionSet', data, function(result) {
                  if (result.success) {
                    scope.$parent.validateQuestionSet(scope.getTitleCode(), data.lock);
                    return scope.$root.testCloseable();
                  }
                });
              }
            }
          }
        }
      };
      scope.isValidateByMyself = function() {
        if (scope.$parent.getQuestionSetValidator(scope.getTitleCode()) != null) {
          if (scope.$parent.getQuestionSetValidator(scope.getTitleCode()).identifier === scope.$root.currentPerson.identifier) {
            return true;
          }
        }
        return false;
      };
      scope.isValidate = function() {
        if (scope.$parent.getQuestionSetValidator(scope.getTitleCode()) != null) {
          return true;
        }
        return false;
      };
      scope.getValidator = function() {
        return scope.$parent.getQuestionSetValidator(scope.getTitleCode());
      };
      scope.getValidatorName = function() {
        if (scope.getValidator() != null) {
          scope.getValidator();
          return scope.getValidator().firstName + " " + scope.getValidator().lastName;
        }
      };
      scope.getLockerName = function() {
        if (scope.getLocker() != null) {
          return scope.getLocker().firstName + " " + scope.getLocker().lastName;
        }
      };
      scope.getValidateClass = function() {
        if (scope.isValidate()) {
          if (scope.$root.closedForms === false) {
            if (scope.isValidateByMyself() || scope.$root.currentPerson.isAdmin) {
              if (!scope.$root.verificationRequest || (scope.$root.verificationRequest.status === 'VERIFICATION_STATUS_CORRECTION' && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'rejected')) {
                return 'validate_by_myself';
              }
            }
          }
          return 'validated';
        } else {
          return 'validate_no_valid';
        }
      };
      scope.verification = function(valid) {
        var comment, data;
        data = {
          questionSetKey: scope.getTitleCode(),
          periodKey: scope.$root.periodSelectedKey,
          scopeId: scope.$root.scopeSelectedId,
          verification: {
            status: 'approved'
          }
        };
        if (valid) {
          return downloadService.postJson('/awac/verification/verify', data, function(result) {
            if (result.success) {
              scope.$parent.verifyQuestionSet(scope.getTitleCode(), result.data);
              return scope.$emit('TEST_CLOSING_VALIDATION');
            }
          });
        } else {
          if (scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) {
            comment = scope.$parent.getQuestionSetVerification(scope.getTitleCode()).comment;
          }
          data = {
            questionSetCode: scope.getTitleCode(),
            refreshVerificationStatus: scope.refreshVerificationStatus,
            comment: comment
          };
          return modalService.show(modalService.VERIFICATION_REJECT, data);
        }
      };
      scope.refreshVerificationStatus = function(status) {
        return scope.$parent.verifyQuestionSet(scope.getTitleCode(), status);
      };
      scope.getVerificationClass = function(valid, disabled) {
        if (disabled == null) {
          disabled = false;
        }
        if (disabled === true) {
          if ((scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'approved') {
            return 'verified_valid';
          } else if ((scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'rejected') {
            return 'verified_rejected';
          } else {
            return 'verified_unverified';
          }
        } else {
          if (valid) {
            if ((scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'approved') {
              return 'verification_approved';
            } else {
              return 'verification_approval';
            }
          } else {
            if ((scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status === 'rejected') {
              return 'verification_rejected';
            } else {
              return 'verification_reject';
            }
          }
        }
      };
      scope.displayVerificationRejectedMessage = function() {
        var data;
        if ((scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) && scope.$parent.getQuestionSetVerification(scope.getTitleCode()).status !== 'approved') {
          data = {
            readOnly: true,
            comment: scope.$parent.getQuestionSetVerification(scope.getTitleCode()).comment
          };
          return modalService.show(modalService.VERIFICATION_REJECT, data);
        }
      };
      scope.getVerifier = function() {
        if (scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) {
          return scope.$parent.getQuestionSetVerification(scope.getTitleCode()).verifier;
        }
        return null;
      };
      scope.getVerifierName = function() {
        if (scope.$parent.getQuestionSetVerification(scope.getTitleCode()) != null) {
          return scope.$parent.getQuestionSetVerification(scope.getTitleCode()).verifier.firstName + " " + scope.$parent.getQuestionSetVerification(scope.getTitleCode()).verifier.lastName;
        }
      };
      return scope.isVerificationDisabled = function() {
        if ($location.path() === '/submit') {
          return true;
        }
        return false;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacResultTableSimple", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '=',
      ngTypeMap: '=',
      ngIdealMap: '=',
      ngTypeColor: '=',
      ngIdealColor: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-result-table-simple.html",
    replace: true,
    transclude: true,
    link: function(scope, element) {
      var idealResultLabelKeys, typicalResultLabelKeys;
      directiveService.autoScopeImpl(scope);
      typicalResultLabelKeys = {
        household: 'TYPICAL_HOUSEHOLD_TITLE',
        littleemitter: 'TYPICAL_LITTLEEMITTER_TITLE',
        event: 'TYPICAL_EVENT_TITLE'
      };
      idealResultLabelKeys = {
        household: 'IDEAL_HOUSEHOLD_TITLE',
        littleemitter: 'IDEAL_LITTLEEMITTER_TITLE',
        event: 'IDEAL_EVENT_TITLE'
      };
      scope.showAll = true;
      scope.getLeftTotal = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.leftScope1Value;
          total += rl.leftScope2Value;
          total += rl.leftScope3Value;
        }
        return total;
      };
      scope.getRightTotal = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.rightScope1Value;
          total += rl.rightScope2Value;
          total += rl.rightScope3Value;
        }
        return total;
      };
      scope.getTypicalResultTotal = function() {
        var k, total, v, _ref;
        if (!scope.ngTypeMap) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngTypeMap;
        for (k in _ref) {
          v = _ref[k];
          total += parseFloat(v);
        }
        return total;
      };
      scope.getIdealResultTotal = function() {
        var k, total, v, _ref;
        if (!scope.ngIdealMap) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngIdealMap;
        for (k in _ref) {
          v = _ref[k];
          total += parseFloat(v);
        }
        return total;
      };
      scope.getTypicalResultLabelKey = function() {
        return typicalResultLabelKeys[scope.$root.instanceName];
      };
      return scope.getIdealResultLabelKey = function() {
        return idealResultLabelKeys[scope.$root.instanceName];
      };
    }
  };
});angular.module('app.directives').directive("focusMe", function($timeout, $parse) {
  return {
    restrict: 'A',
    scope: {
      focusMe: '='
    },
    link: function(scope, element, attrs) {
      scope.$watch('focusMe', function() {
        if (scope.focusMe === true) {
          return element[0].focus();
        }
      });
      return;
    }
  };
});angular.module('app.directives').directive("numbersOnly", function($filter, $log, translationService, $locale) {
  return {
    restrict: 'A',
    require: "ngModel",
    link: function(scope, element, attrs, modelCtrl) {
      var convertToFloat, convertToString, found;
      scope.format = function(v) {
        return v;
      };
      scope.parse = modelCtrl.$rollbackViewValue;
      modelCtrl.$parsers.unshift(function(v) {
        return scope.parse(v);
      });
      modelCtrl.$formatters.unshift(function(v) {
        return scope.format(v);
      });
      convertToString = function(value, decimal) {
        var formats;
        if (!(value != null) || value === "" || isNaN(value)) {
          return "";
        }
        value = parseFloat(value).toFixed(decimal);
        formats = $locale.NUMBER_FORMATS;
        return value.toString().split('.').join(formats.DECIMAL_SEP);
      };
      convertToFloat = function(viewValue) {
        var formats, sep, value;
        if (viewValue === "" || viewValue === null) {
          return null;
        }
        formats = $locale.NUMBER_FORMATS;
        sep = formats.DECIMAL_SEP;
        value = viewValue.split(sep).join('.');
        return value;
      };
      if (attrs.numbersOnly === '') {
        found = true;
        scope.format = function(v) {
          return v;
        };
        scope.parse = function(v) {
          return v;
        };
      }
      return scope.$watch(attrs.numbersOnly, function() {
        scope.lastValidValue = "";
        found = false;
        if (attrs.numbersOnly === "integer") {
          found = true;
          scope.format = function(modelValue) {
            var v;
            v = modelValue;
            return convertToString(v, 0);
          };
          scope.parse = function(viewValue) {
            var errorMessage, parseResult, regexFloat;
            errorMessage = null;
            viewValue = convertToFloat(viewValue.trim());
            if (viewValue === null || viewValue === '') {
              return null;
            }
            regexFloat = new RegExp("^(\\-|\\+)?([0-9]+|Infinity)?$");
            if (regexFloat.test(viewValue)) {
              parseResult = viewValue;
            } else {
              parseResult = null;
              errorMessage = $filter('translateText')('ONLY_INTEGER');
            }
            if (scope.setErrorMessage != null) {
              scope.setErrorMessage(errorMessage);
            }
            return parseResult;
          };
        }
        if (attrs.numbersOnly === "double") {
          found = true;
          scope.format = function(modelValue) {
            var v;
            v = modelValue;
            return convertToString(v, 3);
          };
          scope.parse = function(viewValue) {
            var errorMessage, parseResult, regexFloat;
            errorMessage = null;
            viewValue = convertToFloat(viewValue.trim());
            if (viewValue === null || viewValue === '') {
              return null;
            }
            regexFloat = new RegExp("^(\\-|\\+)?([0-9]+(\\.[0-9]*)?|Infinity)?$");
            if (regexFloat.test(viewValue)) {
              parseResult = viewValue;
            } else {
              parseResult = null;
              errorMessage = $filter('translateText')('ONLY_NUMBER');
            }
            if (scope.setErrorMessage != null) {
              scope.setErrorMessage(errorMessage);
            }
            return parseResult;
          };
        }
        if (attrs.numbersOnly === "percent") {
          found = true;
          scope.format = function(modelValue) {
            var v;
            v = modelValue * 100.0;
            return convertToString(v, 3);
          };
          scope.parse = function(viewValue) {
            var errorMessage, parseResult, regexFloat;
            errorMessage = null;
            viewValue = convertToFloat(viewValue.trim());
            if (viewValue === '') {
              return null;
            }
            regexFloat = new RegExp("^(\\-|\\+)?([0-9]+(\\.[0-9]*)?|Infinity)?$");
            if (regexFloat.test(viewValue)) {
              parseResult = viewValue * 0.01;
            } else {
              parseResult = null;
              errorMessage = $filter('translateText')('ONLY_NUMBER');
            }
            if (scope.setErrorMessage != null) {
              scope.setErrorMessage(errorMessage);
            }
            return parseResult;
          };
        }
        if (attrs.numbersOnly === '') {
          found = true;
          scope.format = function(v) {
            return v;
          };
          scope.parse = function(v) {
            return v;
          };
        }
        if (found) {
          if (modelCtrl.$modelValue != null) {
            modelCtrl.$setViewValue(scope.format(modelCtrl.$modelValue));
            return modelCtrl.$render();
          }
        }
      });
    }
  };
});angular.module('app.directives').directive("ngEnter", function() {
  return function(scope, element, attrs) {
    return element.bind("keydown keypress", function(event) {
      if (event.which === 13 && event.target.tagName !== 'TEXTAREA') {
        scope.$apply(function() {
          return scope.$eval(attrs.ngEnter);
        });
        return event.preventDefault();
      }
    });
  };
});angular.module('app.directives').directive("mmForceNumbers", function() {
  return {
    require: "ngModel",
    link: function(scope, element, attrs, modelCtrl) {
      modelCtrl.$parsers.push(function(inputValue) {
        var transformedInput;
        if (inputValue == null) {
          return "";
        }
        transformedInput = inputValue.replace(/[^0-9+.]/g, "");
        if (transformedInput !== inputValue) {
          modelCtrl.$setViewValue(transformedInput);
          modelCtrl.$render();
        }
        return transformedInput;
      });
      return;
    }
  };
});angular.module('app.directives').directive("inputDate", function($filter) {
  return {
    restrict: 'A',
    require: "ngModel",
    link: function(scope, element, attrs, modelCtrl) {
      return modelCtrl.$formatters.unshift(function(modelValue) {
        return $filter('date')(modelValue, 'yyyy-MM-dd hh:mm a');
      });
    }
  };
});angular.module('app.directives').directive("ngEscape", function() {
  return function(scope, element, attrs) {
    return element.bind("keydown keypress", function(event) {
      if (event.which === 27) {
        scope.$apply(function() {
          return scope.$eval(attrs.ngEscape);
        });
        return event.preventDefault();
      }
    });
  };
});angular.module('app.directives').directive("mmButton", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngCode: '=',
      ngIcon: '='
    }),
    templateUrl: "$/angular/templates/mm-button.html",
    replace: true,
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmFieldDate", function(directiveService, $filter, generateId) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngInfo: '='
    }),
    templateUrl: "$/angular/templates/mm-field-date.html",
    replace: true,
    transclude: true,
    compile: function() {
      return {
        pre: function(scope) {
          scope.id = generateId.generate();
          return scope.idHtag = '#' + scope.id;
        },
        post: function(scope) {
          directiveService.autoScopeImpl(scope);
          scope.result = null;
          scope.$watch('result', function() {
            return scope.resultFormated = $filter('date')(scope.result, 'yyyy-MM-dd');
          });
          scope.$watch('getInfo().field', function() {
            if (scope.getInfo().field != null) {
              return scope.result = new Date(Number(scope.getInfo().field));
            }
          });
          scope.$watch('result', function() {
            if (scope.result != null) {
              scope.getInfo().field = scope.result.getTime();
            } else {
              scope.getInfo().field = null;
            }
            return scope.isValid();
          });
          scope.isValid = function() {
            var isValid;
            console.log("Validating date = " + scope.getInfo().field);
            if (scope.getInfo().disabled === true || scope.getInfo().hidden === true) {
              scope.getInfo().isValid = true;
              return;
            }
            isValid = true;
            if (!(scope.getInfo().field != null)) {
              isValid = false;
            }
            if (scope.getInfo().minValue != null) {
              isValid = scope.getInfo().field > scope.getInfo().minValue.getTime();
            }
            if (scope.getInfo().maxValue != null) {
              isValid = scope.getInfo().field < scope.getInfo().maxValue.getTime();
            }
            scope.getInfo().isValid = isValid;
            return;
          };
          scope.isValid();
          return scope.logField = function() {
            return console.log(scope.getInfo());
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacEventMenu", function() {
  return {
    restrict: "E",
    templateUrl: "$/angular/templates/mm-awac-event-menu.html",
    transclude: true,
    replace: true,
    link: function() {}
  };
});angular.module('app.directives').directive("mmAwacLittleemitterMenu", function() {
  return {
    restrict: "E",
    templateUrl: "$/angular/templates/mm-awac-littleemitter-menu.html",
    transclude: true,
    replace: true,
    link: function() {}
  };
});angular.module('app.directives').directive("mmAwacHouseholdMenu", function() {
  return {
    restrict: "E",
    templateUrl: "$/angular/templates/mm-awac-household-menu.html",
    transclude: true,
    replace: true,
    link: function() {}
  };
});angular.module('app.directives').directive("mmAwacEnterpriseMenu", function() {
  return {
    restrict: "E",
    templateUrl: "$/angular/templates/mm-awac-enterprise-menu.html",
    transclude: true,
    replace: true,
    link: function() {}
  };
});angular.module('app.directives').directive("mmAwacMunicipalityMenu", function() {
  return {
    restrict: "E",
    templateUrl: "$/angular/templates/mm-awac-municipality-menu.html",
    transclude: true,
    replace: true,
    link: function() {}
  };
});angular.module('app.directives').directive("mmAwacTabProgressBar", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngValue: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-tab-progress-bar.html",
    replace: true,
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      scope.pg = null;
      scope.color = null;
      return scope.$watch('value', function(v) {
        if (v < 0) {
          v = 0;
        }
        if (v > 100) {
          v = 100;
        }
        if (v >= 66) {
          scope.color = 'green';
        } else {
          if (v >= 33) {
            scope.color = 'orange';
          } else {
            scope.color = 'red';
          }
        }
        return scope.pg = v;
      });
    }
  };
});angular.module('app.directives').directive("mmAwacGraphDonut", function($sce, $filter) {
  return {
    restrict: "E",
    scope: {
      ngItems: '='
    },
    templateUrl: "$/angular/templates/mm-awac-graph-donut.html",
    replace: true,
    link: function(scope, element) {
      scope.legend = '';
      return scope.$watch('ngItems', function() {
        var color, colorh, ctx, d, data, f, i, myDoughnutChart, total, _len, _len2;
        if (scope.ngItems != null) {
          ctx = $('.holder', element).get(0).getContext('2d');
          data = angular.copy(scope.ngItems);
          f = $filter('numberToI18N');
          total = 0;
          for (i = 0, _len = data.length; i < _len; i++) {
            d = data[i];
            total += d.value;
          }
          for (i = 0, _len2 = data.length; i < _len2; i++) {
            d = data[i];
            color = tinycolor({
              h: 360.0 * i / data.length,
              s: 0.5,
              l: 0.5
            }).toHexString(true);
            colorh = tinycolor({
              h: 360.0 * i / data.length,
              s: 0.75,
              l: 0.66
            }).toHexString(true);
            d.color = color;
            d.highlight = colorh;
            d.label += ' (<b>' + f(100.0 * d.value / total) + '%</b>)';
          }
          myDoughnutChart = new Chart(ctx).Doughnut(data, {
            tooltipTemplate: function(o) {
              return f(100.0 * (o.endAngle - o.startAngle) / (Math.PI * 2)) + "%&nbsp;(" + f(o.value) + "%&nbsp;tCO2e)";
            },
            animation: false,
            legendTemplate: "" + "<% for (var i=0; i<segments.length; i++){%>" + "<div><span class=\"chart-legend-bullet-color\" style=\"background-color:<%=segments[i].fillColor%>\"></span><%if(segments[i].label){%><%=segments[i].label%><%}%></div>" + "<%}%>"
          });
          return scope.legend = $sce.trustAsHtml(myDoughnutChart.generateLegend());
        }
      });
    }
  };
});angular.module('app.directives').directive("mmAwacModalCreateOrEditReductionActionAdvice", function(directiveService, downloadService, translationService, messageFlash, $upload, $window, codeLabelHelper) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-create-or-edit-reduction-action-advice.html",
    controller: function($scope, modalService) {
      var actionAdvice, baseIndicatorCodeLabels, baseIndicatorLabelsByInterfaceType, interfaceTypeCodeLabels, interfaceTypeKeys, _ref, _ref2;
      directiveService.autoScopeImpl($scope);
      interfaceTypeCodeLabels = $scope.getParams().interfaceTypeCodeLabels;
      baseIndicatorCodeLabels = $scope.getParams().baseIndicatorCodeLabels;
      actionAdvice = $scope.getParams().actionAdvice;
      interfaceTypeKeys = ['enterprise', 'municipality', 'household', 'event', 'littleemitter'];
      $scope.interfaceTypeOptions = _.filter(interfaceTypeCodeLabels, function(codeLabel) {
        return _.contains(interfaceTypeKeys, codeLabel.key);
      });
      baseIndicatorLabelsByInterfaceType = {
        enterprise: _.filter(baseIndicatorCodeLabels, function(codeLabel) {
          return codeLabel.key.startsWith("BI_");
        }),
        municipality: _.filter(baseIndicatorCodeLabels, function(codeLabel) {
          return codeLabel.key.startsWith("BICo_");
        }),
        household: _.filter(baseIndicatorCodeLabels, function(codeLabel) {
          return codeLabel.key.startsWith("BIMe_");
        }),
        littleemitter: _.filter(baseIndicatorCodeLabels, function(codeLabel) {
          return codeLabel.key.startsWith("BIPE_");
        }),
        event: _.filter(baseIndicatorCodeLabels, function(codeLabel) {
          return codeLabel.key.startsWith("BIEv_");
        })
      };
      $scope.editMode = !!actionAdvice;
      if ($scope.editMode) {
        $scope.actionAdvice = angular.copy(actionAdvice);
      } else {
        $scope.actionAdvice = {
          interfaceTypeKey: interfaceTypeKeys[0],
          typeKey: '1'
        };
      }
      $scope.interfaceTypeKey = {
        inputName: 'interfaceTypeKey',
        field: $scope.actionAdvice.interfaceTypeKey,
        fieldTitle: "REDUCTION_ACTION_ADVICE_CALCULATOR_FIELD_TITLE"
      };
      $scope.title = {
        inputName: 'title',
        field: $scope.actionAdvice.title,
        fieldTitle: "REDUCTION_ACTION_TITLE_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_TITLE_FIELD_PLACEHOLDER",
        validationRegex: "^.{1,255}$",
        validationMessage: "REDUCTION_ACTION_TITLE_WRONG_LENGTH",
        hideIsValidIcon: true,
        focus: function() {
          return true;
        }
      };
      $scope.typeKey = {
        inputName: 'typeKey',
        field: $scope.actionAdvice.typeKey,
        fieldTitle: "REDUCTION_ACTION_TYPE_FIELD_TITLE"
      };
      $scope.physicalMeasure = {
        inputName: 'physicalMeasure',
        field: $scope.actionAdvice.physicalMeasure,
        fieldTitle: "REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_PLACEHOLDER",
        fieldType: 'textarea'
      };
      $scope.financialBenefit = {
        inputName: 'financialBenefit',
        field: $scope.actionAdvice.financialBenefit,
        numbersOnly: 'double',
        fieldTitle: "REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_PLACEHOLDER"
      };
      $scope.investmentCost = {
        inputName: 'investment',
        field: $scope.actionAdvice.investmentCost,
        numbersOnly: 'double',
        fieldTitle: "REDUCTION_ACTION_INVESTMENT_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_INVESTMENT_FIELD_PLACEHOLDER"
      };
      $scope.expectedPaybackTime = {
        inputName: 'expectedPaybackTime',
        field: $scope.actionAdvice.expectedPaybackTime,
        validationRegex: "^.{0,255}$",
        fieldTitle: "REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.webSite = {
        inputName: 'webSite',
        field: $scope.actionAdvice.webSite,
        validationRegex: "^.{0,255}$",
        fieldTitle: "REDUCTION_ACTION_WEBSITE_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_WEBSITE_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.responsiblePerson = {
        inputName: 'responsiblePerson',
        field: $scope.actionAdvice.responsiblePerson,
        validationRegex: "^.{0,255}$",
        fieldTitle: "REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.comment = {
        inputName: 'comment',
        field: $scope.actionAdvice.comment,
        fieldType: 'textarea',
        validationRegex: ".{0,1000}",
        fieldTitle: "REDUCTION_ACTION_COMMENT_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_COMMENT_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_1000_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.files = (_ref = $scope.actionAdvice.files) != null ? _ref : [];
      $scope.baseIndicatorAssociations = (_ref2 = $scope.actionAdvice.baseIndicatorAssociations) != null ? _ref2 : [];
      $scope.baseIndicatorAssociationToAdd = {
        baseIndicatorKey: "",
        percent: "0",
        percentMax: "0",
        isValid: function() {
          var percent, percentMax;
          console.log(this.percent);
          if (!!this.baseIndicatorKey && (this.percent != null) && !!this.percentMax) {
            percent = +this.percent.replace(',', '.');
            percentMax = +this.percentMax.replace(',', '.');
            return (percent >= -1) && (percent < 100) && (percentMax > 0) && (percentMax < 100) && (percentMax >= percent);
          }
          return false;
        },
        clear: function() {
          this.baseIndicatorKey = "";
          this.percent = 0;
          return this.percentMax = 0;
        }
      };
      $scope.baseIndicatorOptions = [];
      $scope.$watch('interfaceTypeKey.field', function(n, o) {
        if (n !== o) {
          $scope.baseIndicatorAssociations = [];
          $scope.baseIndicatorAssociationToAdd.clear();
          $scope.refreshBaseIndicatorOptions();
        }
        return;
      });
      $scope.$watch('typeKey.field', function(n, o) {
        if (n !== o) {
          $scope.baseIndicatorAssociations = [];
          $scope.baseIndicatorAssociationToAdd.clear();
        }
        return;
      });
      $scope.allFieldValid = function() {
        return $scope.title.isValid && $scope.webSite.isValid && $scope.responsiblePerson.isValid && (($scope.typeKey.field === "2") || ($scope.baseIndicatorAssociations.length > 0));
      };
      $scope.editorOptions = {
        language: 'fr',
        skin: 'moono',
        uiColor: '#CFCDC0',
        toolbar: 'Basic',
        height: '95px'
      };
      $scope.save = function() {
        var data;
        $scope.isLoading = true;
        data = {
          id: $scope.actionAdvice.id,
          interfaceTypeKey: $scope.interfaceTypeKey.field,
          title: $scope.title.field,
          typeKey: $scope.typeKey.field,
          physicalMeasure: $scope.physicalMeasure.field,
          financialBenefit: $scope.financialBenefit.field,
          investmentCost: $scope.investmentCost.field,
          expectedPaybackTime: $scope.expectedPaybackTime.field,
          webSite: $scope.webSite.field,
          responsiblePerson: $scope.responsiblePerson.field,
          comment: $scope.comment.field,
          files: $scope.files,
          baseIndicatorAssociations: $scope.baseIndicatorAssociations
        };
        downloadService.postJson('/awac/admin/advices/save', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
            if ($scope.editMode) {
              angular.extend($scope.getParams().actionAdvice, result.data);
            }
            $scope.close();
            if (!!$scope.getParams().cb) {
              return $scope.getParams().cb();
            }
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      $scope.close = function() {
        return modalService.close(modalService.CREATE_OR_EDIT_REDUCTION_ACTION_ADVICE);
      };
      $scope.onFileSelect = function($files) {
        var file, i;
        $scope.inDownload = true;
        i = 0;
        while (i < $files.length) {
          file = $files[i];
          $scope.upload = $upload.upload({
            url: "/awac/file/upload/",
            file: file
          }).progress(function(evt) {
            $scope.percent = parseInt(100.0 * evt.loaded / evt.total);
            return;
          }).success(function(data) {
            var fileName;
            $scope.percent = 0;
            $scope.inDownload = false;
            fileName = data.name;
            messageFlash.displaySuccess("The file " + fileName + " was successfully uploaded ");
            $scope.files.push(data);
            console.log($scope.files);
            return;
          }).error(function(data, status, headers, config) {
            $scope.percent = 0;
            $scope.inDownload = false;
            messageFlash.displayError("The upload of the file has failed");
            return;
          });
          i++;
        }
        return;
      };
      $scope.download = function(storedFileId) {
        var url;
        url = '/awac/file/download/' + storedFileId;
        return $window.open(url);
      };
      $scope.removeFile = function(storedFileId) {
        var file, index, _ref;
        _ref = $scope.files;
        for (index in _ref) {
          file = _ref[index];
          if (file.id === storedFileId) {
            $scope.files.splice(index, 1);
            break;
          }
        }
        return;
      };
      $scope.refreshBaseIndicatorOptions = function() {
        var baseIndicatorOptions;
        baseIndicatorOptions = baseIndicatorLabelsByInterfaceType[$scope.interfaceTypeKey.field];
        if ($scope.baseIndicatorAssociations.length > 0) {
          baseIndicatorOptions = codeLabelHelper.removeCodeLabelsByKeys(baseIndicatorOptions, _.pluck($scope.baseIndicatorAssociations, "baseIndicatorKey"));
        }
        baseIndicatorOptions = codeLabelHelper.sortCodeLabelsByNumericKey(baseIndicatorOptions);
        $scope.baseIndicatorOptions = baseIndicatorOptions;
        return;
      };
      $scope.sortBaseIndicatorAssociations = function() {
        $scope.baseIndicatorAssociations = _.sortBy($scope.baseIndicatorAssociations, function(baseIndicatorAssociation) {
          return parseInt(baseIndicatorAssociation.baseIndicatorKey.match(/\d+/), 10);
        });
        return;
      };
      $scope.addBaseIndicatorAssociation = function() {
        if ($scope.baseIndicatorAssociationToAdd.isValid()) {
          $scope.baseIndicatorAssociationToAdd.percent = +$scope.baseIndicatorAssociationToAdd.percent.replace(',', '.');
          $scope.baseIndicatorAssociationToAdd.percentMax = +$scope.baseIndicatorAssociationToAdd.percentMax.replace(',', '.');
          $scope.baseIndicatorAssociations.push(angular.copy($scope.baseIndicatorAssociationToAdd));
          $scope.baseIndicatorAssociationToAdd.clear();
          $scope.sortBaseIndicatorAssociations();
          $scope.refreshBaseIndicatorOptions();
        }
        return;
      };
      $scope.removeBaseIndicatorAssociation = function(baseIndicatorKey) {
        var baseIndicatorAssociation, index, _ref;
        _ref = $scope.baseIndicatorAssociations;
        for (index in _ref) {
          baseIndicatorAssociation = _ref[index];
          if (baseIndicatorAssociation.baseIndicatorKey === baseIndicatorKey) {
            $scope.baseIndicatorAssociations.splice(index, 1);
            break;
          }
        }
        $scope.refreshBaseIndicatorOptions();
        return;
      };
      $scope.getBaseIndicatorLabel = function(baseIndicatorKey) {
        return baseIndicatorKey + " - " + codeLabelHelper.getLabelByKey(baseIndicatorCodeLabels, baseIndicatorKey);
      };
      $scope.refreshBaseIndicatorOptions();
      $scope.sortBaseIndicatorAssociations();
      return {
        link: function(scope) {}
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalEditProduct", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-edit-product.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.createNewProduct = true;
      if ($scope.getParams().product != null) {
        $scope.product = angular.copy($scope.getParams().product);
        $scope.createNewProduct = false;
      } else {
        $scope.product = {};
      }
      $scope.fields = {
        name: {
          fieldTitle: "NAME",
          field: $scope.product.name,
          validationRegex: "^.{1,255}$",
          validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH",
          focus: function() {
            return true;
          }
        },
        description: {
          fieldTitle: "DESCRIPTION",
          fieldType: 'textarea',
          validationRegex: "^.{0,65000}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_TEXT",
          field: $scope.product.description,
          hideIsValidIcon: true
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          data = {};
          data.name = $scope.fields.name.field;
          data.description = $scope.fields.description.field;
          $scope.isLoading = true;
          if ($scope.getParams().product != null) {
            data.id = $scope.getParams().product.id;
            downloadService.postJson('/awac/product/edit', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
                $scope.getParams().product.name = $scope.fields.name.field;
                $scope.getParams().product.description = $scope.fields.description.field;
                $scope.getParams().refreshMyProduct();
                return $scope.close();
              } else {
                return $scope.isLoading = false;
              }
            });
          } else {
            downloadService.postJson('/awac/product/create', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
                $scope.getParams().organization.products.push(result.data);
                $scope.$root.mySites.push(result.data);
                console.log("$scope.getParams()");
                console.log($scope.getParams());
                $scope.getParams().refreshMyProduct();
                return $scope.close();
              } else {
                return $scope.isLoading = false;
              }
            });
          }
        }
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.EDIT_PRODUCT);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalConfirmClosing", function(directiveService, downloadService, translationService, messageFlash, $filter) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-confirm-closing.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.isLoading = false;
      $scope.close = function() {
        return modalService.close(modalService.CONFIRM_CLOSING);
      };
      $scope.passwordInfo = {
        fieldTitle: "USER_PASSWORD",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        field: "",
        hideIsValidIcon: true,
        focus: function() {
          return true;
        }
      };
      $scope.allFieldValid = function() {
        if ($scope.passwordInfo.isValid) {
          return true;
        }
        return false;
      };
      return $scope.valid = function() {
        var close, dto;
        if ($scope.allFieldValid()) {
          close = !$scope.$root.closedForms;
          $scope.isLoading = true;
          dto = {};
          dto.password = $scope.passwordInfo.field;
          dto.periodKey = $scope.$root.periodSelectedKey;
          dto.scopeId = $scope.$root.scopeSelectedId;
          dto.close = close;
          return downloadService.postJson("/awac/answer/closeForm/", dto, function(result) {
            if (result.success) {
              $scope.$root.closedForms = close;
              $scope.$root.verificationRequest = null;
              $scope.$root.$broadcast('CLEAN_VERIFICATION');
              if (close) {
                messageFlash.displaySuccess($filter('translate')('MODAL_CONFIRM_CLOSING_SUCCESS'));
              } else {
                messageFlash.displaySuccess($filter('translate')('MODAL_CONFIRM_UNCLOSING_SUCCESS'));
              }
              $scope.isLoading = false;
              return $scope.close();
            } else {
              return $scope.isLoading = false;
            }
          });
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalVerificationFinalization", function(directiveService, downloadService, translationService, messageFlash, $upload, $filter) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-finalization.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.comment = {
        fieldTitle: "MODAL_QUESTION_COMMENT_TITLE",
        validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH",
        fieldType: 'textarea',
        hidden: $scope.getParams().verificationSuccess === true,
        focus: function() {
          return true;
        }
      };
      $scope.save = function() {
        var dto;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          dto = {
            scopeId: $scope.getParams().request.scope.id,
            periodKey: $scope.getParams().request.period.key
          };
          if ($scope.getParams().verificationSuccess === true) {
            dto.newStatus = 'VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_SUCCESS';
            dto.verificationFinalizationFileId = $scope.file.id;
          } else {
            dto.newStatus = 'VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_REJECT';
            dto.verificationRejectedComment = $scope.comment.field;
          }
          return downloadService.postJson("/awac/verification/setStatus", dto, function(result) {
            $scope.isLoading = false;
            if (result.success) {
              $scope.getParams().removeRequest();
              return $scope.close();
            }
          });
        }
      };
      $scope.onFileSelect = function($files) {
        var file, i;
        $scope.inDownload = true;
        i = 0;
        while (i < $files.length) {
          file = $files[i];
          $scope.upload = $upload.upload({
            url: "/awac/file/upload/",
            data: {
              myObj: $scope.myModelObj
            },
            file: file
          }).progress(function(evt) {
            $scope.percent = parseInt(100.0 * evt.loaded / evt.total);
            return;
          }).success(function(data, status, headers, config) {
            var fileName;
            $scope.percent = 0;
            $scope.inDownload = false;
            fileName = data.name;
            messageFlash.displaySuccess($filter('translateText')('FILE_UPLOAD_SUCCESS'));
            $scope.file = data;
            return;
          }).error(function(data, status, headers, config) {
            $scope.percent = 0;
            $scope.inDownload = false;
            messageFlash.displayError($filter('translateText')('FILE_UPLOAD_FAIL'));
            return;
          });
          i++;
        }
        return;
      };
      $scope.allFieldValid = function() {
        return $scope.getParams().verificationSuccess !== true || ($scope.file != null);
      };
      return $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_FINALIZATION);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalVerificationFinalizationVisualization", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-finalization-visualization.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_FINALIZATION_VISUALIZATION);
      };
      return $scope.comment = $scope.ngParams.comment;
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalEditSubList", function(directiveService, downloadService, translationService, messageFlash, $upload, $window, codeLabelHelper) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-edit-sub-list.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.codeLabels = $scope.getParams().codeLabels;
      $scope.subList = angular.copy($scope.getParams().subList);
      $scope.updated = false;
      $scope.sortableOptions = {
        axis: 'y',
        stop: function(e, ui) {
          var index, _results;
          _results = [];
          for (index in $scope.subList.items) {
            _results.push($scope.subList.items[index].orderIndex = +index);
          }
          return _results;
        }
      };
      $scope.save = function() {
        $scope.isLoading = true;
        downloadService.postJson('/awac/admin/translations/sublists/save', $scope.subList, function(result) {
          $scope.isLoading = false;
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
            angular.extend($scope.getParams().subList, result.data);
            $scope.updated = false;
            $scope.close();
          }
          return;
        });
        return;
      };
      $scope.close = function() {
        modalService.close(modalService.EDIT_SUB_LIST);
        return;
      };
      $scope.onModelChange = function(newItems, oldItems) {
        var duplicatedKey;
        duplicatedKey = $scope.findDuplicatedKey(newItems);
        if (duplicatedKey !== null) {
          messageFlash.displayError("L'élément '" + duplicatedKey + "' est déjà présent dans la liste !");
          angular.extend(newItems, oldItems);
          return false;
        }
        $scope.updated = !angular.equals(newItems, $scope.getParams().subList.items);
        return;
      };
      $scope.findDuplicatedKey = function(subListItems) {
        var index, item;
        for (index in subListItems) {
          item = subListItems[index];
          if (_.where(subListItems, {
            key: item.key
          }).length > 1) {
            return item.key;
          }
        }
        return null;
      };
      $scope.$watch('subList.items', $scope.onModelChange, true);
      return {
        link: function(scope) {}
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalConfirmationExitForm", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-confirmation-exit-form.html",
    controller: function($scope, modalService) {
      directiveService.autoScope;
      $scope.close = function() {
        return modalService.close(modalService.CONFIRMATION_EXIT_FORM);
      };
      $scope["continue"] = function() {
        $scope.$root.nav($scope.ngParams.loc, true);
        return $scope.close();
      };
      return $scope.save = function() {
        var arg;
        arg = {};
        arg.loc = $scope.ngParams.loc;
        arg.confirmed = true;
        $scope.$root.$broadcast('SAVE_AND_NAV', arg);
        return $scope.close();
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalEmailChange", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-email-change.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.passwordInfo = {
        field: "",
        fieldType: "password",
        inputName: 'password',
        fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE",
        placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^\\S{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        hideIsValidIcon: true,
        focus: function() {
          return true;
        }
      };
      $scope.oldEmailInfo = {
        inputName: 'email',
        field: $scope.getParams().oldEmail,
        fieldTitle: "EMAIL_CHANGE_FORM_OLD_EMAIL_FIELD_TITLE",
        disabled: true
      };
      $scope.newEmailInfo = {
        inputName: 'email',
        field: "",
        fieldTitle: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_TITLE",
        placeholder: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_PLACEHOLDER",
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT",
        hideIsValidIcon: true
      };
      $scope.allFieldValid = function() {
        if ($scope.passwordInfo.isValid && $scope.newEmailInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          password: $scope.passwordInfo.field,
          newEmail: $scope.newEmailInfo.field
        };
        downloadService.postJson('/awac/user/email/save', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
            $scope.close();
            if ($scope.getParams().cb != null) {
              return $scope.getParams().cb($scope.newEmailInfo.field);
            }
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.EMAIL_CHANGE);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalEditEvent", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-edit-event.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.createNewEvent = true;
      if ($scope.getParams().event != null) {
        $scope.event = angular.copy($scope.getParams().event);
        $scope.createNewEvent = false;
      } else {
        $scope.event = {};
      }
      $scope.fields = {
        name: {
          fieldTitle: "NAME",
          field: $scope.event.name,
          validationRegex: "^.{1,255}$",
          validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH",
          focus: function() {
            return true;
          }
        },
        description: {
          fieldTitle: "DESCRIPTION",
          validationRegex: "^.{0,65000}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_TEXT",
          field: $scope.event.description,
          fieldType: 'textarea',
          hideIsValidIcon: true
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          data = {};
          data.id = $scope.event.id;
          data.name = $scope.fields.name.field;
          data.description = $scope.fields.description.field;
          data.period = $scope.getParams().period;
          $scope.isLoading = true;
          if ($scope.getParams().event != null) {
            data.id = $scope.getParams().event.id;
            downloadService.postJson('/awac/organization/events/save', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
                $scope.getParams().event.name = $scope.fields.name.field;
                $scope.getParams().event.description = $scope.fields.description.field;
                return $scope.close();
              } else {
                return $scope.isLoading = false;
              }
            });
          } else {
            downloadService.postJson('/awac/organization/events/save', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
                if (!$scope.getParams().events) {
                  $scope.getParams().events = [];
                }
                $scope.getParams().events.push(result.data);
                return $scope.close();
              } else {
                return $scope.isLoading = false;
              }
            });
          }
        }
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.EDIT_EVENT);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalCreateReductionAction", function(directiveService, downloadService, translationService, messageFlash, $upload, $window) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-create-reduction-action.html",
    controller: function($scope, modalService) {
      var action, _ref, _ref2, _ref3, _ref4;
      directiveService.autoScopeImpl($scope);
      $scope.typeOptions = $scope.getParams().typeOptions;
      $scope.statusOptions = $scope.getParams().statusOptions;
      $scope.gwpUnits = $scope.getParams().gwpUnits;
      action = $scope.getParams().action;
      $scope.editMode = !!action;
      if ($scope.editMode) {
        $scope.action = angular.copy(action);
      } else {
        $scope.action = {
          typeKey: '1',
          statusKey: '1',
          scopeTypeKey: '1'
        };
      }
      $scope.getDefaultDueDate = function() {
        var defaultDueDate;
        defaultDueDate = new Date();
        defaultDueDate.setFullYear(defaultDueDate.getFullYear() + 1);
        return defaultDueDate;
      };
      $scope.title = {
        inputName: 'title',
        field: $scope.action.title,
        fieldTitle: "REDUCTION_ACTION_TITLE_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_TITLE_FIELD_PLACEHOLDER",
        validationRegex: "^.{1,255}$",
        validationMessage: "REDUCTION_ACTION_TITLE_WRONG_LENGTH",
        hideIsValidIcon: true,
        focus: function() {
          return true;
        }
      };
      $scope.typeKey = {
        inputName: 'typeKey',
        field: $scope.action.typeKey,
        fieldTitle: "REDUCTION_ACTION_TYPE_FIELD_TITLE"
      };
      $scope.statusKey = {
        inputName: 'statusKey',
        field: $scope.action.statusKey,
        fieldTitle: "REDUCTION_ACTION_STATUS_FIELD_TITLE"
      };
      $scope.scopeTypeKey = {
        inputName: 'scopeTypeKey',
        field: $scope.action.scopeTypeKey,
        fieldTitle: "REDUCTION_ACTION_SCOPE_TYPE_FIELD_TITLE"
      };
      $scope.scopeId = {
        inputName: 'scopeId',
        field: !!$scope.action.scopeId && ($scope.action.scopeTypeKey === "2") ? $scope.action.scopeId : $scope.$root.mySites[0].id
      };
      $scope.physicalMeasure = {
        inputName: 'physicalMeasure',
        field: $scope.action.physicalMeasure,
        fieldTitle: "REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_PLACEHOLDER",
        fieldType: 'textarea',
        validationRegex: ".{0,1000}",
        validationMessage: "TEXT_FIELD_MAX_1000_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.ghgBenefit = {
        inputName: 'ghgBenefit',
        field: $scope.action.ghgBenefit,
        numbersOnly: 'double',
        fieldTitle: "REDUCTION_ACTION_GHG_BENEFIT_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_GHG_BENEFIT_FIELD_PLACEHOLDER"
      };
      $scope.ghgBenefitUnitKey = (_ref = $scope.action.ghgBenefitUnitKey) != null ? _ref : "U5335";
      $scope.ghgBenefitMax = {
        inputName: 'ghgBenefitMax',
        field: $scope.action.ghgBenefitMax,
        numbersOnly: 'double',
        fieldTitle: "REDUCTION_ACTION_GHG_BENEFIT_MAX_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_GHG_BENEFIT_MAX_FIELD_PLACEHOLDER"
      };
      $scope.ghgBenefitMaxUnitKey = (_ref2 = $scope.action.ghgBenefitMaxUnitKey) != null ? _ref2 : "U5335";
      $scope.financialBenefit = {
        inputName: 'financialBenefit',
        field: $scope.action.financialBenefit,
        numbersOnly: 'double',
        fieldTitle: "REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_PLACEHOLDER"
      };
      $scope.investmentCost = {
        inputName: 'investment',
        field: $scope.action.investmentCost,
        numbersOnly: 'double',
        fieldTitle: "REDUCTION_ACTION_INVESTMENT_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_INVESTMENT_FIELD_PLACEHOLDER"
      };
      $scope.expectedPaybackTime = {
        inputName: 'expectedPaybackTime',
        field: $scope.action.expectedPaybackTime,
        validationRegex: "^.{0,255}$",
        fieldTitle: "REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.dueDate = {
        inputName: 'dueDate',
        field: (_ref3 = $scope.action.dueDate) != null ? _ref3 : $scope.getDefaultDueDate(),
        fieldTitle: "REDUCTION_ACTION_DUE_DATE_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_DUE_DATE_FIELD_PLACEHOLDER",
        minValue: new Date(),
        validationMessage: "REDUCTION_ACTION_INVALID_DUE_DATE",
        hideIsValidIcon: true
      };
      $scope.webSite = {
        inputName: 'webSite',
        field: $scope.action.webSite,
        validationRegex: "^.{0,255}$",
        fieldTitle: "REDUCTION_ACTION_WEBSITE_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_WEBSITE_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.responsiblePerson = {
        inputName: 'responsiblePerson',
        field: $scope.action.responsiblePerson,
        validationRegex: "^.{0,255}$",
        fieldTitle: "REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_255_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.comment = {
        inputName: 'comment',
        field: $scope.action.comment,
        fieldType: 'textarea',
        validationRegex: ".{0,1000}",
        fieldTitle: "REDUCTION_ACTION_COMMENT_FIELD_TITLE",
        placeholder: "REDUCTION_ACTION_COMMENT_FIELD_PLACEHOLDER",
        validationMessage: "TEXT_FIELD_MAX_1000_CHARACTERS",
        hideIsValidIcon: true
      };
      $scope.files = (_ref4 = $scope.action.files) != null ? _ref4 : [];
      $scope.allFieldValid = function() {
        return $scope.title.isValid && $scope.expectedPaybackTime.isValid && $scope.dueDate.isValid && $scope.webSite.isValid && $scope.responsiblePerson.isValid;
      };
      $scope.save = function() {
        var data;
        $scope.isLoading = true;
        data = {
          id: $scope.action.id,
          title: $scope.title.field,
          typeKey: $scope.typeKey.field,
          statusKey: $scope.statusKey.field,
          completionDate: $scope.action.completionDate,
          scopeTypeKey: $scope.scopeTypeKey.field,
          scopeId: $scope.scopeTypeKey.field === "1" ? null : $scope.scopeId.field,
          physicalMeasure: $scope.physicalMeasure.field,
          ghgBenefit: $scope.ghgBenefit.field,
          ghgBenefitUnitKey: $scope.ghgBenefitUnitKey,
          ghgBenefitMax: $scope.ghgBenefitMax.field,
          ghgBenefitMaxUnitKey: $scope.ghgBenefitMaxUnitKey,
          financialBenefit: $scope.financialBenefit.field,
          investmentCost: $scope.investmentCost.field,
          expectedPaybackTime: $scope.expectedPaybackTime.field,
          dueDate: $scope.dueDate.field,
          webSite: $scope.webSite.field,
          responsiblePerson: $scope.responsiblePerson.field,
          comment: $scope.comment.field,
          files: $scope.files
        };
        downloadService.postJson('/awac/actions/save', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
            if ($scope.editMode) {
              angular.extend($scope.getParams().action, result.data);
            }
            $scope.close();
            if (!!$scope.getParams().cb) {
              return $scope.getParams().cb();
            }
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      $scope.toggleGhgBenefitField = function(typeKey) {
        if (typeKey === '2') {
          $scope.ghgBenefit.field = "";
          $scope.ghgBenefit.disabled = true;
          $scope.ghgBenefitUnitKey.disabled = true;
          $scope.ghgBenefitMax.field = "";
          $scope.ghgBenefitMax.disabled = true;
          return $scope.ghgBenefitMaxUnitKey.disabled = true;
        } else {
          $scope.ghgBenefit.disabled = false;
          $scope.ghgBenefitUnitKey.disabled = false;
          $scope.ghgBenefitMax.disabled = false;
          return $scope.ghgBenefitMaxUnitKey.disabled = false;
        }
      };
      $scope.$watch('typeKey.field', function(n, o) {
        if (n !== o) {
          return $scope.toggleGhgBenefitField(n);
        }
      });
      $scope.close = function() {
        return modalService.close(modalService.CREATE_REDUCTION_ACTION);
      };
      $scope.toggleGhgBenefitField($scope.typeKey.field);
      $scope.onFileSelect = function($files) {
        var file, i;
        $scope.inDownload = true;
        i = 0;
        while (i < $files.length) {
          file = $files[i];
          $scope.upload = $upload.upload({
            url: "/awac/file/upload/",
            file: file
          }).progress(function(evt) {
            $scope.percent = parseInt(100.0 * evt.loaded / evt.total);
            return;
          }).success(function(data) {
            var fileName;
            $scope.percent = 0;
            $scope.inDownload = false;
            fileName = data.name;
            messageFlash.displaySuccess("The file " + fileName + " was upload successfully");
            $scope.files.push(data);
            console.log($scope.files);
            return;
          }).error(function(data, status, headers, config) {
            $scope.percent = 0;
            $scope.inDownload = false;
            messageFlash.displayError("The upload of the file was failed");
            return;
          });
          i++;
        }
        return;
      };
      $scope.download = function(storedFileId) {
        var url;
        url = '/awac/file/download/' + storedFileId;
        return $window.open(url);
      };
      $scope.remove = function(storedFileId) {
        var file, index, _ref;
        _ref = $scope.files;
        for (index in _ref) {
          file = _ref[index];
          if (file.id === storedFileId) {
            $scope.files.splice(index, 1);
            break;
          }
        }
        return;
      };
      $scope.editorOptions = {
        language: 'fr',
        skin: 'moono',
        uiColor: '#CFCDC0',
        toolbar: 'Basic',
        height: '95px'
      };
      return {
        link: function(scope) {}
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalAddUserSite", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-add-user-site.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      if ($scope.getParams().site != null) {
        $scope.site = angular.copy($scope.getParams().site);
        $scope.createNewSite = false;
      } else {
        $scope.site = {};
        $scope.site.percentOwned = 100;
      }
      $scope.allFieldValid = function() {
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          data = {
            organization: $scope.getParams().organization,
            site: $scope.getParams().site,
            selectedAccounts: $scope.selection
          };
          downloadService.postJson('/awac/organization/site/associatedaccounts/save', data, function(result) {
            var account, founded, i, ownSite, site, _i, _j, _len, _len2, _ref, _ref2;
            if (result.success) {
              ownSite = false;
              _ref = $scope.selection;
              for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                account = _ref[_i];
                if (account.identifier === $scope.$root.currentPerson.identifier) {
                  ownSite = true;
                  break;
                }
              }
              i = 0;
              founded = false;
              _ref2 = $scope.$root.mySites;
              for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                site = _ref2[_j];
                if (parseFloat(site.id) === parseFloat($scope.getParams().site.id)) {
                  founded = true;
                  if (ownSite === false) {
                    $scope.$root.mySites.splice(i, 1);
                  }
                  break;
                }
                i++;
              }
              if (ownSite === true && founded === false) {
                $scope.$root.mySites.push($scope.getParams().site);
              }
              return $scope.close();
            } else {
              messageFlash.displayError(result.data.message);
              return $scope.isLoading = false;
            }
          });
        }
        return false;
      };
      $scope.accounts = [];
      $scope.selection = [];
      $scope.toggleSelection = function(account) {
        var idx;
        console.log("entering toggleSelection");
        console.log(account.identifier);
        idx = $scope.selection.indexOf(account);
        console.log(idx);
        if (idx > -1) {
          return $scope.selection.splice(idx, 1);
        } else {
          return $scope.selection.push(account);
        }
      };
      $scope.close = function() {
        return modalService.close(modalService.ADD_USER_SITE);
      };
      return $scope.getAssociatedUsers = function() {
        var data;
        console.log("entering getAssociatedUsers");
        $scope.selection = [];
        $scope.accounts = [];
        $scope.prepare = [];
        data = {
          organization: $scope.getParams().organization,
          site: $scope.getParams().site
        };
        downloadService.postJson('/awac/organization/site/associatedaccounts/load', data, function(result) {
          var selected, user, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3, _results;
          if (result.success) {
            _ref = result.data.organizationUserList;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              user = _ref[_i];
              $scope["in"] = false;
              if (result.data.siteSelectedUserList.length) {
                _ref2 = result.data.siteSelectedUserList;
                for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                  selected = _ref2[_j];
                  if (user.identifier === selected.identifier) {
                    $scope["in"] = true;
                  }
                }
              }
              if ($scope["in"] === false) {
                $scope.accounts.push(user);
              }
            }
            _ref3 = result.data.siteSelectedUserList;
            _results = [];
            for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
              selected = _ref3[_k];
              $scope.selection.push(selected);
              _results.push($scope.accounts.push(selected));
            }
            return _results;
          }
        });
        return $scope.isLoading = false;
      };
    },
    link: function(scope) {
      console.log("entering mmAwacModalAddUserSite");
      return scope.getAssociatedUsers();
    }
  };
});angular.module('app.directives').directive("mmAwacModalAgreement", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-agreement.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.url = "http://www.w3schools.com";
      return $scope.close = function() {
        return modalService.close(modalService.HELP);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalConfirmDialog", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-confirm-dialog.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.titleKey = $scope.getParams().titleKey;
      $scope.messageKey = $scope.getParams().messageKey;
      $scope.close = function() {
        return modalService.close(modalService.CONFIRM_DIALOG);
      };
      return $scope.confirm = function() {
        if (!!$scope.getParams().onConfirm) {
          if (!!$scope.getParams().confirmParams && $scope.getParams().confirmParams.length > 0) {
            if ($scope.getParams().confirmParams.length === 1) {
              $scope.getParams().onConfirm($scope.getParams().confirmParams[0]);
            }
            if ($scope.getParams().confirmParams.length === 2) {
              $scope.getParams().onConfirm($scope.getParams().confirmParams[0], $scope.getParams().confirmParams[1]);
            }
          } else {
            $scope.getParams().onConfirm();
          }
        }
        return $scope.close();
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalEditSite", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-edit-site.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.organizationStructureList = ['ORGANIZATION_STRUCTURE_1', 'ORGANIZATION_STRUCTURE_2', 'ORGANIZATION_STRUCTURE_3', 'ORGANIZATION_STRUCTURE_4', 'ORGANIZATION_STRUCTURE_5'];
      $scope.organizationStructure = $scope.organizationStructureList[0];
      $scope.createNewSite = true;
      if ($scope.getParams().site != null) {
        $scope.site = angular.copy($scope.getParams().site);
        $scope.createNewSite = false;
        $scope.organizationStructure = $scope.site.organizationalStructure;
      } else {
        $scope.site = {};
        $scope.site.percentOwned = 100;
      }
      $scope.fields = {
        name: {
          fieldTitle: "NAME",
          field: $scope.site.name,
          validationRegex: "^.{1,255}$",
          validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH",
          focus: function() {
            return true;
          }
        },
        description: {
          fieldTitle: "DESCRIPTION",
          fieldType: 'textarea',
          validationRegex: "^.{0,65000}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_TEXT",
          field: $scope.site.description,
          hideIsValidIcon: true
        },
        nace: {
          fieldTitle: "SITE_MANAGER_NACE_CODE",
          validationRegex: "^.{0,255}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_STRING",
          field: $scope.site.naceCode,
          hideIsValidIcon: true
        },
        percentOwned: {
          fieldTitle: "SITE_MANAGER_PERCENT_OWNED",
          validationFct: function() {
            if ($scope.fields.percentOwned.field > 0 && $scope.fields.percentOwned.field <= 100) {
              return true;
            }
            return false;
          },
          validationMessage: "CONTROL_FIELD_DEFAULT_PERCENT_MAX_100",
          field: $scope.site.percentOwned,
          numbersOnly: 'double',
          description: 'SITE_MANAGER_PERCENT_OWNED_DESC'
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          data = {};
          data.name = $scope.fields.name.field;
          data.description = $scope.fields.description.field;
          data.naceCode = $scope.fields.nace.field;
          data.organizationalStructure = $scope.organizationStructure;
          data.percentOwned = $scope.fields.percentOwned.field;
          $scope.isLoading = true;
          if ($scope.getParams().site != null) {
            data.id = $scope.getParams().site.id;
            downloadService.postJson('/awac/site/edit', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
                $scope.getParams().site.name = $scope.fields.name.field;
                $scope.getParams().site.description = $scope.fields.description.field;
                $scope.getParams().site.naceCode = $scope.fields.nace.field;
                $scope.getParams().site.organizationalStructure = $scope.organizationStructure;
                $scope.getParams().site.percentOwned = $scope.fields.percentOwned.field;
                $scope.getParams().refreshMySites();
                return $scope.close();
              } else {
                return $scope.isLoading = false;
              }
            });
          } else {
            downloadService.postJson('/awac/site/create', data, function(result) {
              if (result.success) {
                messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
                $scope.getParams().organization.sites.push(result.data);
                $scope.$root.mySites.push(result.data);
                $scope.getParams().refreshMySites();
                return $scope.close();
              } else {
                return $scope.isLoading = false;
              }
            });
          }
        }
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.EDIT_SITE);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalDocumentManager", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-document-manager.html",
    controller: function($scope, modalService, $location, $window) {
      directiveService.autoScopeImpl($scope);
      $scope.listDocuments = $scope.getParams().listDocuments;
      console.log($scope.listDocuments);
      $scope.show = false;
      $scope.loc = null;
      $scope.download = function(storedFileId) {
        var url;
        url = '/awac/file/download/' + storedFileId;
        return $window.open(url);
      };
      $scope.removeDoc = function(storedFileId) {
        if ($scope.getParams().readyOnly !== true) {
          delete $scope.listDocuments[storedFileId];
          return $scope.getParams().wasEdited();
        }
      };
      return $scope.close = function() {
        return modalService.close(modalService.DOCUMENT_MANAGER);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalQuestionComment", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-question-comment.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.close = function() {
        return modalService.close(modalService.QUESTION_COMMENT);
      };
      $scope.comment = $scope.ngParams.comment;
      return $scope.save = function() {
        $scope.ngParams.save($scope.comment);
        return $scope.close();
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalHelp", function(directiveService, downloadService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-help.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      if ($scope.getParams().template != null) {
        downloadService.getJson("/awac/admin/translations/wysiwyg/get/" + $scope.$root.instanceName + "/" + $scope.getParams().template + ":" + $scope.$root.language, function(result) {
          if (result.success) {
            return $('.modal-help-content').html(result.data.content);
          }
        });
      }
      return $scope.close = function() {
        return modalService.close(modalService.HELP);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalVerificationConfirmation", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-confirmation.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.fields = {
        myPassword: {
          fieldType: "password",
          inputName: 'password',
          fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE",
          placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER",
          validationRegex: "^\\S{5,20}$",
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
          hideIsValidIcon: true,
          focus: function() {
            return true;
          }
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function(valid) {
        var dto;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          dto = {
            scopeId: $scope.$root.scopeSelectedId,
            periodKey: $scope.$root.periodSelectedKey,
            password: $scope.fields.myPassword.field
          };
          if (valid === true) {
            dto.newStatus = 'VERIFICATION_STATUS_WAIT_ASSIGNATION';
          } else {
            dto.newStatus = 'VERIFICATION_STATUS_REJECTED';
          }
          return downloadService.postJson("/awac/verification/setStatus", dto, function(result) {
            $scope.isLoading = false;
            if (result.success) {
              if (valid === true) {
                $scope.$root.verificationRequest.status = 'VERIFICATION_STATUS_WAIT_ASSIGNATION';
              } else {
                $scope.$root.verificationRequest = null;
              }
              return $scope.close();
            }
          });
        }
      };
      return $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_CONFIRMATION);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalInviteUser", function(directiveService, downloadService, translationService, messageFlash, $rootScope) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-invite-user.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.inviteEmailInfo = {
        field: "",
        inputName: 'email',
        fieldTitle: "USER_INVITATION_EMAIL_FIELD_TITLE",
        placeholder: "USER_INVITATION_EMAIL_FIELD_PLACEHOLDER",
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT",
        focus: function() {
          return true;
        }
      };
      $scope.allFieldValid = function() {
        if ($scope.inviteEmailInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          invitationEmail: $scope.inviteEmailInfo.field,
          organizationName: $rootScope.organizationName
        };
        downloadService.postJson('/awac/invitation', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("INVITATION_EMAIL_SENT"));
            return $scope.close();
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.INVITE_USER);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalVerificationReject", function(directiveService, downloadService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-reject.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_REJECT);
      };
      $scope.comment = $scope.ngParams.comment;
      return $scope.save = function() {
        var data;
        if (!$scope.getParams().readOnly || $scope.getParams().readOnly === false) {
          data = {
            questionSetKey: $scope.getParams().questionSetCode,
            periodKey: $scope.$root.periodSelectedKey,
            scopeId: $scope.$root.scopeSelectedId,
            verification: {
              status: 'rejected',
              comment: $scope.comment
            }
          };
          return downloadService.postJson('/awac/verification/verify', data, function(result) {
            if (result.success) {
              $scope.getParams().refreshVerificationStatus(result.data);
              $scope.$root.$broadcast('TEST_CLOSING_VALIDATION');
              return $scope.close();
            }
          });
        }
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalRequestVerification", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-request-verification.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.fields = {
        email: {
          inputName: 'email',
          fieldTitle: "REQUEST_VERIFICATION_EMAIL_VERIFIER",
          placeholder: "EMAIL_CHANGE_FORM_NEW_EMAIL_FIELD_PLACEHOLDER",
          validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
          validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT",
          focus: function() {
            return true;
          }
        },
        comment: {
          fieldTitle: "MODAL_QUESTION_COMMENT_TITLE",
          fieldType: 'textarea',
          validationRegex: "^.{0,65000}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_TEXT",
          hideIsValidIcon: true
        },
        myPhoneNumber: {
          fieldTitle: "MY_PHONE_NUMBER",
          validationRegex: "^.{0,20}$",
          validationMessage: "CONTROL_FIELD_DEFAULT_STRING",
          hideIsValidIcon: true
        },
        myPassword: {
          fieldType: "password",
          inputName: 'password',
          fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE",
          placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER",
          validationRegex: "^\\S{5,20}$",
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function() {
        var data;
        data = {};
        data.password = $scope.fields.myPassword.field;
        data.periodKey = $scope.$root.periodSelectedKey;
        data.scopeId = $scope.$root.scopeSelectedId;
        data.email = $scope.fields.email.field;
        data.comment = $scope.fields.comment.field;
        data.phoneNumber = $scope.fields.myPhoneNumber.field;
        console.log(data);
        $scope.isLoading = true;
        downloadService.postJson('/awac/verification/createRequest', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("REQUEST_SEND"));
            if (!$scope.$root.verificationRequest) {
              $scope.$root.verificationRequest = {};
            }
            $scope.$root.verificationRequest.status = 'VERIFICATION_STATUS_WAIT_VERIFIER_REGISTRATION';
            return $scope.close();
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.REQUEST_VERIFICATION);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalVerificationDocument", function(directiveService, downloadService, translationService, messageFlash, $window) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-document.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.fields = {
        myPassword: {
          fieldType: "password",
          inputName: 'password',
          fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE",
          placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER",
          validationRegex: "^\\S{5,20}$",
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
          hideIsValidIcon: true,
          focus: function() {
            return true;
          }
        }
      };
      $scope.download = function() {
        var url;
        url = '/awac/file/download/' + $scope.$root.verificationRequest.verificationSuccessFileId;
        return $window.open(url);
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function(valid) {
        var data, newStatus;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          if (valid === true) {
            newStatus = 'VERIFICATION_STATUS_VERIFIED';
          } else {
            newStatus = 'VERIFICATION_STATUS_VERIFICATION';
          }
          data = {
            scopeId: $scope.$root.scopeSelectedId,
            periodKey: $scope.$root.periodSelectedKey,
            password: $scope.fields.myPassword.field,
            newStatus: newStatus
          };
          downloadService.postJson("/awac/verification/setStatus", data, function(result) {
            var _ref;
            $scope.isLoading = false;
            if (result.success) {
              $scope.$root.testCloseable();
              if ((_ref = $scope.$root.verificationRequest) != null) {
                _ref.status = newStatus;
              }
              return $scope.close();
            }
          });
        }
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_DOCUMENT);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalConsultEvent", function(directiveService, downloadService, translationService, messageFlash, ngTableParams, $filter) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-consult-event.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      downloadService.getJson('/awac/organization/events/byOrganization/' + $scope.getParams().organizationCustomer.id, function(result) {
        if (!result.success) {
          return $scope.isLoading = false;
        } else {
          $scope.events = result.data.organizationEventList;
          if (($scope.events != null) && $scope.events.length > 0) {
            if ($scope.tableParams != null) {
              $scope.tableParams.reload();
            } else {

            }
            $scope.tableParams = new ngTableParams({
              page: 1,
              count: 100
            }, {
              counts: [],
              total: 1,
              getData: function($defer, params) {
                var orderedData;
                orderedData = $filter("orderBy")($scope.events, params.orderBy());
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                return params.total(orderedData.length);
              }
            });
          }
          return $scope.isLoading = false;
        }
      });
      return $scope.close = function() {
        return modalService.close(modalService.CONSULT_EVENT);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalPasswordChange", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-password-change.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.validatePasswordConfirmField = function() {
        if ((($scope.newPasswordInfo.isValid != null) === true && true > 0) && $scope.newPasswordInfo.field === $scope.newPasswordConfirmInfo.field) {
          return true;
        }
        return false;
      };
      $scope.oldPasswordInfo = {
        inputName: 'password',
        fieldTitle: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_OLD_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        field: "",
        focus: function() {
          return true;
        }
      };
      $scope.newPasswordInfo = {
        inputName: 'password',
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        field: ""
      };
      $scope.newPasswordConfirmInfo = {
        inputName: 'password',
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_PLACEHOLDER",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        field: ""
      };
      $scope.allFieldValid = function() {
        if ($scope.oldPasswordInfo.isValid && $scope.newPasswordInfo.isValid && $scope.newPasswordConfirmInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          oldPassword: $scope.oldPasswordInfo.field,
          newPassword: $scope.newPasswordInfo.field
        };
        downloadService.postJson('/awac/user/password/save', data, function(result) {
          if (result.success) {
            messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
            return $scope.close();
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.PASSWORD_CHANGE);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalSendEmail", function(directiveService, downloadService, translationService, messageFlash, $filter) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-send-email.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.fields = {
        title: {
          fieldTitle: "TITLE",
          validationRegex: "^.{1,255}$",
          validationMessage: "EMAIL_TITLE_WRONG_LENGTH",
          focus: function() {
            return true;
          }
        },
        content: {
          fieldTitle: "CONTENT",
          validationRegex: /^.{1,65550}$/m,
          validationMessage: "EMAIL_CONTENT_WRONG_LENGTH",
          fieldType: 'textarea'
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid) {
          data = {
            calculatorKey: $scope.getParams().calculatorTarget,
            onlyOrganizationSharedData: $scope.getParams().onlyOrganizationSharedData,
            emailTitle: $scope.fields.title.field,
            emailContent: $scope.fields.content.field
          };
          console.log(data);
          return downloadService.postJson("/awac/admin/organizationData/sendEmail", data, function(result) {
            if (result.success) {
              $scope.close();
              return messageFlash.displaySuccess(translationService.get('EMAIL_SEND'));
            }
          });
        }
      };
      return $scope.close = function() {
        return modalService.close(modalService.SEND_EMAIL);
      };
    },
    link: function(scope) {
      return scope.getAssociatedUsers();
    }
  };
});angular.module('app.directives').directive("mmAwacModalConnectionPasswordChange", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-connection-password-change.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.validatePasswordConfirmField = function() {
        if ($scope.newPasswordConfirmInfo.field.match("^\\S{5,20}$")) {
          if ($scope.newPasswordInfo.field === $scope.newPasswordConfirmInfo.field) {
            return true;
          }
          $scope.newPasswordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_CONFIRMATION";
        } else {
          $scope.newPasswordConfirmInfo.validationMessage = "PASSWORD_VALIDATION_WRONG_LENGTH";
        }
        return false;
      };
      $scope.newPasswordInfo = {
        inputName: 'password',
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_FIELD_PLACEHOLDER",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        hideIsValidIcon: true,
        field: "",
        focus: function() {
          return true;
        }
      };
      $scope.newPasswordConfirmInfo = {
        inputName: 'password',
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        fieldType: "password",
        placeholder: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_PLACEHOLDER",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
        hideIsValidIcon: true,
        field: ""
      };
      $scope.allFieldValid = function() {
        if ($scope.newPasswordInfo.isValid && $scope.newPasswordConfirmInfo.isValid) {
          return true;
        }
        return false;
      };
      $scope.save = function() {
        var data;
        if (!$scope.allFieldValid()) {
          return false;
        }
        $scope.isLoading = true;
        data = {
          login: $scope.getParams().login,
          password: $scope.getParams().password,
          interfaceName: $scope.$root.instanceName,
          newPassword: $scope.newPasswordInfo.field
        };
        downloadService.postJson('/awac/login', data, function(result) {
          if (result.success) {
            $scope.$root.loginSuccess(result.data);
            messageFlash.displaySuccess(translationService.get('CONNECTION_MESSAGE_SUCCESS'));
            $scope.isLoading = false;
            return $scope.close();
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.CONNECTION_PASSWORD_CHANGE);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalManager", function(directiveService, $compile) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngCondition: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-manager.html",
    replace: true,
    link: function(scope, element) {
      scope.$on('SHOW_MODAL', function(event, args) {
        if (args.show === true) {
          scope.displayModal(args.target, args.params);
        } else {
          scope.removeModal(args.target);
        }
        return;
      });
      scope.displayModal = function(target, params) {
        var directive, paramName;
        paramName = 'params_' + target.replace(/-/g, "_");
        scope[paramName] = params;
        directive = $compile("<mm-awac-modal-" + target + " ng-params=\"" + paramName + "\" ></mm-awac-modal-" + target + ">")(scope);
        element.append(directive);
        return;
      };
      return scope.removeModal = function(target) {
        var child, _i, _len, _ref;
        _ref = element.children();
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          child = _ref[_i];
          if (child.tagName.toLowerCase() === 'mm-awac-modal-' + target.toLowerCase()) {
            angular.element(child).remove();
          }
        }
        return;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalFieldText", function(directiveService, $timeout) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngInfo: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-field-text.html",
    replace: true,
    transclude: true,
    compile: function() {
      return {
        pre: function(scope) {
          return directiveService.autoScopeImpl(scope);
        },
        post: function(scope) {
          directiveService.autoScopeImpl(scope);
          scope.isValidationDefined = !!scope.getInfo().validationRegex || !!scope.getInfo().validationFct;
          scope.hideIsValidIcon = !!scope.getInfo().hideIsValidIcon;
          scope.fieldType = !!scope.getInfo().fieldType ? scope.getInfo().fieldType : "text";
          if (!scope.getInfo().field) {
            scope.getInfo().field = "";
          }
          if (scope.getInfo().isValid === void 0) {
            scope.getInfo().isValid = !scope.isValidationDefined;
          }
          if (scope.isValidationDefined) {
            scope.$watch('getInfo().field', function(n, o) {
              if (n !== o) {
                return scope.isValid();
              }
            });
          }
          scope.isValid = function() {
            var isValid;
            if (scope.getInfo().disabled === true || scope.getInfo().hidden === true) {
              scope.getInfo().isValid = true;
              return;
            }
            if (!scope.getInfo().field) {
              scope.getInfo().field = "";
            }
            isValid = true;
            if (typeof (scope.getInfo().field) !== 'string') {
              scope.getInfo().field += "";
            }
            if (scope.getInfo().validationRegex != null) {
              isValid = !!scope.getInfo().field.match(scope.getInfo().validationRegex);
            }
            if (scope.getInfo().validationFct != null) {
              isValid = isValid && scope.getInfo().validationFct();
            }
            scope.getInfo().isValid = isValid;
            return;
          };
          scope.isValid();
          scope.logField = function() {
            return console.log(scope.getInfo());
          };
          scope.errorMessage = "";
          return scope.setErrorMessage = function(errorMessage) {
            scope.errorMessage = errorMessage;
            if (scope.lastTimeOut != null) {
              $timeout.cancel(scope.lastTimeOut);
            }
            return scope.lastTimeOut = $timeout(function() {
              scope.errorMessage = "";
              return scope.lastTimeOut = null;
            }, 2000);
          };
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacModalAddUserProduct", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-add-user-product.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      if ($scope.getParams().product != null) {
        $scope.product = angular.copy($scope.getParams().product);
        $scope.createNewProduct = false;
      } else {
        $scope.product = {};
        $scope.product.percentOwned = 100;
      }
      $scope.allFieldValid = function() {
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          data = {
            organization: $scope.getParams().organization,
            product: $scope.getParams().product,
            selectedAccounts: $scope.selection
          };
          downloadService.postJson('/awac/organization/product/associatedaccounts/save', data, function(result) {
            var account, founded, i, ownProduct, product, _i, _j, _len, _len2, _ref, _ref2;
            if (result.success) {
              ownProduct = false;
              _ref = $scope.selection;
              for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                account = _ref[_i];
                if (account.identifier === $scope.$root.currentPerson.identifier) {
                  ownProduct = true;
                  break;
                }
              }
              i = 0;
              founded = false;
              _ref2 = $scope.$root.mySites;
              for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                product = _ref2[_j];
                if (parseFloat(product.id) === parseFloat($scope.getParams().product.id)) {
                  founded = true;
                  if (ownProduct === false) {
                    $scope.$root.mySites.splice(i, 1);
                  }
                  break;
                }
                i++;
              }
              if (ownProduct === true && founded === false) {
                $scope.$root.mySites.push($scope.getParams().product);
              }
              return $scope.close();
            } else {
              messageFlash.displayError(result.data.message);
              return $scope.isLoading = false;
            }
          });
        }
        return false;
      };
      $scope.accounts = [];
      $scope.selection = [];
      $scope.toggleSelection = function(account) {
        var idx;
        console.log("entering toggleSelection");
        console.log(account.identifier);
        idx = $scope.selection.indexOf(account);
        console.log(idx);
        if (idx > -1) {
          return $scope.selection.splice(idx, 1);
        } else {
          return $scope.selection.push(account);
        }
      };
      $scope.close = function() {
        return modalService.close(modalService.ADD_USER_PRODUCT);
      };
      return $scope.getAssociatedUsers = function() {
        var data;
        console.log("entering getAssociatedUsers");
        $scope.selection = [];
        $scope.accounts = [];
        $scope.prepare = [];
        data = {
          organization: $scope.getParams().organization,
          product: $scope.getParams().product
        };
        downloadService.postJson('/awac/organization/product/associatedaccounts/load', data, function(result) {
          var selected, user, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3, _results;
          if (result.success) {
            _ref = result.data.organizationUserList;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              user = _ref[_i];
              $scope["in"] = false;
              if (result.data.productSelectedUserList.length) {
                _ref2 = result.data.productSelectedUserList;
                for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                  selected = _ref2[_j];
                  if (user.identifier === selected.identifier) {
                    $scope["in"] = true;
                  }
                }
              }
              if ($scope["in"] === false) {
                $scope.accounts.push(user);
              }
            }
            _ref3 = result.data.productSelectedUserList;
            _results = [];
            for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
              selected = _ref3[_k];
              $scope.selection.push(selected);
              _results.push($scope.accounts.push(selected));
            }
            return _results;
          }
        });
        return $scope.isLoading = false;
      };
    },
    link: function(scope) {
      console.log("entering mmAwacModalAddUserProduct");
      return scope.getAssociatedUsers();
    }
  };
});angular.module('app.directives').directive("mmAwacModalLoading", function(directiveService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-modal-loading.html",
    controller: function($scope, modalService) {
      return $scope.close = function() {
        return modalService.close(modalService.LOADING);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalPasswordConfirmation", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-password-confirmation.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.fields = {
        myPassword: {
          fieldType: "password",
          inputName: 'password',
          fieldTitle: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_TITLE",
          placeholder: "EMAIL_CHANGE_FORM_PASSWORD_FIELD_PLACEHOLDER",
          validationRegex: "^\\S{5,20}$",
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
          hideIsValidIcon: true,
          focus: function() {
            return true;
          }
        }
      };
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        return true;
      };
      $scope.save = function() {
        var data;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          data = $scope.getParams().data;
          data.password = $scope.fields.myPassword.field;
          downloadService.postJson($scope.getParams().url, data, function(result) {
            $scope.isLoading = false;
            if (result.success) {
              if ($scope.getParams().successMessage != null) {
                messageFlash.displaySuccess(translationService.get($scope.getParams().successMessage));
              }
              if ($scope.getParams().afterSave != null) {
                $scope.getParams().afterSave();
              }
              return $scope.close();
            }
          });
        }
        return false;
      };
      return $scope.close = function() {
        return modalService.close(modalService.PASSWORD_CONFIRMATION);
      };
    },
    link: function(scope) {}
  };
});angular.module('app.directives').directive("mmAwacModalVerificationAssign", function(directiveService, downloadService, translationService, messageFlash) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngParams: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-modal-verification-assign.html",
    controller: function($scope, modalService) {
      directiveService.autoScopeImpl($scope);
      $scope.isLoading = true;
      $scope.usersSelected = [];
      downloadService.getJson('awac/organization/getMyOrganization', function(result) {
        var user, userToTest, _i, _j, _len, _len2, _ref, _ref2;
        if (!result.success) {
          messageFlash.displayError(result.data.message);
          return $scope.isLoading = false;
        } else {
          $scope.users = result.data.users;
          if ($scope.getParams().request.verifierList != null) {
            _ref = $scope.getParams().request.verifierList;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              user = _ref[_i];
              _ref2 = $scope.users;
              for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                userToTest = _ref2[_j];
                if (user.identifier === userToTest.identifier) {
                  userToTest.selected = true;
                }
              }
              $scope.usersSelected.push(user);
            }
          }
          return $scope.isLoading = false;
        }
      });
      $scope.$watch('users', function() {
        var user, _i, _len, _ref, _results;
        $scope.usersSelected = [];
        if ($scope.users != null) {
          _ref = $scope.users;
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            user = _ref[_i];
            _results.push(user.selected === true ? $scope.usersSelected.push(user) : void 0);
          }
          return _results;
        }
      }, true);
      $scope.allFieldValid = function() {
        return $scope.usersSelected.length > 0;
      };
      $scope.save = function() {
        var dto, user, usersSelectedIdentifier, _i, _len, _ref;
        if ($scope.allFieldValid() === true) {
          $scope.isLoading = true;
          usersSelectedIdentifier = [];
          _ref = $scope.usersSelected;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            user = _ref[_i];
            usersSelectedIdentifier.push(user.identifier);
          }
          dto = {
            newStatus: 'VERIFICATION_STATUS_VERIFICATION',
            scopeId: $scope.getParams().request.scope.id,
            periodKey: $scope.getParams().request.period.key,
            verifierIdentifier: usersSelectedIdentifier
          };
          return downloadService.postJson("/awac/verification/setStatus", dto, function(result) {
            if (!result.success) {
              return $scope.isLoading = false;
            } else {
              $scope.isLoading = false;
              $scope.getParams().request.status = 'VERIFICATION_STATUS_VERIFICATION';
              $scope.getParams().request.verifierList = $scope.usersSelected;
              return $scope.close();
            }
          });
        }
      };
      return $scope.close = function() {
        return modalService.close(modalService.VERIFICATION_ASSIGN);
      };
    }
  };
});angular.module('app.directives').directive("mmAwacVerificationSurvey", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-verification-survey.html",
    transclude: true,
    replace: true,
    controller: 'MainCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacAdminSurvey", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-admin-survey.html",
    transclude: true,
    replace: true,
    controller: 'MainCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacCalculatorSurvey", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-calculator-survey.html",
    transclude: true,
    replace: true,
    controller: 'MainCtrl',
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacFormNavigator", function(directiveService) {
  return {
    restrict: "E",
    templateUrl: "$/angular/templates/mm-awac-form-navigator.html",
    replace: true,
    link: function(scope) {
      return directiveService.autoScopeImpl(scope);
    }
  };
});angular.module('app.directives').directive("mmAwacVerificationForm", function(directiveService) {
  return {
    restrict: "E",
    templateUrl: function() {
      return "$/angular/views/enterprise/" + form + ".html";
    }
  };
});angular.module('app.directives').directive("mmPlusMinusToggleButton", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-plus-minus-toggle-button.html",
    link: function(scope) {
      directiveService.autoScopeImpl(scope);
      return scope.toggle = function() {
        if (scope.ngModel) {
          return scope.ngModel = false;
        } else {
          return scope.ngModel = true;
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacResultTable", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-result-table.html",
    replace: true,
    transclude: true,
    link: function(scope, element) {
      directiveService.autoScopeImpl(scope);
      scope.getLeftTotalScope1 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.leftScope1Value;
        }
        return total;
      };
      scope.getLeftTotalScope2 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.leftScope2Value;
        }
        return total;
      };
      scope.getLeftTotalScope3 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.leftScope3Value;
        }
        return total;
      };
      scope.getLeftTotalOutOfScope = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.leftOutOfScopeValue;
        }
        return total;
      };
      scope.getRightTotalScope1 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.rightScope1Value;
        }
        return total;
      };
      scope.getRightTotalScope2 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.rightScope2Value;
        }
        return total;
      };
      scope.getRightTotalScope3 = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.rightScope3Value;
        }
        return total;
      };
      return scope.getRightTotalOutOfScope = function() {
        var rl, total, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        total = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          rl = _ref[_i];
          total += rl.rightOutOfScopeValue;
        }
        return total;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacResultLegend", function(directiveService, $filter) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '=',
      ngMode: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-result-legend.html",
    replace: true,
    link: function(scope, element) {
      directiveService.autoScopeImpl(scope);
      scope.getNumber = function(rl) {
        var considerAll, index, l, result, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        if (!rl) {
          return void 0;
        }
        considerAll = scope.isSimple();
        result = null;
        index = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          l = _ref[_i];
          if (considerAll || (l.leftScope1Value + l.leftScope2Value + l.leftScope3Value + l.leftOutOfScopeValue + l.rightScope1Value + l.rightScope2Value + l.rightScope3Value + l.rightOutOfScopeValue > 0)) {
            index += 1;
            if (l.indicatorName === rl.indicatorName) {
              result = index;
              break;
            }
          }
        }
        return result;
      };
      scope.getReportLines = function() {
        var considerAll;
        if (!scope.ngModel) {
          return void 0;
        }
        considerAll = scope.isSimple();
        if (considerAll) {
          return scope.ngModel.reportLines;
        } else {
          return $filter('filter')(scope.ngModel.reportLines, {
            color: '!!'
          });
        }
      };
      return scope.isSimple = function() {
        return scope.$root.instanceName === 'household' || scope.$root.instanceName === 'event' || scope.$root.instanceName === 'littleemitter';
      };
    }
  };
});angular.module('app.directives').directive("mmAwacResultLegendSimple", function(directiveService) {
  return {
    restrict: "E",
    scope: directiveService.autoScope({
      ngModel: '=',
      ngIdeal: '=',
      ngType: '=',
      ngIdealColor: '=',
      ngTypeColor: '='
    }),
    templateUrl: "$/angular/templates/mm-awac-result-legend-simple.html",
    replace: true,
    link: function(scope, element) {
      directiveService.autoScopeImpl(scope);
      scope.getNumber = function(indicator) {
        var index, l, result, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        if (!indicator) {
          return void 0;
        }
        result = null;
        index = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          l = _ref[_i];
          index += 1;
          if (l.indicatorName === indicator) {
            result = index;
            break;
          }
        }
        return result;
      };
      scope.getLeftTotal = function(indicator) {
        var index, l, result, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        if (!indicator) {
          return void 0;
        }
        result = null;
        index = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          l = _ref[_i];
          if (l.indicatorName === indicator) {
            result = l.leftScope1Value + l.leftScope2Value + l.leftScope3Value;
            break;
          }
        }
        return result;
      };
      scope.getRightTotal = function(indicator) {
        var index, l, result, _i, _len, _ref;
        if (!scope.ngModel) {
          return void 0;
        }
        if (!indicator) {
          return void 0;
        }
        result = null;
        index = 0;
        _ref = scope.ngModel.reportLines;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          l = _ref[_i];
          if (l.indicatorName === indicator) {
            result = l.rightScope1Value + l.rightScope2Value + l.rightScope3Value;
            break;
          }
        }
        return result;
      };
      scope.isTypicalShown = function() {
        var k, v, _ref;
        _ref = scope.ngType;
        for (k in _ref) {
          v = _ref[k];
          if (v > 0) {
            return true;
          }
        }
        return false;
      };
      return scope.isIdealShown = function() {
        var k, v, _ref;
        _ref = scope.ngIdeal;
        for (k in _ref) {
          v = _ref[k];
          if (v > 0) {
            return true;
          }
        }
        return false;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRegistrationHousehold", function(directiveService, downloadService, messageFlash, translationService, modalService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-registration-household.html",
    replace: true,
    controller: function($scope) {
      $scope.validatePasswordConfirmField = function() {
        if ((($scope.fields.passwordInfo.isValid != null) === true && true > 0) && $scope.fields.passwordInfo.field === $scope.fields.passwordConfirmInfo.field) {
          return true;
        }
        return false;
      };
      $scope.fields = {
        identifierInfo: {
          fieldTitle: "USER_IDENTIFIER",
          inputName: 'identifier',
          validationRegex: "^\\S{5,20}$",
          validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"
        },
        lastNameInfo: {
          fieldTitle: "USER_LASTNAME",
          inputName: 'lastName',
          validationRegex: "^.{1,255}$",
          validationMessage: "USER_LASTNAME_WRONG_LENGTH"
        },
        firstNameInfo: {
          fieldTitle: "USER_FIRSTNAME",
          inputName: 'firstName',
          fieldType: "text",
          validationRegex: "^.{1,255}$",
          validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
          focus: function() {
            return $scope.$parent.tabActive[2];
          }
        },
        emailInfo: {
          fieldTitle: "USER_EMAIL",
          inputName: 'email',
          validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
          validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
        },
        passwordInfo: {
          fieldTitle: "USER_PASSWORD",
          inputName: 'password',
          fieldType: "password",
          validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        },
        passwordConfirmInfo: {
          fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
          inputName: 'password',
          fieldType: "password",
          validationFct: $scope.validatePasswordConfirmField,
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        }
      };
      $scope.aggrement_validation = false;
      $scope.organizationStatisticsAllowed = true;
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        if ($scope.aggrement_validation === false) {
          return false;
        }
        return true;
      };
      $scope.displayAgreement = function() {
        return modalService.show(modalService.HELP, {
          template: 'agreement'
        });
      };
      return $scope.registration = function() {
        var data;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          data = {};
          data.person = {};
          data.person.email = $scope.fields.emailInfo.field;
          data.person.identifier = $scope.fields.identifierInfo.field;
          data.person.firstName = $scope.fields.firstNameInfo.field;
          data.person.lastName = $scope.fields.lastNameInfo.field;
          data.password = $scope.fields.passwordInfo.field;
          data.organizationStatisticsAllowed = $scope.fields.organizationStatisticsAllowed;
          data.person.defaultLanguage = $scope.$root.language;
          downloadService.postJson('/awac/registration/household', data, function(result) {
            if (result.success) {
              $scope.$root.loginSuccess(result.data);
              messageFlash.displaySuccess(translationService.get("CONNECTION_MESSAGE_SUCCESS"));
              return $scope.isLoading = false;
            } else {
              return $scope.isLoading = false;
            }
          });
          return false;
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRegistrationMunicipality", function(directiveService, downloadService, messageFlash, modalService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-registration-municipality.html",
    replace: true,
    controller: function($scope) {
      $scope.validatePasswordConfirmField = function() {
        if ((($scope.passwordInfo.isValid != null) === true && true > 0) && $scope.passwordInfo.field === $scope.passwordConfirmInfo.field) {
          return true;
        }
        return false;
      };
      $scope.identifierInfo = {
        fieldTitle: "USER_IDENTIFIER",
        inputName: 'identifier',
        validationRegex: "^\\S{5,20}$",
        validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"
      };
      $scope.lastNameInfo = {
        fieldTitle: "USER_LASTNAME",
        inputName: 'lastName',
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_LASTNAME_WRONG_LENGTH"
      };
      $scope.firstNameInfo = {
        fieldTitle: "USER_FIRSTNAME",
        inputName: 'firstName',
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
        focus: function() {
          return $scope.$parent.tabActive[2];
        }
      };
      $scope.emailInfo = {
        fieldTitle: "USER_EMAIL",
        inputName: 'email',
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
      };
      $scope.passwordInfo = {
        fieldTitle: "USER_PASSWORD",
        inputName: 'password',
        fieldType: "password",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.passwordConfirmInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        inputName: 'password',
        fieldType: "password",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.municipalityNameInfo = {
        fieldTitle: "MUNICIPALITY_NAME",
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "MUNICIPALITY_NAME_WRONG_LENGTH"
      };
      $scope.organizationStatisticsAllowed = true;
      $scope.aggrement_validation = false;
      $scope.registrationFieldValid = function() {
        if ($scope.identifierInfo.isValid && $scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid && $scope.emailInfo.isValid && $scope.passwordInfo.isValid && $scope.passwordConfirmInfo.isValid && $scope.municipalityNameInfo.isValid && $scope.aggrement_validation === true) {
          return true;
        }
        return false;
      };
      $scope.displayAgreement = function() {
        return modalService.show(modalService.HELP, {
          template: 'agreement'
        });
      };
      return $scope.registration = function() {
        var data;
        $scope.isLoading = true;
        data = {};
        data.person = {};
        data.person.email = $scope.emailInfo.field;
        data.person.identifier = $scope.identifierInfo.field;
        data.person.firstName = $scope.firstNameInfo.field;
        data.person.lastName = $scope.lastNameInfo.field;
        data.password = $scope.passwordInfo.field;
        data.organizationName = $scope.municipalityNameInfo.field;
        data.organizationStatisticsAllowed = $scope.organizationStatisticsAllowed;
        data.person.defaultLanguage = $scope.$root.language;
        downloadService.postJson('/awac/registration/municipality', data, function(result) {
          if (result.success) {
            $scope.$root.loginSuccess(result.data);
            messageFlash.displaySuccess(translationService.get("CONNECTION_MESSAGE_SUCCESS"));
            return $scope.isLoading = false;
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRegistrationLittleemitter", function(directiveService, downloadService, messageFlash, translationService, modalService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-registration-littleemitter.html",
    replace: true,
    controller: function($scope) {
      $scope.validatePasswordConfirmField = function() {
        if ((($scope.fields.passwordInfo.isValid != null) === true && true > 0) && $scope.fields.passwordInfo.field === $scope.fields.passwordConfirmInfo.field) {
          return true;
        }
        return false;
      };
      $scope.fields = {
        identifierInfo: {
          fieldTitle: "USER_IDENTIFIER",
          inputName: 'identifier',
          validationRegex: "^\\S{5,20}$",
          validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"
        },
        lastNameInfo: {
          fieldTitle: "USER_LASTNAME",
          inputName: 'lastName',
          validationRegex: "^.{1,255}$",
          validationMessage: "USER_LASTNAME_WRONG_LENGTH"
        },
        firstNameInfo: {
          fieldTitle: "USER_FIRSTNAME",
          inputName: 'firstName',
          fieldType: "text",
          validationRegex: "^.{1,255}$",
          validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
          focus: function() {
            return $scope.$parent.tabActive[2];
          }
        },
        emailInfo: {
          fieldTitle: "USER_EMAIL",
          inputName: 'email',
          validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
          validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
        },
        passwordInfo: {
          fieldTitle: "USER_PASSWORD",
          inputName: 'password',
          fieldType: "password",
          validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        },
        passwordConfirmInfo: {
          fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
          inputName: 'password',
          fieldType: "password",
          validationFct: $scope.validatePasswordConfirmField,
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        },
        organizationNameInfo: {
          fieldTitle: "ORGANIZATION_NAME",
          fieldType: "text",
          validationRegex: "^.{1,255}$",
          validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
        }
      };
      $scope.aggrement_validation = false;
      $scope.organizationStatisticsAllowed = true;
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        if ($scope.aggrement_validation === false) {
          return false;
        }
        return true;
      };
      $scope.displayAgreement = function() {
        return modalService.show(modalService.HELP, {
          template: 'agreement'
        });
      };
      return $scope.registration = function() {
        var data;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          data = {};
          data.person = {};
          data.person.email = $scope.fields.emailInfo.field;
          data.person.identifier = $scope.fields.identifierInfo.field;
          data.person.firstName = $scope.fields.firstNameInfo.field;
          data.person.lastName = $scope.fields.lastNameInfo.field;
          data.password = $scope.fields.passwordInfo.field;
          data.organizationName = $scope.fields.organizationNameInfo.field;
          data.organizationStatisticsAllowed = $scope.fields.organizationStatisticsAllowed;
          data.person.defaultLanguage = $scope.$root.language;
          downloadService.postJson('/awac/registration/littleemitter', data, function(result) {
            if (result.success) {
              $scope.$root.loginSuccess(result.data);
              messageFlash.displaySuccess(translationService.get("CONNECTION_MESSAGE_SUCCESS"));
              return $scope.isLoading = false;
            } else {
              return $scope.isLoading = false;
            }
          });
          return false;
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRegistrationEnterprise", function(directiveService, downloadService, messageFlash, translationService, modalService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-registration-enterprise.html",
    replace: true,
    controller: function($scope) {
      $scope.validatePasswordConfirmField = function() {
        if ((($scope.passwordInfo.isValid != null) === true && true > 0) && $scope.passwordInfo.field === $scope.passwordConfirmInfo.field) {
          return true;
        }
        return false;
      };
      $scope.identifierInfo = {
        fieldTitle: "USER_IDENTIFIER",
        inputName: 'identifier',
        validationRegex: "^\\S{5,20}$",
        validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"
      };
      $scope.lastNameInfo = {
        fieldTitle: "USER_LASTNAME",
        inputName: 'lastName',
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_LASTNAME_WRONG_LENGTH"
      };
      $scope.firstNameInfo = {
        fieldTitle: "USER_FIRSTNAME",
        inputName: 'firstName',
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
        focus: function() {
          return $scope.$parent.tabActive[2];
        }
      };
      $scope.emailInfo = {
        fieldTitle: "USER_EMAIL",
        inputName: 'email',
        validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
        validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
      };
      $scope.passwordInfo = {
        fieldTitle: "USER_PASSWORD",
        inputName: 'password',
        fieldType: "password",
        validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.passwordConfirmInfo = {
        fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
        inputName: 'password',
        fieldType: "password",
        validationFct: $scope.validatePasswordConfirmField,
        validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
      };
      $scope.organizationNameInfo = {
        fieldTitle: "ORGANIZATION_NAME",
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
      };
      $scope.organizationStatisticsAllowed = true;
      $scope.firstSiteNameInfo = {
        fieldTitle: "MAIN_SITE_NAME",
        fieldType: "text",
        validationRegex: "^.{1,255}$",
        validationMessage: "SITE_NAME_WRONG_LENGTH"
      };
      $scope.aggrement_validation = false;
      $scope.registrationFieldValid = function() {
        if ($scope.identifierInfo.isValid && $scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid && $scope.emailInfo.isValid && $scope.passwordInfo.isValid && $scope.passwordConfirmInfo.isValid && $scope.organizationNameInfo.isValid && $scope.firstSiteNameInfo.isValid && $scope.aggrement_validation === true) {
          return true;
        }
        return false;
      };
      $scope.displayAgreement = function() {
        return modalService.show(modalService.HELP, {
          template: 'agreement'
        });
      };
      return $scope.registration = function() {
        var data;
        $scope.isLoading = true;
        data = {};
        data.person = {};
        data.person.email = $scope.emailInfo.field;
        data.person.identifier = $scope.identifierInfo.field;
        data.person.firstName = $scope.firstNameInfo.field;
        data.person.lastName = $scope.lastNameInfo.field;
        data.password = $scope.passwordInfo.field;
        data.organizationName = $scope.organizationNameInfo.field;
        data.organizationStatisticsAllowed = $scope.organizationStatisticsAllowed;
        data.firstSiteName = $scope.firstSiteNameInfo.field;
        data.person.defaultLanguage = $scope.$root.language;
        downloadService.postJson('/awac/registration/enterprise', data, function(result) {
          if (result.success) {
            $scope.$root.loginSuccess(result.data);
            messageFlash.displaySuccess(translationService.get("CONNECTION_MESSAGE_SUCCESS"));
            return $scope.isLoading = false;
          } else {
            return $scope.isLoading = false;
          }
        });
        return false;
      };
    }
  };
});angular.module('app.directives').directive("mmAwacRegistrationEvent", function(directiveService, downloadService, messageFlash, translationService, modalService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-registration-event.html",
    replace: true,
    controller: function($scope) {
      $scope.validatePasswordConfirmField = function() {
        if ((($scope.fields.passwordInfo.isValid != null) === true && true > 0) && $scope.fields.passwordInfo.field === $scope.fields.passwordConfirmInfo.field) {
          return true;
        }
        return false;
      };
      $scope.fields = {
        identifierInfo: {
          fieldTitle: "USER_IDENTIFIER",
          inputName: 'identifier',
          validationRegex: "^\\S{5,20}$",
          validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH"
        },
        lastNameInfo: {
          fieldTitle: "USER_LASTNAME",
          inputName: 'lastName',
          validationRegex: "^.{1,255}$",
          validationMessage: "USER_LASTNAME_WRONG_LENGTH"
        },
        firstNameInfo: {
          fieldTitle: "USER_FIRSTNAME",
          inputName: 'firstName',
          fieldType: "text",
          validationRegex: "^.{1,255}$",
          validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
          focus: function() {
            return $scope.$parent.tabActive[2];
          }
        },
        emailInfo: {
          fieldTitle: "USER_EMAIL",
          inputName: 'email',
          validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
          validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
        },
        passwordInfo: {
          fieldTitle: "USER_PASSWORD",
          inputName: 'password',
          fieldType: "password",
          validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        },
        passwordConfirmInfo: {
          fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
          inputName: 'password',
          fieldType: "password",
          validationFct: $scope.validatePasswordConfirmField,
          validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
        },
        organizationNameInfo: {
          fieldTitle: "ORGANIZATION_NAME",
          fieldType: "text",
          validationRegex: "^.{1,255}$",
          validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
        },
        firstProductNameInfo: {
          fieldTitle: "MAIN_EVENT_NAME",
          fieldType: "text",
          validationRegex: "^.{1,255}$",
          validationMessage: "SITE_NAME_WRONG_LENGTH"
        }
      };
      $scope.aggrement_validation = false;
      $scope.organizationStatisticsAllowed = true;
      $scope.allFieldValid = function() {
        var key, _i, _len, _ref;
        _ref = Object.keys($scope.fields);
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          key = _ref[_i];
          if (key !== '$$hashKey') {
            if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
              return false;
            }
          }
        }
        if ($scope.aggrement_validation === false) {
          return false;
        }
        return true;
      };
      $scope.displayAgreement = function() {
        return modalService.show(modalService.HELP, {
          template: 'agreement'
        });
      };
      return $scope.registration = function() {
        var data;
        if ($scope.allFieldValid()) {
          $scope.isLoading = true;
          data = {};
          data.person = {};
          data.person.email = $scope.fields.emailInfo.field;
          data.person.identifier = $scope.fields.identifierInfo.field;
          data.person.firstName = $scope.fields.firstNameInfo.field;
          data.person.lastName = $scope.fields.lastNameInfo.field;
          data.password = $scope.fields.passwordInfo.field;
          data.organizationName = $scope.fields.organizationNameInfo.field;
          data.firstProductName = $scope.fields.firstProductNameInfo.field;
          data.organizationStatisticsAllowed = $scope.fields.organizationStatisticsAllowed;
          data.person.defaultLanguage = $scope.$root.language;
          downloadService.postJson('/awac/registration/event', data, function(result) {
            if (result.success) {
              $scope.$root.loginSuccess(result.data);
              messageFlash.displaySuccess(translationService.get("CONNECTION_MESSAGE_SUCCESS"));
              return $scope.isLoading = false;
            } else {
              return $scope.isLoading = false;
            }
          });
          return false;
        }
      };
    }
  };
});angular.module('app.directives').directive("mmAdminTranslationsNavigationBar", function() {
  return {
    restrict: "E",
    scope: {
      ngModel: '='
    },
    templateUrl: "$/angular/templates/mm-admin-translations-navigation-bar.html",
    replace: true,
    controller: function($scope) {}
  };
});angular.module('app.directives').directive("mmAwacQuestionSetTree", function(RecursionHelper) {
  return {
    restrict: "E",
    scope: {
      ngModel: '='
    },
    templateUrl: "$/angular/templates/mm-awac-question-set-tree.html",
    replace: false,
    compile: function(element) {
      return RecursionHelper.compile(element, function(scope, iElement, iAttrs, controller, transcludeFn) {
        return scope.getPadding = function() {
          var p;
          p = 30;
          if (scope.$parent.getPadding) {
            p += scope.$parent.getPadding();
          }
          return p;
        };
      });
    }
  };
});angular.module('app.directives').directive("mmAwacAdminBadImporter", function(directiveService, $timeout, ngTableParams, $http, $filter, downloadService, messageFlash, modalService) {
  return {
    restrict: "E",
    scope: {},
    templateUrl: "$/angular/templates/mm-awac-admin-bad-importer.html",
    replace: true,
    transclude: true,
    link: function(scope) {
      var url;
      scope.total_bad = 0;
      scope.total_info = 0;
      scope.total_warning = 0;
      scope.total_error = 0;
      url = "awac/admin/badImporter/" + scope.$root.instanceName;
      scope["import"] = function() {
        scope.total_bad = 0;
        scope.total_bad_info = 0;
        scope.total_bad_warning = 0;
        scope.total_bad_error = 0;
        scope.total_question = 0;
        scope.total_question_info = 0;
        scope.total_question_warning = 0;
        scope.total_question_error = 0;
        modalService.show(modalService.LOADING);
        return downloadService.getJson(url, function(result) {
          var logBad, logLine, logQuestion, _i, _len, _ref;
          if (!result.success) {
            return modalService.close(modalService.LOADING);
          } else {
            logBad = [];
            logQuestion = [];
            _ref = result.data.logLines;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              logLine = _ref[_i];
              logLine.messagesWarningNb = 0;
              logLine.messagesInfoNb = 0;
              logLine.messagesErrorNb = 0;
              if (logLine.logCategory === 'BAD') {
                scope.total_bad++;
                logBad.push(logLine);
                if (logLine.messages['WARNING'] != null) {
                  logLine.messagesWarningNb = logLine.messages['WARNING'].length;
                  scope.total_bad_warning++;
                }
                if (logLine.messages['INFO'] != null) {
                  logLine.messagesInfoNb = logLine.messages['INFO'].length;
                  scope.total_bad_info++;
                }
                if (logLine.messages['ERROR'] != null) {
                  logLine.messagesErrorNb = logLine.messages['ERROR'].length;
                  scope.total_bad_error++;
                }
              }
              if (logLine.logCategory === 'QUESTION') {
                scope.total_question++;
                logQuestion.push(logLine);
                if (logLine.messages['WARNING'] != null) {
                  logLine.messagesWarningNb = logLine.messages['WARNING'].length;
                  scope.total_question_warning++;
                }
                if (logLine.messages['INFO'] != null) {
                  logLine.messagesInfoNb = logLine.messages['INFO'].length;
                  scope.total_question_info++;
                }
                if (logLine.messages['ERROR'] != null) {
                  logLine.messagesErrorNb = logLine.messages['ERROR'].length;
                  scope.total_question_error++;
                }
              }
            }
            scope.tableParams = new ngTableParams({
              page: 1,
              count: 100,
              sorting: {
                code: "asc"
              }
            }, {
              total: 0,
              getData: function($defer, params) {
                var orderedData;
                orderedData = $filter("orderBy")(logBad, params.orderBy());
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                return params.total(logBad.length);
              }
            });
            scope.tableParams2 = new ngTableParams({
              page: 1,
              count: 100,
              sorting: {
                code: "asc"
              }
            }, {
              total: 0,
              getData: function($defer, params) {
                var orderedData;
                orderedData = $filter("orderBy")(logQuestion, params.orderBy());
                $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
                return params.total(logQuestion.length);
              }
            });
            return modalService.close(modalService.LOADING);
          }
        });
      };
      return;
    }
  };
});angular.module('app.controllers').controller("AdminCtrl", function($scope, downloadService) {
  $scope.notifications = [];
  return downloadService.getJson("admin/get_notifications", function(dto) {
    if (dto != null) {
      $scope.notifications = dto.notifications;
    }
    return;
  });
});angular.module('app.controllers').controller("AdminTranslationSingleListCtrl", function($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper, displayLittleFormMenu, state, $location) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.state = state;
  $scope.codeLabels = {};
  $scope.waitingData = true;
  $scope.isLoading = false;
  $scope.baseListLabel = "";
  $scope.loadCodeLabels = function() {
    var baseList;
    if ($scope.state === 'error-messages') {
      baseList = 'TRANSLATIONS_ERROR_MESSAGES';
      $scope.baseListLabel = "Messages d'erreurs";
    } else if ($scope.state === 'emails') {
      baseList = 'TRANSLATIONS_EMAIL_MESSAGE';
      $scope.baseListLabel = "E-Mails";
    }
    downloadService.getJson("/awac/admin/translations/codelabels/load/" + baseList, function(result) {
      if (result.success) {
        $scope.codeLabelsByList = result.data.codeLabelsByList;
        $scope.codeLabels = codeLabelHelper.sortCodeLabelsByOrder($scope.codeLabelsByList[baseList]);
        $scope.initialCodeLabels = angular.copy($scope.codeLabels);
      }
      $scope.waitingData = false;
      return;
    });
    return;
  };
  $scope.save = function() {
    var data;
    $scope.isLoading = true;
    data = {
      codeLabelsByList: $scope.codeLabelsByList
    };
    return downloadService.postJson("/awac/admin/translations/codelabels/save", data, function(result) {
      $scope.isLoading = false;
      if (result.success) {
        $scope.loadCodeLabels();
        messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
        return translationService.reinitialize();
      }
    });
  };
  $scope.ignoreChanges = false;
  $scope.$root.$on('$locationChangeStart', function(event, next, current) {
    var eq, params;
    if (!!$scope.ignoreChanges) {
      return;
    }
    eq = angular.equals($scope.initialCodeLabels, $scope.codeLabels);
    if (!eq) {
      event.preventDefault();
      params = {
        titleKey: "CONFIRM_EXIT_TITLE",
        messageKey: "CONFIRM_EXIT_MESSAGE",
        onConfirm: function() {
          $scope.ignoreChanges = true;
          return $location.path(next.split('#')[1]);
        }
      };
      return modalService.show(modalService.CONFIRM_DIALOG, params);
    }
  });
  return $scope.loadCodeLabels();
});angular.module('app.controllers').controller("VerificationRegistrationCtrl", function($scope, downloadService, modalService, messageFlash, $routeParams, translationService) {
  $scope.isLoading = false;
  $scope.fields = {
    identifierInfo: {
      fieldTitle: "USER_IDENTIFIER",
      inputName: 'identifier',
      validationRegex: "[a-zA-Z0-9-]{5,20}",
      validationMessage: "IDENTIFIER_CHECK_WRONG"
    },
    lastNameInfo: {
      fieldTitle: "USER_LASTNAME",
      inputName: 'lastName',
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_LASTNAME_WRONG_LENGTH"
    },
    firstNameInfo: {
      fieldTitle: "USER_FIRSTNAME",
      inputName: 'firstName',
      fieldType: "text",
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
      focus: function() {
        return true;
      }
    },
    emailInfo: {
      fieldTitle: "USER_EMAIL",
      inputName: 'email',
      validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
      validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
    },
    passwordInfo: {
      fieldTitle: "USER_PASSWORD",
      inputName: 'password',
      fieldType: "password",
      validationRegex: "^[A-Za-z0-9#?!@$%^&*-]{5,20}$",
      validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
    },
    passwordConfirmInfo: {
      fieldTitle: "PASSWORD_CHANGE_FORM_NEW_PASSWORD_CONFIRM_FIELD_TITLE",
      inputName: 'password',
      fieldType: "password",
      validationFct: $scope.validatePasswordConfirmField,
      validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH"
    },
    organizationNameInfo: {
      fieldTitle: "ORGANIZATION_NAME",
      fieldType: "text",
      validationRegex: "^.{1,255}$",
      validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH"
    },
    agreementValidation: {
      isValid: false
    }
  };
  $scope.displayAgreement = function() {
    return modalService.show(modalService.HELP, {
      template: 'agreement'
    });
  };
  $scope.allFieldValid = function() {
    var key, _i, _len, _ref;
    _ref = Object.keys($scope.fields);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        if (!($scope.fields[key].isValid != null) || $scope.fields[key].isValid === false) {
          return false;
        }
      }
    }
    return true;
  };
  return $scope.registration = function() {
    var data;
    if ($scope.allFieldValid()) {
      $scope.isLoading = true;
      data = {};
      data.person = {};
      data.person.email = $scope.fields.emailInfo.field;
      data.person.identifier = $scope.fields.identifierInfo.field;
      data.person.firstName = $scope.fields.firstNameInfo.field;
      data.person.lastName = $scope.fields.lastNameInfo.field;
      data.password = $scope.fields.passwordInfo.field;
      data.organizationName = $scope.fields.organizationNameInfo.field;
      data.person.defaultLanguage = $scope.$root.language;
      data.key = $routeParams.key;
      downloadService.postJson('/awac/registration/validator', data, function(result) {
        if (result.success) {
          $scope.$root.loginSuccess(result.data);
          messageFlash.displaySuccess(translationService.get("CONNECTION_MESSAGE_SUCCESS"));
          return $scope.isLoading = false;
        } else {
          return $scope.isLoading = false;
        }
      });
      return false;
    }
  };
});angular.module('app.controllers').controller("ProductManagerCtrl", function($scope, translationService, modalService, downloadService, messageFlash) {
  $scope.isLoading = {};
  if ($scope.$root.periods != null) {
    $scope.assignPeriod = $scope.$root.periods[0].key;
  } else {
    $scope.$watch('$root.periods', function() {
      return $scope.assignPeriod = $scope.$root.periods[0].key;
    });
  }
  $scope.isPeriodChecked = {};
  $scope.selectedPeriodForEvent = $scope.$root.periods[0].key;
  modalService.show(modalService.LOADING);
  return downloadService.getJson('awac/organization/getMyOrganization', function(result) {
    if (!result.success) {
      return modalService.close(modalService.LOADING);
    } else {
      modalService.close(modalService.LOADING);
      $scope.organization = result.data;
      $scope.$watchCollection('assignPeriod', function() {
        return $scope.refreshPeriod();
      });
      $scope.refreshPeriod = function() {
        var product, _i, _len, _ref, _results;
        _ref = $scope.organization.products;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          product = _ref[_i];
          _results.push($scope.isPeriodChecked[product.id] = $scope.periodAssignTo(product));
        }
        return _results;
      };
      $scope.toForm = function() {
        return $scope.$root.navToLastFormUsed();
      };
      $scope.getProductList = function() {
        return $scope.organization.products;
      };
      $scope.editOrCreateProduct = function(product) {
        var params;
        params = {};
        if (product != null) {
          params.product = product;
        }
        params.organization = $scope.organization;
        params.refreshMyProduct = function() {
          $scope.refreshMyProducts();
          return $scope.refreshPeriod();
        };
        return modalService.show(modalService.EDIT_PRODUCT, params);
      };
      $scope.addUsers = function(product) {
        var params;
        params = {};
        if (product != null) {
          params.product = product;
        }
        params.organization = $scope.organization;
        return modalService.show(modalService.ADD_USER_PRODUCT, params);
      };
      $scope.periodAssignTo = function(product) {
        var period, _i, _len, _ref;
        if (product.listPeriodAvailable != null) {
          _ref = product.listPeriodAvailable;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            period = _ref[_i];
            if (period.key === $scope.assignPeriod) {
              return true;
            }
          }
        }
        return false;
      };
      $scope.assignPeriodToProduct = function(product) {
        var data;
        $scope.isLoading[product.id] = true;
        data = {
          periodKeyCode: $scope.assignPeriod,
          productId: product.id,
          assign: !$scope.periodAssignTo(product)
        };
        return downloadService.postJson('awac/product/assignPeriodToProduct', data, function(result) {
          $scope.isLoading[product.id] = false;
          if (result.success) {
            product.listPeriodAvailable = result.data.periodsList;
            return $scope.refreshMyProducts();
          }
        });
      };
      return $scope.refreshMyProducts = function() {
        var myScope, person, product, _i, _j, _len, _len2, _ref, _ref2;
        myScope = [];
        _ref = $scope.organization.products;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          product = _ref[_i];
          if (product.listPersons != null) {
            _ref2 = product.listPersons;
            for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
              person = _ref2[_j];
              if (person.identifier === $scope.$root.currentPerson.identifier) {
                myScope.push(product);
              }
            }
          }
        }
        return $scope.$root.mySites = myScope;
      };
    }
  });
});angular.module('app.controllers').controller("OrganizationManagerCtrl", function($scope, translationService, modalService, downloadService, messageFlash) {
  $scope.isLoading = false;
  if ($scope.$root.periods != null) {
    $scope.assignPeriod = $scope.$root.periods[0].key;
  } else {
    $scope.$watch('$root.periods', function() {
      return $scope.assignPeriod = $scope.$root.periods[0].key;
    });
  }
  $scope.isPeriodChecked = {};
  $scope.selectedPeriodForEvent = $scope.$root.periods[0].key;
  $scope.allFieldValid = function() {
    return $scope.nameInfo.isValid && (($scope.nameInfo.field !== $scope.organization.name) || ($scope.statisticsAllowed !== $scope.organization.statisticsAllowed));
  };
  $scope.nameInfo = {
    fieldTitle: ($scope.$root.instanceName === 'household' ? "HOUSEHOLD_NAME" : "ORGANIZATION_NAME"),
    validationRegex: "^.{1,255}$",
    validationMessage: "ORGANIZATION_NAME_WRONG_LENGTH",
    field: null,
    hideIsValidIcon: true,
    isValid: true,
    focus: function() {
      return true;
    }
  };
  $scope.statisticsAllowed = false;
  modalService.show(modalService.LOADING);
  return downloadService.getJson('awac/organization/getMyOrganization', function(result) {
    modalService.close(modalService.LOADING);
    if (result.success) {
      $scope.organization = result.data;
      $scope.nameInfo.field = $scope.organization.name;
      $scope.statisticsAllowed = $scope.organization.statisticsAllowed;
      downloadService.getJson('awac/organization/events/load', function(result) {
        if (result.success) {
          return $scope.events = result.data.organizationEventList;
        }
      });
      $scope.toForm = function() {
        return $scope.$root.navToLastFormUsed();
      };
      $scope.editOrCreateEvent = function(event) {
        var params, period, _i, _len, _ref;
        params = {};
        params.organization = $scope.organization;
        params.events = $scope.events;
        _ref = $scope.$root.periods;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          period = _ref[_i];
          if (period.key === $scope.selectedPeriodForEvent) {
            params.period = period;
          }
        }
        if (event != null) {
          params.event = event;
        }
        return modalService.show(modalService.EDIT_EVENT, params);
      };
      return $scope.saveOrganization = function() {
        var data;
        if (!$scope.allFieldValid) {
          return false;
        }
        data = {
          name: $scope.nameInfo.field,
          statisticsAllowed: $scope.statisticsAllowed
        };
        $scope.isLoading = true;
        return downloadService.postJson('/awac/organization/update', data, function(result) {
          $scope.isLoading = false;
          if (result.success) {
            messageFlash.displaySuccess("CHANGES_SAVED");
            $scope.$root.organizationName = $scope.nameInfo.field;
          }
          return false;
        });
      };
    }
  });
});angular.module('app.controllers').controller("NoScopeCtrl", function($scope, displayLittleFormMenu) {
  return $scope.displayLittleFormMenu = displayLittleFormMenu;
});angular.module('app.controllers').controller("AdminTranslationSubListCtrl", function($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper, displayLittleFormMenu, $location) {
  var updateOrderIndexes;
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.subLists = [];
  $scope.codeLabels = {};
  $scope.isLoading = false;
  $scope.waitingData = true;
  $scope.itemToAdd = {
    key: "",
    clear: function() {
      this.key = "";
      return;
    }
  };
  $scope.loadSubLists = function() {
    downloadService.getJson("awac/admin/translations/sublists/load", function(result) {
      var codeLabelList, _i, _len, _ref;
      if (result.success) {
        $scope.subLists = $scope.sortLinkedListsItems(result.data.sublists);
        $scope.subLists = _.sortBy($scope.subLists, function(list) {
          return list.codeList;
        });
        $scope.initialSubLists = angular.copy($scope.subLists);
        _ref = result.data.codeLabels;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          codeLabelList = _ref[_i];
          $scope.codeLabels[codeLabelList.code] = codeLabelHelper.sortCodeLabelsByNumericKey(codeLabelList.codeLabels);
        }
        $scope.waitingData = false;
      }
      return;
    });
    return;
  };
  updateOrderIndexes = function(baseLists) {
    var baseList, codeLabel, index, _i, _len, _ref;
    for (_i = 0, _len = baseLists.length; _i < _len; _i++) {
      baseList = baseLists[_i];
      _ref = baseList.codeLabels;
      for (index in _ref) {
        codeLabel = _ref[index];
        codeLabel.orderIndex = +index + 1;
      }
    }
    return baseLists;
  };
  $scope.save = function() {
    var data;
    $scope.isLoading = true;
    data = {
      sublists: updateOrderIndexes($scope.subLists)
    };
    downloadService.postJson("awac/admin/translations/sublists/save", data, function(result) {
      $scope.isLoading = false;
      if (result.success) {
        messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
        angular.extend($scope.subLists, $scope.sortLinkedListsItems(result.data.sublists));
        $scope.subLists = _.sortBy($scope.subLists, function(list) {
          return list.codeList;
        });
        translationService.reinitialize();
      }
      return;
    });
    return;
  };
  $scope.addItem = function(subList) {
    if (!(!!$scope.itemToAdd.key)) {
      messageFlash.displayError("Veuillez sélectionner un élément de la liste!");
      return;
    }
    if (!!_.findWhere(subList.items, {
      'key': $scope.itemToAdd.key
    })) {
      messageFlash.displayError("Echec de l'ajout: la clé '" + $scope.itemToAdd.key + "' est déjà présente dans cette liste!");
      return;
    }
    subList.items.push(angular.copy($scope.itemToAdd));
    $scope.itemToAdd.clear();
    return;
  };
  $scope.sortLinkedListsItems = function(linkedLists) {
    var linkedList, _i, _len;
    for (_i = 0, _len = linkedLists.length; _i < _len; _i++) {
      linkedList = linkedLists[_i];
      linkedList.items = _.sortBy(linkedList.items, function(item) {
        return item.orderIndex;
      });
    }
    return linkedLists;
  };
  $scope.getLabelByKey = function(codeList, key) {
    return codeLabelHelper.getLabelByKey($scope.codeLabels[codeList], key);
  };
  $scope.ignoreChanges = false;
  $scope.$root.$on('$locationChangeStart', function(event, next, current) {
    var eq, params;
    if (!!$scope.ignoreChanges) {
      return;
    }
    eq = angular.equals($scope.initialSubLists, updateOrderIndexes($scope.subLists));
    if (!eq) {
      event.preventDefault();
      params = {
        titleKey: "CONFIRM_EXIT_TITLE",
        messageKey: "CONFIRM_EXIT_MESSAGE",
        onConfirm: function() {
          $scope.ignoreChanges = true;
          return $location.path(next.split('#')[1]);
        }
      };
      return modalService.show(modalService.CONFIRM_DIALOG, params);
    }
  });
  return $scope.loadSubLists();
});angular.module('app.controllers').controller("FormCtrl", function($scope, downloadService, messageFlash, translationService, modalService, $route, $timeout, $filter) {
  $scope.forms = {
    enterprise: ['TAB2', 'TAB3', 'TAB4', 'TAB5', 'TAB6', 'TAB7'],
    household: ['TAB_M1', 'TAB_M2', 'TAB_M3', 'TAB_M4', 'TAB_M5'],
    littleemitter: ['TAB_P1', 'TAB_P2', 'TAB_P3', 'TAB_P4', 'TAB_P5', 'TAB_P6'],
    municipality: ['TAB_C1', 'TAB_C2', 'TAB_C3', 'TAB_C4', 'TAB_C5'],
    event: ['TAB_EV1', 'TAB_EV2', 'TAB_EV3', 'TAB_EV4', 'TAB_EV5', 'TAB_EV6']
  };
  $scope.displayFormMenu = true;
  $scope.init = function(route) {
    var array, idx;
    $scope.formIdentifier = route;
    array = $scope.forms[$scope.$root.instanceName];
    idx = _.indexOf(array, route);
    $scope.previousFormIdentifier = null;
    if (idx > 0) {
      $scope.previousFormIdentifier = array[idx - 1];
    }
    $scope.nextFormIdentifier = null;
    if (idx < array.length - 1) {
      $scope.nextFormIdentifier = array[idx + 1];
    }
    $scope.tabSet = {};
    $scope.dataToCompare = null;
    $scope.answerList = [];
    $scope.mapRepetition = {};
    $scope.mapQuestionSet = {};
    /*
    illustration of the structures
    $scope.mapRepetition['A15'] = [{'A15':1},
                                    {'A15':2}]

    $scope.mapRepetition['A16'] = [{'A16':1,'A15':1},
                                   {'A16':2,'A15':1}]
    */
    /*
    list of the questionSet key locked with locker like personDTO
    */
    $scope.datalocker = {};
    $scope.dataValidator = {};
    $scope.dataVerification = {};
    $scope.$on('CLEAN_VERIFICATION', function() {
      return $scope.dataVerification = [];
    });
    $scope.loading = true;
    $scope.errorMessage = null;
    modalService.show(modalService.LOADING);
    downloadService.getJson("/awac/answer/getByForm/" + $scope.formIdentifier + "/" + $scope.$root.periodSelectedKey + "/" + $scope.$root.scopeSelectedId, function(result) {
      var answerSave, args, qSet, questionSetDTO, _i, _j, _len, _len2, _ref, _ref2;
      if (!result.success) {
        if (!!result.data.messageToTranslate) {
          $scope.errorMessage = $filter('translateText')(result.data.messageToTranslate);
        } else {
          $scope.errorMessage = result.data.message;
        }
        return modalService.close(modalService.LOADING);
      } else {
        console.log("LOADED DATA : ");
        console.log(result.data);
        $scope.$root.lastPeriodSelectedKey = $scope.$root.periodSelectedKey;
        $scope.$root.lastScopeSelectedId = $scope.$root.scopeSelectedId;
        $scope.$root.lastFormIdentifier = $scope.formIdentifier;
        $scope.o = angular.copy(result.data);
        if ($scope.o.answersSave !== null && $scope.o.answersSave !== void 0) {
          answerSave = $scope.o.answersSave;
          $scope.answerList = answerSave.listAnswers;
        }
        $scope.loopRepetition = function(questionSetDTO, listQuestionSetRepetition, parent) {
          var answer, child, questionSetElement, _i, _j, _len, _len2, _ref, _ref2, _results;
          if (listQuestionSetRepetition == null) {
            listQuestionSetRepetition = [];
          }
          if (questionSetDTO.datalocker != null) {
            $scope.datalocker[questionSetDTO.code] = questionSetDTO.datalocker;
          }
          if (questionSetDTO.dataValidator != null) {
            $scope.dataValidator[questionSetDTO.code] = questionSetDTO.dataValidator;
          }
          if (questionSetDTO.verification != null) {
            $scope.dataVerification[questionSetDTO.code] = questionSetDTO.verification;
          }
          questionSetElement = {
            questionSetDTO: questionSetDTO,
            parent: parent
          };
          $scope.mapQuestionSet[questionSetDTO.code] = questionSetElement;
          if (questionSetDTO.repetitionAllowed === true) {
            listQuestionSetRepetition[listQuestionSetRepetition.length] = questionSetDTO.code;
            _ref = $scope.answerList;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              answer = _ref[_i];
              if (answer.mapRepetition !== null && answer.mapRepetition !== void 0) {
                if (answer.mapRepetition[questionSetDTO.code] !== null && answer.mapRepetition[questionSetDTO.code] !== void 0 && answer.mapRepetition[questionSetDTO.code] !== 0) {
                  $scope.addMapRepetition(questionSetDTO.code, answer.mapRepetition, listQuestionSetRepetition);
                }
              }
            }
          }
          if (questionSetDTO.children) {
            _ref2 = questionSetDTO.children;
            _results = [];
            for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
              child = _ref2[_j];
              _results.push($scope.loopRepetition(child, angular.copy(listQuestionSetRepetition), questionSetElement));
            }
            return _results;
          }
        };
        $scope.addDefaultValue = function(questionSetDTO) {
          var answer, child, question, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3, _results;
          _ref = questionSetDTO.questions;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            question = _ref[_i];
            if (question.defaultValue !== void 0 && question.defaultValue !== null) {
              _ref2 = $scope.answerList;
              for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                answer = _ref2[_j];
                if (answer.questionKey === question.code && answer.value === null) {
                  answer.value = question.defaultValue;
                }
              }
            }
          }
          _ref3 = questionSetDTO.children;
          _results = [];
          for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
            child = _ref3[_k];
            _results.push($scope.addDefaultValue(child));
          }
          return _results;
        };
        args = {};
        args.time = $scope.o.answersSave.lastUpdateDate;
        if ($scope.$root != null) {
          $scope.$root.$broadcast("REFRESH_LAST_SAVE_TIME", args);
        }
        _ref = $scope.o.questionSets;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          qSet = _ref[_i];
          $scope.loopRepetition(qSet);
        }
        _ref2 = $scope.o.questionSets;
        for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
          questionSetDTO = _ref2[_j];
          $scope.addDefaultValue(questionSetDTO);
        }
        $timeout(function() {
          var answer, _i, _len, _ref;
          _ref = $scope.answerList;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            answer = _ref[_i];
            $scope.createTabWatcher(answer);
          }
          return $timeout(function() {
            var answer, _i, _len, _ref;
            modalService.close(modalService.LOADING);
            $scope.loading = false;
            _ref = $scope.answerList;
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              answer = _ref[_i];
              if (answer.hasValidCondition === false && answer.value !== null) {
                answer.value = null;
              }
            }
            return $scope.$root.$broadcast('FORM_LOADING_FINISH');
          }, 0);
        }, 0);
        return;
      }
    });
    $scope.$on('SAVE_AND_NAV', function(event, args) {
      return $scope.save(args);
    });
    $scope.$on('SAVE', function() {
      return $scope.save();
    });
    $scope.save = function(argToNav) {
      var answer, finalList, listAnswerToSave, _i, _j, _len, _len2, _ref;
      if (argToNav == null) {
        argToNav = null;
      }
      modalService.show(modalService.LOADING);
      listAnswerToSave = [];
      _ref = $scope.answerList;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
          if (answer.hasValidCondition !== void 0 && answer.hasValidCondition === false) {
            answer.value = null;
          }
          listAnswerToSave[listAnswerToSave.length] = answer;
        }
      }
      finalList = [];
      for (_j = 0, _len2 = listAnswerToSave.length; _j < _len2; _j++) {
        answer = listAnswerToSave[_j];
        if (!$scope.isQuestionLocked(answer.questionKey) && !$scope.isQuestionValidate(answer.questionKey)) {
          finalList.push(answer);
        }
      }
      if (finalList.length === 0) {
        messageFlash.displayInfo(translationService.get('ALL_ANSWERS_ALREADY_SAVED'));
        return modalService.close(modalService.LOADING);
      } else {
        $scope.o.answersSave.listAnswers = finalList;
        console.log("SAVE");
        console.log($scope.o.answersSave);
        return downloadService.postJson('/awac/answer/save', $scope.o.answersSave, function(result) {
          var answer, founded, i, it, j, key, listToRemove, repetition, repetitionToRemove, _i, _j, _k, _len, _len2, _len3, _ref, _ref2;
          if (result.success) {
            listToRemove = [];
            _ref = Object.keys($scope.mapRepetition);
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              key = _ref[_i];
              if (key !== '$$hashKey') {
                i = 0;
                while (i < $scope.mapRepetition[key].length) {
                  repetition = $scope.mapRepetition[key][i];
                  founded = false;
                  _ref2 = $scope.answerList;
                  for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                    answer = _ref2[_j];
                    if ((answer.value != null) && (!(answer.isAggregation != null) || answer.isAggregation !== true) && $scope.compareRepetitionMap(answer.mapRepetition, repetition)) {
                      founded = true;
                      break;
                    }
                  }
                  if (founded === false) {
                    it = listToRemove.length;
                    listToRemove[it] = {};
                    listToRemove[it].code = key;
                    listToRemove[it].iteration = {};
                    listToRemove[it].iteration[key] = $scope.mapRepetition[key][i][key];
                    j = angular.copy($scope.mapRepetition[key][i]);
                    delete j[key];
                    listToRemove[it].map = j;
                  }
                  i++;
                }
              }
            }
            for (_k = 0, _len3 = listToRemove.length; _k < _len3; _k++) {
              repetitionToRemove = listToRemove[_k];
              $scope.removeIteration(repetitionToRemove.code, repetitionToRemove.iteration, repetitionToRemove.map);
            }
            i = $scope.answerList.length - 1;
            while (i >= 0) {
              answer = $scope.answerList[i];
              if (answer.wasEdited === true) {
                answer.wasEdited = false;
              }
              if (answer.toRemove === true) {
                $scope.answerList.splice(i, 1);
              }
              i--;
            }
            $scope.saveFormProgress();
            messageFlash.displaySuccess(translationService.get('ANSWERS_SAVED'));
            modalService.close(modalService.LOADING);
            if (argToNav !== null) {
              $scope.$root.nav(argToNav.loc, argToNav.confirmed);
            }
            return;
          } else {
            messageFlash.displayError(translationService.get('ERROR_THROWN_ON_SAVE') + data.message);
            modalService.close(modalService.LOADING);
            return;
          }
        });
      }
    };
    $scope.getUnitCategories = function(code) {
      var question;
      if ($scope.getQuestion(code) === null) {
        return null;
      }
      question = $scope.getQuestion(code);
      if (question === null || question === void 0) {
        console.log("ERROR : this question was not found : " + code);
        return null;
      }
      if (question.unitCategoryId === null || question.unitCategoryId === void 0) {
        return null;
      }
      return $scope.o.unitCategories[question.unitCategoryId];
    };
    $scope.getCodeList = function(code) {
      var question;
      if ($scope.loading) {
        return null;
      }
      question = $scope.getQuestion(code);
      return $scope.o.codeLists[question.codeListName];
    };
    $scope.getRepetitionMapByQuestionSet = function(code, mapRepetition) {
      var listRepetition, repetition, _i, _len, _ref;
      listRepetition = [];
      if ($scope.mapRepetition[code] !== null && $scope.mapRepetition[code] !== void 0) {
        _ref = $scope.mapRepetition[code];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          repetition = _ref[_i];
          if (mapRepetition === null || mapRepetition === void 0 || $scope.compareRepetitionMap(repetition, mapRepetition)) {
            listRepetition[listRepetition.length] = repetition;
          }
        }
      }
      return listRepetition;
    };
    $scope.getQuestion = function(code, listQuestionSets) {
      var q, qSet, result, _i, _j, _len, _len2, _ref;
      if (listQuestionSets == null) {
        listQuestionSets = null;
      }
      if (listQuestionSets === null) {
        if ($scope.o === null || $scope.o === void 0 || $scope.o.questionSets === null) {
          return null;
        }
        listQuestionSets = $scope.o.questionSets;
      }
      if (listQuestionSets) {
        for (_i = 0, _len = listQuestionSets.length; _i < _len; _i++) {
          qSet = listQuestionSets[_i];
          if (qSet.questions) {
            _ref = qSet.questions;
            for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
              q = _ref[_j];
              if (q.code === code) {
                return q;
              }
            }
          }
          if (qSet.children) {
            result = $scope.getQuestion(code, qSet.children);
            if (result) {
              return result;
            }
          }
        }
      }
      return null;
    };
    $scope.getAnswerToCompare = function(code, useless, mapIndex) {
      var answer, indexKey, key, mapIteration, repetitionKey, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3;
      if ($scope.dataToCompare !== null) {
        if (mapIndex != null) {
          mapIteration = {};
          _ref = Object.keys(mapIndex);
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            indexKey = _ref[_i];
            if ($scope.mapRepetitionEquivalenceForComparison[indexKey] != null) {
              _ref2 = Object.keys($scope.mapRepetitionEquivalenceForComparison[indexKey]);
              for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
                key = _ref2[_j];
                if ($scope.mapRepetitionEquivalenceForComparison[indexKey][key] === mapIndex[indexKey]) {
                  repetitionKey = key;
                  mapIteration[indexKey] = parseFloat(repetitionKey);
                }
              }
            }
          }
          if (Object.keys(mapIteration).length === 0) {
            return null;
          }
        }
        _ref3 = $scope.dataToCompare.answersSave.listAnswers;
        for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
          answer = _ref3[_k];
          if (answer.questionKey === code) {
            if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
              return answer;
            }
          }
        }
      }
      return null;
    };
    /*
    $scope.getAnswerToCompare = (code, mapIteration,index) ->

        if $scope.dataToCompare != null
            i=0
            for answer in $scope.dataToCompare.answersSave.listAnswers
                if answer.questionKey == code
                    if i == index
                        return answer
                    i++
        return null


    #
    # get the first repetition
    #
    $scope.getFirstRepeteableElement = (questionSetCode) ->
        if $scope.mapQuestionSet[questionSetCode].repetitionAllowed == true
            return questionSetCode
        else
            return getFirstRepeteableElement($scope.mapQuestionSet[questionSetCode].parent.code)

    */
    $scope.getAnswerOrCreate = function(code, mapIteration, tabSet, tab, optional) {
      var answerLine, defaultUnitCode, question, result, value, wasEdited;
      if (tabSet == null) {
        tabSet = null;
      }
      if (tab == null) {
        tab = null;
      }
      if (optional == null) {
        optional = false;
      }
      if (code === null || code === void 0) {
        return null;
      }
      result = $scope.getAnswer(code, mapIteration);
      if (result) {
        if (result.toRemove != null) {
          result.toRemove = false;
        }
        if ((tabSet != null) && !(result.tabSet != null)) {
          result.tabSet = tabSet;
          result.tab = tab;
        }
        result.optional = optional;
        return result;
      } else {
        value = null;
        defaultUnitCode = null;
        wasEdited = false;
        if ($scope.getQuestion(code) !== null) {
          question = $scope.getQuestion(code);
          if (question.defaultValue !== null) {
            value = question.defaultValue;
            wasEdited = true;
          }
          if (question.unitCategoryId != null) {
            if (question.defaultUnit != null) {
              defaultUnitCode = question.defaultUnit.code;
            } else {
              defaultUnitCode = $scope.getUnitCategories(code).mainUnitCode;
            }
          }
        }
        answerLine = {
          'questionKey': code,
          'value': value,
          'unitCode': defaultUnitCode,
          'mapRepetition': mapIteration,
          'lastUpdateUser': $scope.$root.currentPerson,
          'wasEdited': wasEdited
        };
        if (tabSet != null) {
          answerLine.tabSet = tabSet;
          answerLine.tab = tab;
        }
        answerLine.optional = optional;
        $scope.createTabWatcher(answerLine);
        $scope.answerList[$scope.answerList.length] = answerLine;
        return answerLine;
      }
    };
    $scope.getAnswer = function(code, mapIteration) {
      var answer, _i, _len, _ref;
      _ref = $scope.answerList;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (answer.questionKey === code) {
          if ($scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
            return answer;
          }
        }
      }
      return null;
    };
    $scope.getListAnswer = function(code, mapIteration) {
      var answer, listAnswer, _i, _len, _ref;
      listAnswer = [];
      _ref = $scope.answerList;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (answer.questionKey === code && $scope.compareRepetitionMap(answer.mapRepetition, mapIteration)) {
          listAnswer[listAnswer.length] = answer;
        }
      }
      return listAnswer;
    };
    $scope.addIteration = function(code, mapRepetition) {
      var max, repetition, repetitionToAdd, _i, _len, _ref;
      max = 0;
      repetitionToAdd = {};
      if (mapRepetition != null) {
        repetitionToAdd = angular.copy(mapRepetition);
      }
      if ($scope.mapRepetition[code] === null || $scope.mapRepetition[code] === void 0) {
        repetitionToAdd[code] = max + 1;
        $scope.mapRepetition[code] = [];
        return $scope.mapRepetition[code][0] = repetitionToAdd;
      } else {
        _ref = $scope.mapRepetition[code];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          repetition = _ref[_i];
          if ($scope.compareRepetitionMap(repetition, mapRepetition) && repetition[code] > max) {
            max = repetition[code];
          }
        }
        repetitionToAdd[code] = max + 1;
        return $scope.mapRepetition[code][$scope.mapRepetition[code].length] = repetitionToAdd;
      }
    };
    $scope.removeIteration = function(questionSetCode, iterationToDelete, mapRepetition) {
      var iteration, key, len, question, _i, _len, _ref, _results;
      len = $scope.answerList.length;
      while (len--) {
        question = $scope.answerList[len];
        if ((question.mapRepetition != null) && $scope.compareRepetitionMap(question.mapRepetition, mapRepetition)) {
          if (question.mapRepetition[questionSetCode] && question.mapRepetition[questionSetCode] === iterationToDelete[questionSetCode]) {
            $scope.answerList[len].toRemove = true;
            if ($scope.answerList[len].value !== null) {
              $scope.answerList[len].value = null;
              $scope.answerList[len].wasEdited = true;
            }
          }
        }
      }
      _ref = Object.keys($scope.mapRepetition);
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        key = _ref[_i];
        _results.push((function() {
          var _results;
          if (key !== '$$hashKey') {
            len = $scope.mapRepetition[key].length;
            _results = [];
            while (len--) {
              iteration = $scope.mapRepetition[key][len];
              _results.push($scope.compareRepetitionMap(iteration, mapRepetition) && iteration[questionSetCode] && iteration[questionSetCode] === iterationToDelete[questionSetCode] ? $scope.mapRepetition[key].splice(len, 1) : void 0);
            }
            return _results;
          }
        })());
      }
      return _results;
    };
    $scope.compareRepetitionMap = function(mapContainer, mapContained) {
      var key, value, _i, _len, _ref;
      if (mapContained === null || mapContained === void 0 || mapContained.length === 0) {
        return true;
      }
      if (mapContainer === null || mapContainer === void 0 || mapContainer.length === 0) {
        return false;
      }
      _ref = Object.keys(mapContained);
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        key = _ref[_i];
        if (key !== '$$hashKey') {
          value = mapContained[key];
          if (mapContainer[key] === null || mapContainer[key] === void 0 || mapContainer[key] !== value) {
            return false;
          }
        }
      }
      return true;
    };
    $scope.addMapRepetition = function(questionSetCode, mapRepetitionSource, listQuestionSetRepetition) {
      var founded, key, mapRepetitionToAdd, questionCode, repetition, _i, _j, _k, _len, _len2, _len3, _ref, _ref2;
      mapRepetitionToAdd = {};
      _ref = Object.keys(mapRepetitionSource);
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        key = _ref[_i];
        if (key !== '$$hashKey') {
          for (_j = 0, _len2 = listQuestionSetRepetition.length; _j < _len2; _j++) {
            questionCode = listQuestionSetRepetition[_j];
            if (questionCode === key) {
              mapRepetitionToAdd[key] = mapRepetitionSource[key];
            }
          }
        }
      }
      if ($scope.mapRepetition[questionSetCode]) {
        founded = false;
        _ref2 = $scope.mapRepetition[questionSetCode];
        for (_k = 0, _len3 = _ref2.length; _k < _len3; _k++) {
          repetition = _ref2[_k];
          if ($scope.compareRepetitionMap(repetition, mapRepetitionToAdd)) {
            founded = true;
          }
        }
        if (founded === false) {
          return $scope.mapRepetition[questionSetCode][$scope.mapRepetition[questionSetCode].length] = angular.copy(mapRepetitionToAdd);
        }
      } else {
        $scope.mapRepetition[questionSetCode] = [];
        return $scope.mapRepetition[questionSetCode][0] = angular.copy(mapRepetitionToAdd);
      }
    };
    $scope.validNavigation = function() {
      var answer, result, _i, _len, _ref;
      result = {};
      result.modalForConfirm = modalService.CONFIRMATION_EXIT_FORM;
      _ref = $scope.answerList;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
          result.valid = false;
          break;
        }
      }
      return result;
    };
    $scope.mapRepetitionEquivalenceForComparison = {};
    $scope.$watch('$root.periodToCompare', function() {
      if ($scope.$parent !== null && ($scope.$root.periodToCompare != null) && $scope.$root.periodToCompare !== 'default') {
        return downloadService.getJson('/awac/answer/getByForm/' + $scope.formIdentifier + "/" + $scope.$root.periodToCompare + "/" + $scope.$root.scopeSelectedId, function(result) {
          var answer, index, repetitionKey, _i, _len, _ref, _results;
          if (result.success) {
            $scope.dataToCompare = result.data;
            _ref = result.data.answersSave.listAnswers;
            _results = [];
            for (_i = 0, _len = _ref.length; _i < _len; _i++) {
              answer = _ref[_i];
              _results.push((function() {
                var _i, _len, _ref, _results;
                if ((answer.mapRepetition != null) && Object.keys(answer.mapRepetition).length > 0) {
                  _ref = Object.keys(answer.mapRepetition);
                  _results = [];
                  for (_i = 0, _len = _ref.length; _i < _len; _i++) {
                    repetitionKey = _ref[_i];
                    index = 0;
                    if (!$scope.mapRepetitionEquivalenceForComparison[repetitionKey]) {
                      $scope.mapRepetitionEquivalenceForComparison[repetitionKey] = {};
                    } else {
                      index = Object.keys($scope.mapRepetitionEquivalenceForComparison[repetitionKey]).length;
                    }
                    _results.push(!($scope.mapRepetitionEquivalenceForComparison[repetitionKey][answer.mapRepetition[repetitionKey]] != null) ? $scope.mapRepetitionEquivalenceForComparison[repetitionKey][answer.mapRepetition[repetitionKey]] = index : void 0);
                  }
                  return _results;
                }
              })());
            }
            return _results;
          } else {
            return $scope.dataToCompare = null;
          }
        });
      } else {
        return $scope.dataToCompare = null;
      }
    });
    $scope.saveFormProgress = function() {
      var answer, answered, formProgress, formProgressDTO, founded, key, listTotal, percentage, tabSet, total, _i, _j, _k, _l, _len, _len2, _len3, _len4, _len5, _len6, _m, _n, _ref, _ref2, _ref3, _ref4, _ref5, _ref6;
      percentage = 0;
      total = 0;
      answered = 0;
      listTotal = [];
      _ref = $scope.answerList;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (!(answer.tabSet != null)) {
          if (answer.optional !== true && answer.isAggregation !== true) {
            if (answer.hasValidCondition === void 0 || answer.hasValidCondition === null || answer.hasValidCondition === true) {
              total++;
              listTotal.push(answer);
              if (answer.value !== null) {
                answered++;
              }
            }
          }
        }
      }
      _ref2 = Object.keys($scope.tabSet);
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        key = _ref2[_j];
        if (key !== '$$hashKey') {
          _ref3 = $scope.tabSet[key];
          for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
            tabSet = _ref3[_k];
            if (tabSet.master != null) {
              _ref4 = $scope.answerList;
              for (_l = 0, _len4 = _ref4.length; _l < _len4; _l++) {
                answer = _ref4[_l];
                if ((answer.tabSet != null) && parseFloat(answer.tabSet) === parseFloat(key) && parseFloat(answer.tab) === parseFloat(tabSet.master)) {
                  if (answer.optional !== true && answer.isAggregation !== true) {
                    if (answer.hasValidCondition === void 0 || answer.hasValidCondition === null || answer.hasValidCondition === true) {
                      total++;
                      listTotal.push(answer);
                      if (answer.value !== null) {
                        answered++;
                      }
                    }
                  }
                }
              }
            } else {
              _ref5 = $scope.answerList;
              for (_m = 0, _len5 = _ref5.length; _m < _len5; _m++) {
                answer = _ref5[_m];
                if ((answer.tabSet != null) && parseFloat(answer.tabSet) === parseFloat(key) && parseFloat(answer.tab) === 1) {
                  if (answer.optional !== true && answer.isAggregation !== true) {
                    if (answer.hasValidCondition === void 0 || answer.hasValidCondition === null || answer.hasValidCondition === true) {
                      total++;
                      listTotal.push(answer);
                      if (answer.value !== null) {
                        answered++;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      if (answered === 0) {
        percentage = 0;
      } else {
        percentage = answered / total * 100;
        percentage = Math.floor(percentage);
      }
      formProgressDTO = {};
      formProgressDTO.form = $scope.formIdentifier;
      formProgressDTO.period = $scope.$root.periodSelectedKey;
      formProgressDTO.scope = $scope.$root.scopeSelectedId;
      formProgressDTO.percentage = percentage;
      founded = false;
      if ($scope.$parent.formProgress != null) {
        _ref6 = $scope.$parent.formProgress;
        for (_n = 0, _len6 = _ref6.length; _n < _len6; _n++) {
          formProgress = _ref6[_n];
          if (formProgress.form === $scope.formIdentifier) {
            founded = true;
            formProgress.percentage = percentage;
          }
        }
      } else {
        $scope.$parent.formProgress = [];
      }
      if (founded === false) {
        $scope.$parent.formProgress[$scope.$parent.formProgress.length] = formProgressDTO;
      }
      return downloadService.postJson('/awac/answer/formProgress', formProgressDTO, function(result) {
        if (result.success) {
          return;
        }
      });
    };
    $scope.addTabSet = function(tabSet, tab, mapRepetition) {
      var i, ite, wasCreated, _ref;
      ite = null;
      wasCreated = false;
      if (!($scope.tabSet[tabSet] != null)) {
        $scope.tabSet[tabSet] = [];
        $scope.tabSet[tabSet][0] = {};
        $scope.tabSet[tabSet][0].mapRepetition = mapRepetition;
        ite = 0;
        wasCreated = true;
      } else {
        i = 0;
        while (i < $scope.tabSet[tabSet].length) {
          if ($scope.compareRepetitionMap(mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)) {
            ite = i;
            break;
          }
          i++;
        }
        if (ite === null) {
          ite = $scope.tabSet[tabSet].length;
          $scope.tabSet[tabSet][ite] = {};
          $scope.tabSet[tabSet][ite].mapRepetition = mapRepetition;
        }
      }
      if (!($scope.tabSet[tabSet][ite][tab] != null)) {
        $scope.tabSet[tabSet][ite][tab] = {};
        $scope.tabSet[tabSet][ite][tab].active = (_ref = tab === 1) != null ? _ref : {
          "true": false
        };
        wasCreated = true;
      }
      if (!($scope.tabSet[tabSet][ite][tab].listToCompute != null)) {
        $scope.tabSet[tabSet][ite][tab].listToCompute = [];
      }
      return ite;
    };
    $scope.getStatusClassForTab = function(tabSet, tab, mapRepetition) {
      var allWasSave, answer, atLeastOneQuestion, i, isFinish, ite, _i, _len, _ref;
      isFinish = true;
      i = 0;
      while (i < $scope.tabSet[tabSet].length) {
        if ($scope.compareRepetitionMap(mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)) {
          ite = i;
          break;
        }
        i++;
      }
      allWasSave = true;
      atLeastOneQuestion = false;
      _ref = $scope.tabSet[tabSet][ite][tab].listToCompute;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (!(answer.hasValidCondition != null) || answer.hasValidCondition === true && answer.optional !== true && answer.isAggregation !== true) {
          atLeastOneQuestion = true;
          if (answer.wasEdited !== void 0 && answer.wasEdited === true) {
            allWasSave = false;
          }
          if (answer.value === null) {
            isFinish = false;
          }
        }
      }
      if (isFinish === true && atLeastOneQuestion === false) {
        isFinish = false;
      }
      if (isFinish === true) {
        if (allWasSave === false) {
          return 'answer_temp';
        }
        return 'answer';
      } else {
        if (allWasSave === false) {
          return 'pending_temp';
        }
        return 'pending';
      }
    };
    $scope.createTabWatcher = function(answer) {
      var answerToTest, i, ite, j, tab, tabSet;
      if (answer.tabSet != null) {
        tabSet = answer.tabSet;
        tab = answer.tab;
        ite = $scope.addTabSet(tabSet, tab, answer.mapRepetition);
        j = $scope.tabSet[tabSet][ite][tab].listToCompute.length;
        j--;
        while (j >= 0) {
          answerToTest = $scope.tabSet[tabSet][ite][tab].listToCompute[j];
          if (answerToTest.questionKey === answer.questionKey && $scope.compareRepetitionMap(answer.mapRepetition, answerToTest.mapRepetition)) {
            $scope.tabSet[tabSet][ite][tab].listToCompute.splice(j, 1);
          }
          j--;
        }
        i = $scope.tabSet[tabSet][ite][tab].listToCompute.length;
        $scope.tabSet[tabSet][ite][tab].listToCompute[i] = answer;
        $scope.$watch('tabSet[' + tabSet + '][' + ite + '][' + tab + '].listToCompute[' + i + '].value', function() {
          return $scope.computeTab(tabSet, tab, answer.mapRepetition);
        });
        if ($scope.loading === false) {
          $scope.computeTab(tabSet, tab, answer.mapRepetition);
        }
        if (answer.value === null) {
          return $scope.tabSet[tabSet][ite][tab].isFinish = false;
        }
      }
    };
    $scope.computeTab = function(tabSet, tab, mapRepetition) {
      var answer, i, isFinish, ite, key, value, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3, _results;
      isFinish = false;
      i = 0;
      while (i < $scope.tabSet[tabSet].length) {
        if ($scope.compareRepetitionMap(mapRepetition, $scope.tabSet[tabSet][i].mapRepetition)) {
          ite = i;
          break;
        }
        i++;
      }
      _ref = $scope.tabSet[tabSet][ite][tab].listToCompute;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        answer = _ref[_i];
        if (!(answer.hasValidCondition != null) || answer.hasValidCondition === true && answer.optional !== true && answer.isAggregation !== true) {
          if (answer.value === null) {
            isFinish = false;
            break;
          } else {
            isFinish = true;
          }
        }
      }
      if (isFinish !== $scope.tabSet[tabSet][ite][tab].isFinish) {
        $scope.tabSet[tabSet][ite][tab].isFinish = isFinish;
        if (isFinish === true) {
          if (!($scope.tabSet[tabSet][ite].master != null) || $scope.tabSet[tabSet][ite].master > tab) {
            $scope.tabSet[tabSet][ite].master = tab;
          }
        } else if ($scope.tabSet[tabSet][ite].master === tab) {
          delete $scope.tabSet[tabSet][ite].master;
          _ref2 = Object.keys($scope.tabSet[tabSet][ite]);
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            key = _ref2[_j];
            if (key !== '$$hashKey' && key !== 'master' && key !== 'active' && key !== 'mapRepetition') {
              value = $scope.tabSet[tabSet][ite][key];
              if (value.isFinish === true) {
                if (!($scope.tabSet[tabSet][ite].master != null) || parseFloat($scope.tabSet[tabSet][ite].master) > parseFloat(key)) {
                  $scope.tabSet[tabSet][ite].master = parseFloat(key);
                }
              }
            }
          }
        }
        if ($scope.loading === true && ($scope.tabSet[tabSet][ite].master != null)) {
          _ref3 = Object.keys($scope.tabSet[tabSet][ite]);
          _results = [];
          for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
            key = _ref3[_k];
            _results.push(key !== '$$hashKey' && key !== 'master' && key !== 'active' && key !== 'mapRepetition' ? parseFloat(key) === parseFloat($scope.tabSet[tabSet][ite].master) ? $scope.tabSet[tabSet][ite][key].active = true : $scope.tabSet[tabSet][ite][key].active = false : void 0);
          }
          return _results;
        }
      }
    };
    $scope.getTab = function(tabSet, tab, mapRepetition) {
      var tabSetToTest, _i, _len, _ref, _ref2;
      if (mapRepetition == null) {
        mapRepetition = null;
      }
      if ($scope.loading === true || !($scope.tabSet[tabSet] != null)) {
        $scope.addTabSet(tabSet, tab, mapRepetition);
      }
      _ref = $scope.tabSet[tabSet];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        tabSetToTest = _ref[_i];
        if ($scope.compareRepetitionMap(mapRepetition, tabSetToTest.mapRepetition)) {
          if (!(tabSetToTest[tab] != null)) {
            tabSetToTest[tab] = {};
            tabSetToTest[tab].active = (_ref2 = parseFloat(tab) === 1) != null ? _ref2 : {
              "true": false
            };
            return tabSetToTest[tab];
          }
          return tabSetToTest[tab];
        }
      }
      return null;
    };
    $scope.tabIsMaster = function(tabSet, tab, mapRepetition) {
      var tabSetToTest, _i, _len, _ref;
      if (mapRepetition == null) {
        mapRepetition = null;
      }
      if ($scope.loading === true || !($scope.tabSet[tabSet] != null)) {
        return false;
      }
      _ref = $scope.tabSet[tabSet];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        tabSetToTest = _ref[_i];
        if ($scope.compareRepetitionMap(mapRepetition, tabSetToTest.mapRepetition)) {
          if (tabSetToTest.master === parseFloat(tab)) {
            return true;
          }
          return false;
        }
      }
      return false;
    };
    $scope.getQuestionSetLocker = function(code) {
      if ($scope.datalocker[code] != null) {
        return $scope.datalocker[code];
      }
      return null;
    };
    $scope.getQuestionSetValidator = function(code) {
      if ($scope.dataValidator[code] != null) {
        return $scope.dataValidator[code];
      }
      return null;
    };
    $scope.isQuestionLocked = function(code) {
      var key, question, questionSet, result, _i, _j, _len, _len2, _ref, _ref2, _results;
      _ref = Object.keys($scope.mapQuestionSet);
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        key = _ref[_i];
        if (key !== '$$hashKey') {
          questionSet = $scope.mapQuestionSet[key];
          if (questionSet.questionSetDTO.code === code) {
            result = $scope.foundBasicParent(questionSet);
            return (result.datalocker != null) && result.datalocker.identifier !== $scope.$root.currentPerson.identifier;
          }
          if (questionSet.questionSetDTO.questions != null) {
            _ref2 = questionSet.questionSetDTO.questions;
            for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
              question = _ref2[_j];
              if (question.code === code) {
                result = $scope.foundBasicParent(questionSet);
                return (result.datalocker != null) && result.datalocker.identifier !== $scope.$root.currentPerson.identifier;
              }
            }
          }
        }
      }
      return _results;
    };
    $scope.isQuestionValidate = function(code) {
      if ($scope.getValidatorByQuestionCode(code) != null) {
        return true;
      }
      return false;
    };
    $scope.getValidatorByQuestionCode = function(code) {
      var key, question, questionSet, _i, _j, _len, _len2, _ref, _ref2;
      _ref = Object.keys($scope.mapQuestionSet);
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        key = _ref[_i];
        if (key !== '$$hashKey') {
          questionSet = $scope.mapQuestionSet[key];
          if (questionSet.questionSetDTO.code === code) {
            return $scope.foundBasicParent(questionSet).dataValidator;
          }
          if (questionSet.questionSetDTO.questions != null) {
            _ref2 = questionSet.questionSetDTO.questions;
            for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
              question = _ref2[_j];
              if (question.code === code) {
                return $scope.foundBasicParent(questionSet).dataValidator;
              }
            }
          }
        }
      }
      return null;
    };
    $scope.lockQuestionSet = function(code, lock) {
      if (lock === true) {
        if (!($scope.datalocker[code] != null)) {
          $scope.datalocker[code] = $scope.$root.currentPerson;
        }
        return $scope.mapQuestionSet[code].questionSetDTO.datalocker = $scope.$root.currentPerson;
      } else {
        if ($scope.datalocker[code] != null) {
          delete $scope.datalocker[code];
        }
        return $scope.mapQuestionSet[code].questionSetDTO.datalocker = null;
      }
    };
    $scope.validateQuestionSet = function(code, lock) {
      if (lock === true) {
        if (!($scope.dataValidator[code] != null)) {
          $scope.dataValidator[code] = $scope.$root.currentPerson;
        }
        if ($scope.mapQuestionSet[code] != null) {
          return $scope.mapQuestionSet[code].questionSetDTO.dataValidator = $scope.$root.currentPerson;
        }
      } else {
        if ($scope.dataValidator[code] != null) {
          delete $scope.dataValidator[code];
        }
        return $scope.mapQuestionSet[code].questionSetDTO.dataValidator = null;
      }
    };
    $scope.foundBasicParent = function(questionSet) {
      if (!(questionSet.parent != null)) {
        return questionSet.questionSetDTO;
      }
      return $scope.foundBasicParent(questionSet.parent);
    };
    $scope.verifyQuestionSet = function(code, verification) {
      return $scope.dataVerification[code] = verification;
    };
    return $scope.getQuestionSetVerification = function(code) {
      if ($scope.dataVerification[code] != null) {
        return $scope.dataVerification[code];
      }
      return null;
    };
  };
  if (($route.current.params.form != null)) {
    return $scope.init($route.current.params.form);
  }
});angular.module('app.controllers').controller("VerificationArchiveCtrl", function($scope, displayLittleFormMenu, modalService, downloadService, messageFlash, $window, ngTableParams, $filter) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  modalService.show(modalService.LOADING);
  downloadService.getJson("/awac/verification/archivedRequests", function(result) {
    if (!result.success) {
      return modalService.close(modalService.LOADING);
    } else {
      $scope.requests = result.data.list;
      $scope.displayTable();
      return modalService.close(modalService.LOADING);
    }
  });
  $scope.displayTable = function() {
    if (!$scope.requests) {
      $scope.requests = [];
    }
    if ($scope.tableParams != null) {
      return $scope.tableParams.reload();
    } else {
      return $scope.tableParams = new ngTableParams({
        page: 1,
        count: 100,
        sorting: {
          code: "asc"
        }
      }, {
        total: 0,
        getData: function($defer, params) {
          var orderedData;
          orderedData = $filter("orderBy")($scope.requests, params.orderBy());
          $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
          return params.total($scope.requests.length);
        }
      });
    }
  };
  return $scope.downloadReport = function(request) {
    var url;
    url = '/awac/file/download/' + request.verificationSuccessFileId;
    return $window.open(url);
  };
});angular.module('app.controllers').controller("AdminTranslationHelpsCtrl", function($scope, $compile, $timeout, downloadService, displayLittleFormMenu, modalService, messageFlash, translationService, codeLabelHelper) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.files = [];
  if ($scope.files.length > 0) {
    $scope.selectedFile = $scope.files[0];
  }
  $scope.select = function(file) {
    return $scope.selectedFile = file;
  };
  $scope.isModified = function(file) {
    return file.content !== file.original;
  };
  $scope.isSelected = function(file) {
    return $scope.selectedFile === file;
  };
  $scope.load = function() {
    return downloadService.getJson("/awac/admin/translations/wysiwyg/all", function(result) {
      var f, _i, _len, _ref, _results;
      if (result.success) {
        $scope.files = result.data.files;
        _ref = $scope.files;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          f = _ref[_i];
          _results.push(f.original = f.content);
        }
        return _results;
      }
    });
  };
  $scope.categories = [
    {
      key: 'enterprise',
      label: 'Entreprise'
    }, {
      key: 'municipality',
      label: 'Communes'
    }, {
      key: 'household',
      label: 'Ménages'
    }, {
      key: 'verification',
      label: 'Vérification'
    }, {
      key: 'event',
      label: 'Evènements'
    }, {
      key: 'littleemitter',
      label: 'Petits émetteurs '
    }
  ];
  $scope.save = function(f) {
    return downloadService.postJson("/awac/admin/translations/wysiwyg/update", _.omit(f, 'original'), function(result) {
      if (result.success) {
        f.original = f.content;
        return translationService.reinitialize();
      }
    });
  };
  $scope.editorOptions = {
    language: 'fr',
    skin: 'moono',
    uiColor: '#CFCDC0',
    height: '500px',
    toolbar: 'Wysisyg'
  };
  $(window).keydown(function(event) {
    if ((!(String.fromCharCode(event.which).toLowerCase() === 's' && event.ctrlKey) && !(event.which === 19)) || !$scope.selectedFile) {
      return true;
    }
    $scope.save($scope.selectedFile);
    event.preventDefault();
    return false;
  });
  return $scope.load();
});angular.module('app.controllers').controller("ActionsCtrl", function($scope, $window, displayFormMenu, modalService, downloadService, $filter, messageFlash, translationService, $filter) {
  $scope.displayFormMenu = displayFormMenu;
  $scope.actions = [];
  $scope.actionAdvices = [];
  $scope.typeOptions = [];
  $scope.statusOptions = [];
  $scope.gwpUnits = [];
  $scope.waitingAdvices = true;
  $scope.loadActions = function() {
    downloadService.getJson("awac/actions/load", function(result) {
      var codeLists;
      if (result.success) {
        codeLists = result.data.codeLists;
        $scope.typeOptions = _.findWhere(codeLists, {
          code: 'REDUCING_ACTION_TYPE'
        }).codeLabels;
        $scope.statusOptions = _.findWhere(codeLists, {
          code: 'REDUCING_ACTION_STATUS'
        }).codeLabels;
        $scope.gwpUnits = result.data.gwpUnits;
        $scope.actions = result.data.reducingActions;
      }
      return;
    });
    return;
  };
  $scope.loadAdvices = function() {
    $scope.waitingAdvices = true;
    downloadService.getJson("awac/advices/load/" + $scope.$root.periodSelectedKey, function(result) {
      var altGroupLabel, e, index, _ref;
      if (result.success) {
        $scope.actionAdvices = result.data.reducingActionAdvices;
        _ref = $scope.actionAdvices;
        for (index in _ref) {
          e = _ref[index];
          if ((e.typeKey === "2") && (!!e.alternativeGroupKey)) {
            altGroupLabel = translationService.get(e.alternativeGroupKey);
            e.title = $filter("translateTextWithVars")("BETTER_MEASURE_ADVICE_TITLE", [altGroupLabel]);
            e.physicalMeasure = translationService.get("BETTER_MEASURE_ADVICE_DESC");
          }
        }
      }
      $scope.waitingAdvices = false;
      return;
    });
    return;
  };
  $scope.createAction = function() {
    var params;
    params = {
      typeOptions: $scope.typeOptions,
      statusOptions: $scope.statusOptions,
      gwpUnits: $scope.gwpUnits,
      cb: $scope.loadActions
    };
    return modalService.show(modalService.CREATE_REDUCTION_ACTION, params);
  };
  $scope.updateAction = function(action) {
    var params;
    params = {
      typeOptions: $scope.typeOptions,
      statusOptions: $scope.statusOptions,
      gwpUnits: $scope.gwpUnits,
      action: action
    };
    return modalService.show(modalService.CREATE_REDUCTION_ACTION, params);
  };
  $scope.markActionAsDone = function(action) {
    var data;
    $scope.isLoading = true;
    data = angular.copy(action);
    data.statusKey = "2";
    data.completionDate = new Date();
    return downloadService.postJson('/awac/actions/save', data, function(result) {
      $scope.isLoading = false;
      if (result.success) {
        angular.extend(action, result.data);
        return messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
      }
    });
  };
  $scope.deleteAction = function(action) {
    $scope.isLoading = true;
    return downloadService.postJson("awac/actions/delete", action, function(result) {
      var e, index, _ref, _results;
      $scope.isLoading = false;
      if (result.success) {
        messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
        _ref = $scope.actions;
        _results = [];
        for (index in _ref) {
          e = _ref[index];
          if (e.id === action.id) {
            $scope.actions.splice(index, 1);
            break;
          }
        }
        return _results;
      }
    });
  };
  $scope.confirmDelete = function(action) {
    var params;
    params = {
      titleKey: "REDUCING_ACTION_DELETE_CONFIRMATION_TITLE",
      messageKey: "REDUCING_ACTION_DELETE_CONFIRMATION_MESSAGE",
      onConfirm: function() {
        return $scope.deleteAction(action);
      }
    };
    return modalService.show(modalService.CONFIRM_DIALOG, params);
  };
  $scope.exportActionsToXls = function() {
    return $window.open('/awac/actions/exportToXls', translationService.get('RESULT_DOWNLOAD_START', null));
  };
  $scope.getScopeName = function(scopeTypeKey, scopeId) {
    if (scopeTypeKey === "1") {
      return $scope.$root.organizationName;
    } else {
      return $scope.$root.mySites.filter(function(e) {
        return ("" + e.id) === ("" + scopeId);
      })[0].name;
    }
  };
  $scope.getTypeLabel = function(typeKey) {
    return _.findWhere($scope.typeOptions, {
      key: typeKey
    }).label;
  };
  $scope.getStatusLabel = function(statusKey) {
    return _.findWhere($scope.statusOptions, {
      key: statusKey
    }).label;
  };
  $scope.getGwpUnitSymbol = function(gwpUnitCodeKey) {
    if (gwpUnitCodeKey !== null) {
      return _.findWhere($scope.gwpUnits, {
        code: gwpUnitCodeKey
      }).name;
    }
  };
  $scope.getDefaultDueDate = function() {
    var defaultDueDate;
    defaultDueDate = new Date();
    defaultDueDate.setFullYear(defaultDueDate.getFullYear() + 1);
    return defaultDueDate;
  };
  $scope.createActionFromAdvice = function(actionAdvice) {
    var data;
    if (!!_.findWhere($scope.actions, {
      scopeTypeKey: '1',
      title: actionAdvice.title
    })) {
      messageFlash.displayError("REDUCING_ACTION_ALREADY_EXISTING_ERROR");
      return;
    }
    data = {
      title: actionAdvice.title,
      typeKey: actionAdvice.typeKey,
      statusKey: "1",
      scopeTypeKey: "1",
      ghgBenefit: actionAdvice.computedGhgBenefit,
      ghgBenefitUnitKey: actionAdvice.computedGhgBenefitUnitKey,
      ghgBenefitMax: actionAdvice.computedGhgBenefitMax,
      ghgBenefitMaxUnitKey: actionAdvice.computedGhgBenefitMaxUnitKey,
      physicalMeasure: actionAdvice.physicalMeasure,
      webSite: actionAdvice.webSite,
      responsiblePerson: actionAdvice.responsiblePerson,
      files: [],
      dueDate: $scope.getDefaultDueDate()
    };
    downloadService.postJson('/awac/actions/save', data, function(result) {
      if (result.success) {
        messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
        if ($scope.editMode) {
          angular.extend($scope.getParams().action, result.data);
        }
        return $scope.loadActions();
      } else {
        return $scope.isLoading = false;
      }
    });
    return false;
  };
  return $scope.$watch('$root.mySites', function(n, o) {
    if (!!n) {
      $scope.loadActions();
      return $scope.loadAdvices();
    }
  });
});angular.module('app.controllers').controller("LoginCtrl", function($scope, downloadService, $location, $filter, messageFlash, $compile, $timeout, modalService, translationService) {
  $scope.loading = false;
  $scope.tabActive = [];
  $scope.$watch('tabActive[1]', function() {
    if ($scope.tabActive[1] !== true) {
      $scope.forgotEmailSuccessMessage = null;
      return $scope.forgotPasswordInfo.field = "";
    }
  });
  $scope.enterEvent = function() {
    if ($scope.tabActive[0] === true) {
      return $scope.send({
        anonymous: false
      });
    } else if ($scope.tabActive[1] === true) {
      return $scope.sendForgotPassword();
    }
  };
  $scope.loginInfo = {
    fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE",
    id: 'loginForm',
    inputName: 'identifier',
    fieldType: "text",
    placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER",
    validationRegex: "^\\S{5,20}$",
    validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH",
    field: "",
    isValid: false,
    focus: function() {
      return $scope.tabActive[0];
    }
  };
  $scope.passwordInfo = {
    fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE",
    inputName: 'password',
    fieldType: "password",
    validationRegex: "^\\S{5,20}$",
    validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
    field: "",
    isValid: false
  };
  $scope.forgotPasswordInfo = {
    fieldTitle: "IDENTIFIENT_OR_EMAIL",
    inputName: 'password',
    fieldType: "text",
    validationRegex: "^\\S{5,20}$",
    validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH",
    field: "",
    isValid: false,
    focus: function() {
      return $scope.tabActive[1];
    }
  };
  $scope.connectionFieldValid = function() {
    if ($scope.loginInfo.isValid && $scope.passwordInfo.isValid) {
      return true;
    }
    return false;
  };
  $scope.forgotPasswordFieldValid = function() {
    if ($scope.forgotPasswordInfo.isValid && $scope.forgotPasswordInfo.isValid) {
      return true;
    }
    return false;
  };
  $scope.send = function(options) {
    var dto;
    if (options.anonymous || $scope.connectionFieldValid()) {
      $scope.isLoading = true;
      if (options.anonymous) {
        dto = {
          login: null,
          password: null,
          interfaceName: $scope.$root.instanceName
        };
      } else {
        dto = {
          login: $scope.loginInfo.field,
          password: $scope.passwordInfo.field,
          interfaceName: $scope.$root.instanceName
        };
      }
      $timeout(function() {
        return downloadService.postJson('/awac/login', dto, function(result) {
          var params;
          if (result.success) {
            $scope.$root.loginSuccess(result.data);
            messageFlash.displaySuccess(translationService.get('CONNECTION_MESSAGE_SUCCESS'));
            return $scope.isLoading = false;
          } else {
            $scope.isLoading = false;
            if (result.data.__type === 'eu.factorx.awac.dto.myrmex.get.MustChangePasswordExceptionsDTO') {
              params = {
                login: $scope.loginInfo.field,
                password: $scope.passwordInfo.field
              };
              return modalService.show(modalService.CONNECTION_PASSWORD_CHANGE, params);
            }
          }
        });
      }, 3000);
    }
    return false;
  };
  $scope.forgotEmailSuccessMessage = null;
  $scope.sendForgotPassword = function() {
    $scope.isLoading = true;
    downloadService.postJson('/awac/forgotPassword', {
      identifier: $scope.forgotPasswordInfo.field,
      interfaceName: $scope.$root.instanceName
    }, function(result) {
      if (result.success) {
        $scope.forgotEmailSuccessMessage = translationService.get('LOGIN_FORGOT_PASSWORD_SUCCESS');
        $scope.isLoading = false;
        return;
      } else {
        $scope.isLoading = false;
        return;
      }
    });
    return false;
  };
  $scope.injectRegistrationDirective = function() {
    var directive, directiveName;
    console.log("$scope.injectRegistrationDirective");
    if ($scope.$root != null) {
      console.log($scope.$root.instanceName);
      if ($scope.$root.instanceName === 'enterprise') {
        directiveName = "mm-awac-registration-enterprise";
      } else if ($scope.$root.instanceName === 'municipality') {
        directiveName = "mm-awac-registration-municipality";
      } else if ($scope.$root.instanceName === 'household') {
        directiveName = "mm-awac-registration-household";
      } else if ($scope.$root.instanceName === 'littleemitter') {
        directiveName = "mm-awac-registration-littleemitter";
      } else if ($scope.$root.instanceName === 'event') {
        directiveName = "mm-awac-registration-event";
      }
      directive = $compile("<" + directiveName + "></" + directiveName + ">")($scope);
      return $('.inject-registration-form').append(directive);
    }
  };
  return $timeout(function() {
    return $scope.injectRegistrationDirective();
  }, 0);
});angular.module('app.controllers').controller("UserManagerCtrl", function($scope, translationService, modalService, downloadService, messageFlash) {
  $scope.title = translationService.get('USER_MANAGER_TITLE');
  $scope.statusesForVerification = [
    {
      id: 1,
      label: 'SIMPLE_USER'
    }, {
      id: 2,
      label: 'USER_MANAGER_MAIN_VERIFIER'
    }, {
      id: 3,
      label: 'USER_MANAGER_ADMINISTRATOR'
    }
  ];
  $scope.isLoading = {};
  $scope.isLoading['admin'] = {};
  $scope.isLoading['isActive'] = {};
  modalService.show(modalService.LOADING);
  downloadService.getJson('awac/organization/getMyOrganization', function(result) {
    var user, _i, _len, _ref;
    if (!result.success) {
      messageFlash.displayError(translationService.get('UNABLE_LOAD_DATA'));
      return modalService.close(modalService.LOADING);
    } else {
      modalService.close(modalService.LOADING);
      $scope.organization = result.data;
      if ($scope.$root.instanceName === 'verification') {
        _ref = $scope.organization.users;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          user = _ref[_i];
          if (user.isAdmin === true) {
            user.tempStatus = 3;
          } else if (user.isMainVerifier === true) {
            user.tempStatus = 2;
          } else {
            user.tempStatus = 1;
          }
        }
      }
      $scope.getUserList = function() {
        return $scope.organization.users;
      };
      $scope.inviteUser = function() {
        return modalService.show(modalService.INVITE_USER);
      };
      $scope.getMyself = function() {
        return $scope.$root.currentPerson;
      };
      $scope.activeUser = function(user) {
        var data;
        if ($scope.getMyself().isAdmin === true && $scope.getMyself().email !== user.email) {
          data = {};
          data.identifier = user.identifier;
          data.isActive = !user.isActive;
          $scope.isLoading['isActive'][user.email] = true;
          return downloadService.postJson("/awac/user/activeAccount", data, function(result) {
            if (result.success) {
              return $scope.isLoading['isActive'][user.email] = false;
            } else {
              return $scope.isLoading['isActive'][user.email] = false;
            }
          });
        }
      };
      $scope.isAdminUser = function(user) {
        var data;
        if ($scope.getMyself().isAdmin === true && $scope.getMyself().email !== user.email && user.isActive === true) {
          data = {};
          data.identifier = user.identifier;
          data.isAdmin = !user.isAdmin;
          $scope.isLoading['admin'][user.email] = true;
          return downloadService.postJson("/awac/user/isAdminAccount", data, function(result) {
            if (result.success) {
              return $scope.isLoading['admin'][user.email] = false;
            } else {
              return $scope.isLoading['admin'][user.email] = false;
            }
          });
        }
      };
      return $scope.changeStatusForVerification = function(user) {
        var data;
        data = {};
        data.identifier = user.identifier;
        if (user.tempStatus === 1) {
          data.newStatus = 'user';
        } else if (user.tempStatus === 2) {
          data.newStatus = 'main_verifier';
        } else if (user.tempStatus === 3) {
          data.newStatus = 'admin';
        }
        $scope.isLoading['admin'][user.email] = true;
        console.log('!!!!');
        console.log(data);
        return downloadService.postJson("/awac/user/setStatus", data, function(result) {
          return $scope.isLoading['admin'][user.email] = false;
        });
      };
    }
  });
  return $scope.toForm = function() {
    return $scope.$root.navToLastFormUsed();
  };
});angular.module('app.controllers').controller("VerificationVerificationCtrl", function($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.selectedRequest = null;
  modalService.show(modalService.LOADING);
  downloadService.getJson('/awac/verification/myVerificationRequests', function(result) {
    if (!result.success) {
      return modalService.close(modalService.LOADING);
    } else {
      modalService.close(modalService.LOADING);
      $scope.requests = result.data.list;
      return $scope.displayTable();
    }
  });
  $scope.displayTable = function() {
    if (!$scope.requests) {
      $scope.requests = [];
    }
    if ($scope.tableParams != null) {
      return $scope.tableParams.reload();
    } else {
      return $scope.tableParams = new ngTableParams({
        page: 1,
        count: 100
      }, {
        counts: [],
        total: 1,
        getData: function($defer, params) {
          var orderedData;
          orderedData = $filter("orderBy")($scope.requests, params.orderBy());
          $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
          params.total(orderedData.length);
          return;
        }
      });
    }
  };
  $scope.form = null;
  $scope.requestSelected = null;
  $scope.selectRequest = function(request) {
    var directiveName;
    $('.injectForm:first').empty();
    $('.injectFormMenu:first').empty();
    if ($scope.directiveMenu != null) {
      $scope.directiveMenu.remove();
    }
    if ($scope.directive != null) {
      $scope.directive.remove();
    }
    if (request != null) {
      $scope.requestSelected = request;
      $scope.$root.mySites = [request.scope];
    }
    $scope.selectedRequest = $scope.requestSelected;
    $scope.$root.periodSelectedKey = $scope.requestSelected.period.key;
    $scope.$root.scopeSelectedId = $scope.requestSelected.scope.id;
    if (!$scope.form) {
      if ($scope.requestSelected.organizationCustomer.interfaceName === 'enterprise') {
        $scope.form = 'TAB2';
      }
      if ($scope.requestSelected.organizationCustomer.interfaceName === 'municipality') {
        $scope.form = 'TAB_C1';
      }
    }
    if ($scope.form === '/results') {
      directiveName = "<div ng-include=\"'$/angular/views/results.html'\" ng-controller=\"ResultsCtrl\"></div>";
    } else {
      directiveName = "<div ng-include=\"'$/angular/views/" + $scope.requestSelected.organizationCustomer.interfaceName + "/" + $scope.form + ".html'\" ng-init=\"init('" + $scope.form + "')\" ng-controller=\"FormCtrl\"></div>";
    }
    $scope.directive = $compile(directiveName)($scope);
    $('.injectForm:first').append($scope.directive);
    $scope.directiveMenu = $compile("<mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu form=\"" + $scope.form + "\"></mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu>")($scope);
    $scope.displayMenu = true;
    $('.injectFormMenu:first').append($scope.directiveMenu);
    return $scope.testClosingValidation();
  };
  $scope.isMenuCurrentlySelected = function(target) {
    if ('/form/' + $scope.form === target || $scope.form === target) {
      return true;
    }
    return false;
  };
  $scope.navTo = function(target) {
    var form;
    form = target.replace('/form/', '');
    $scope.form = form;
    return $scope.selectRequest();
  };
  $scope.consultEvent = function() {
    var data;
    data = {
      organizationCustomer: $scope.requestSelected.organizationCustomer
    };
    return modalService.show(modalService.CONSULT_EVENT, data);
  };
  $scope.$on('TEST_CLOSING_VALIDATION', function() {
    return $scope.testClosingValidation();
  });
  $scope.testClosingValidation = function() {
    return downloadService.getJson('/awac/answer/testClosingValidation/' + $scope.requestSelected.period.key + "/" + $scope.requestSelected.scope.id, function(result) {
      if (!result.success) {
        return modalService.close(modalService.LOADING);
      } else {
        return $scope.verificationFinalization = result.data;
      }
    });
  };
  $scope.removeRequest = function() {
    var i, request, _i, _len, _ref;
    i = 0;
    _ref = $scope.requests;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      request = _ref[_i];
      if (request.id === $scope.requestSelected.id) {
        $scope.requests.splice(i, 1);
      }
      i++;
    }
    $scope.displayTable();
    $('.injectForm:first').empty();
    $('.injectFormMenu:first').empty();
    return $scope.requestSelected = null;
  };
  return $scope.finalizeVerification = function() {
    var data;
    console.log($scope.verificationFinalization);
    data = {
      removeRequest: $scope.removeRequest,
      verificationSuccess: $scope.verificationFinalization.success,
      request: $scope.requestSelected
    };
    return modalService.show(modalService.VERIFICATION_FINALIZATION, data);
  };
});angular.module('app.controllers').controller("ResultsCtrl", function($scope, $window, $filter, downloadService, modalService, messageFlash, translationService) {
  $scope.displayFormMenu = true;
  $scope.verificationRequests = [];
  $scope.$root.$watch('mySites', function(nv) {
    var s, _i, _len, _ref, _results;
    console.log('watch $root.mySites');
    $scope.mySites = angular.copy($scope.$root.mySites);
    _ref = $scope.mySites;
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      s = _ref[_i];
      _results.push("" + s.id === "" + $scope.$root.scopeSelectedId ? s.selected = true : void 0);
    }
    return _results;
  }, true);
  $scope.getVerificationRequestStatus = function() {
    if ($scope.$root.verificationRequest != null) {
      return $scope.$root.verificationRequest.status;
    }
  };
  $scope.getVerificationRequestOrganizationVerifierName = function() {
    var _ref, _ref2;
    return (_ref = $scope.$root.verificationRequest) != null ? (_ref2 = _ref.organizationVerifier) != null ? _ref2.name : void 0 : void 0;
  };
  $scope.$watch('$root.periodToCompare', function() {
    return $scope.reload();
  });
  $scope.$watch('mySites', function() {
    console.log('watch mySites');
    return $scope.reload();
  }, true);
  downloadService.getJson('/awac/verification/verificationRequests/' + $scope.$root.periodSelectedKey, function(result) {
    if (!result.success) {
      return messageFlash.displayError(result.data.message);
    } else {
      console.log('verif request : ');
      console.log(result.data.list);
      return $scope.verificationRequests = result.data.list;
    }
  });
  $scope.downloadVerificationReport = function(verificationRequest) {
    var url;
    url = '/awac/file/download/' + verificationRequest.verificationSuccessFileId;
    return $window.open(url);
  };
  $scope.exportPdf = function() {
    var dto, sites;
    sites = $scope.mySites.filter(function(e) {
      return e.selected;
    });
    dto = {
      __type: 'eu.factorx.awac.dto.awac.post.GetReportParametersDTO',
      periodKey: $scope.$root.periodSelectedKey,
      scopesIds: sites.map(function(s) {
        return s.id;
      })
    };
    if ($scope.$root.periodToCompare !== 'default') {
      dto.comparedPeriodKey = $scope.$root.periodToCompare;
    }
    $scope.pdfLoading = true;
    return downloadService.postJson('/awac/result/getReportAsPdf', dto, function(result) {
      $scope.pdfLoading = false;
      return messageFlash.displayInfo('PREPARING_DOCUMENT');
    });
  };
  $scope.exportXls = function() {
    var dto, sites;
    sites = $scope.mySites.filter(function(e) {
      return e.selected;
    });
    dto = {
      __type: 'eu.factorx.awac.dto.awac.post.GetReportParametersDTO',
      periodKey: $scope.$root.periodSelectedKey,
      scopesIds: sites.map(function(s) {
        return s.id;
      })
    };
    if ($scope.$root.periodToCompare !== 'default') {
      dto.comparedPeriodKey = $scope.$root.periodToCompare;
    }
    $scope.xlsLoading = true;
    return downloadService.postJson('/awac/result/getReportAsXls', dto, function(result) {
      $scope.xlsLoading = false;
      return messageFlash.displayInfo('PREPARING_DOCUMENT');
    });
  };
  $scope.dataURItoBlob = function(dataURI) {
    var ab, bb, byteString, i, ia, mimeString;
    byteString = atob(dataURI.split(",")[1]);
    mimeString = dataURI.split(",")[0].split(":")[1].split(";")[0];
    ab = new ArrayBuffer(byteString.length);
    ia = new Uint8Array(ab);
    i = 0;
    while (i < byteString.length) {
      ia[i] = byteString.charCodeAt(i);
      i++;
    }
    bb = new BlobBuilder();
    bb.append(ab);
    return bb.getBlob(mimeString);
  };
  $scope.reloading = false;
  $scope.reload = function() {
    var dto, sites;
    if ($scope.reloading) {
      return;
    }
    $scope.reloading = true;
    sites = $scope.mySites.filter(function(e) {
      return e.selected;
    });
    if (sites.length > 0) {
      $scope.o = void 0;
      $scope.leftTotalEmissions = void 0;
      $scope.leftTotalScope1 = void 0;
      $scope.leftTotalScope2 = void 0;
      $scope.leftTotalScope3 = void 0;
      $scope.rightTotalEmissions = void 0;
      $scope.rightTotalScope1 = void 0;
      $scope.rightTotalScope2 = void 0;
      $scope.rightTotalScope3 = void 0;
      modalService.show(modalService.LOADING);
      dto = {
        __type: 'eu.factorx.awac.dto.awac.post.GetReportParametersDTO',
        periodKey: $scope.$root.periodSelectedKey,
        scopesIds: sites.map(function(s) {
          return s.id;
        })
      };
      if ($scope.$root.periodToCompare !== 'default') {
        dto.comparedPeriodKey = $scope.$root.periodToCompare;
      }
      return downloadService.postJson('/awac/result/getReport', dto, function(result) {
        var line, _i, _len, _ref;
        modalService.close(modalService.LOADING);
        if (result.success) {
          $scope.o = result.data;
          if ($scope.$root.instanceName === 'municipality') {
            $scope.o.reportDTOs.R_1 = $scope.o.reportDTOs.RCo_1;
            $scope.o.reportDTOs.R_2 = $scope.o.reportDTOs.RCo_2;
            $scope.o.reportDTOs.R_3 = $scope.o.reportDTOs.RCo_3;
            $scope.o.reportDTOs.R_4 = $scope.o.reportDTOs.RCo_4;
            $scope.o.reportDTOs.R_5 = $scope.o.reportDTOs.RCo_5;
            $scope.o.leftSvgDonuts.R_1 = $scope.o.leftSvgDonuts.RCo_1;
            $scope.o.leftSvgDonuts.R_2 = $scope.o.leftSvgDonuts.RCo_2;
            $scope.o.leftSvgDonuts.R_3 = $scope.o.leftSvgDonuts.RCo_3;
            $scope.o.leftSvgDonuts.R_4 = $scope.o.leftSvgDonuts.RCo_4;
            $scope.o.leftSvgDonuts.R_5 = $scope.o.leftSvgDonuts.RCo_5;
            $scope.o.rightSvgDonuts.R_1 = $scope.o.rightSvgDonuts.RCo_1;
            $scope.o.rightSvgDonuts.R_2 = $scope.o.rightSvgDonuts.RCo_2;
            $scope.o.rightSvgDonuts.R_3 = $scope.o.rightSvgDonuts.RCo_3;
            $scope.o.rightSvgDonuts.R_4 = $scope.o.rightSvgDonuts.RCo_4;
            $scope.o.rightSvgDonuts.R_5 = $scope.o.rightSvgDonuts.RCo_5;
            $scope.o.svgWebs.R_1 = $scope.o.svgWebs.RCo_1;
            $scope.o.svgWebs.R_2 = $scope.o.svgWebs.RCo_2;
            $scope.o.svgWebs.R_3 = $scope.o.svgWebs.RCo_3;
            $scope.o.svgWebs.R_4 = $scope.o.svgWebs.RCo_4;
            $scope.o.svgWebs.R_5 = $scope.o.svgWebs.RCo_5;
            $scope.o.svgHistograms.R_1 = $scope.o.svgHistograms.RCo_1;
            $scope.o.svgHistograms.R_2 = $scope.o.svgHistograms.RCo_2;
            $scope.o.svgHistograms.R_3 = $scope.o.svgHistograms.RCo_3;
            $scope.o.svgHistograms.R_4 = $scope.o.svgHistograms.RCo_4;
            $scope.o.svgHistograms.R_5 = $scope.o.svgHistograms.RCo_5;
            $scope.o.svgHistogramsCEF.R_1 = $scope.o.svgHistogramsCEF.RCo_1;
            $scope.o.svgHistogramsCEF.R_2 = $scope.o.svgHistogramsCEF.RCo_2;
            $scope.o.svgHistogramsCEF.R_3 = $scope.o.svgHistogramsCEF.RCo_3;
            $scope.o.svgHistogramsCEF.R_4 = $scope.o.svgHistogramsCEF.RCo_4;
            $scope.o.svgHistogramsCEF.R_5 = $scope.o.svgHistogramsCEF.RCo_5;
            $scope.o.reportCEFDTOs.R_1 = $scope.o.reportCEFDTOs.RCo_1;
            $scope.o.reportCEFDTOs.R_2 = $scope.o.reportCEFDTOs.RCo_2;
            $scope.o.reportCEFDTOs.R_3 = $scope.o.reportCEFDTOs.RCo_3;
            $scope.o.reportCEFDTOs.R_4 = $scope.o.reportCEFDTOs.RCo_4;
            $scope.o.reportCEFDTOs.R_5 = $scope.o.reportCEFDTOs.RCo_5;
          }
          if ($scope.$root.instanceName === 'event') {
            $scope.o.reportDTOs.R_1 = $scope.o.reportDTOs.REv_1;
            $scope.o.reportDTOs.R_2 = $scope.o.reportDTOs.REv_2;
            $scope.o.reportDTOs.R_3 = $scope.o.reportDTOs.REv_3;
            $scope.o.reportDTOs.R_4 = $scope.o.reportDTOs.REv_4;
            $scope.o.reportDTOs.R_5 = $scope.o.reportDTOs.REv_5;
            $scope.o.leftSvgDonuts.R_1 = $scope.o.leftSvgDonuts.REv_1;
            $scope.o.leftSvgDonuts.R_2 = $scope.o.leftSvgDonuts.REv_2;
            $scope.o.leftSvgDonuts.R_3 = $scope.o.leftSvgDonuts.REv_3;
            $scope.o.leftSvgDonuts.R_4 = $scope.o.leftSvgDonuts.REv_4;
            $scope.o.leftSvgDonuts.R_5 = $scope.o.leftSvgDonuts.REv_5;
            $scope.o.rightSvgDonuts.R_1 = $scope.o.rightSvgDonuts.REv_1;
            $scope.o.rightSvgDonuts.R_2 = $scope.o.rightSvgDonuts.REv_2;
            $scope.o.rightSvgDonuts.R_3 = $scope.o.rightSvgDonuts.REv_3;
            $scope.o.rightSvgDonuts.R_4 = $scope.o.rightSvgDonuts.REv_4;
            $scope.o.rightSvgDonuts.R_5 = $scope.o.rightSvgDonuts.REv_5;
            $scope.o.svgWebs.R_1 = $scope.o.svgWebs.REv_1;
            $scope.o.svgWebs.R_2 = $scope.o.svgWebs.REv_2;
            $scope.o.svgWebs.R_3 = $scope.o.svgWebs.REv_3;
            $scope.o.svgWebs.R_4 = $scope.o.svgWebs.REv_4;
            $scope.o.svgWebs.R_5 = $scope.o.svgWebs.REv_5;
            $scope.o.svgHistograms.R_1 = $scope.o.svgHistograms.REv_1;
            $scope.o.svgHistograms.R_2 = $scope.o.svgHistograms.REv_2;
            $scope.o.svgHistograms.R_3 = $scope.o.svgHistograms.REv_3;
            $scope.o.svgHistograms.R_4 = $scope.o.svgHistograms.REv_4;
            $scope.o.svgHistograms.R_5 = $scope.o.svgHistograms.REv_5;
            $scope.o.svgHistogramsCEF.R_1 = $scope.o.svgHistogramsCEF.REv_1;
            $scope.o.svgHistogramsCEF.R_2 = $scope.o.svgHistogramsCEF.REv_2;
            $scope.o.svgHistogramsCEF.R_3 = $scope.o.svgHistogramsCEF.REv_3;
            $scope.o.svgHistogramsCEF.R_4 = $scope.o.svgHistogramsCEF.REv_4;
            $scope.o.svgHistogramsCEF.R_5 = $scope.o.svgHistogramsCEF.REv_5;
            $scope.o.reportCEFDTOs.R_1 = $scope.o.reportCEFDTOs.REv_1;
            $scope.o.reportCEFDTOs.R_2 = $scope.o.reportCEFDTOs.REv_2;
            $scope.o.reportCEFDTOs.R_3 = $scope.o.reportCEFDTOs.REv_3;
            $scope.o.reportCEFDTOs.R_4 = $scope.o.reportCEFDTOs.REv_4;
            $scope.o.reportCEFDTOs.R_5 = $scope.o.reportCEFDTOs.REv_5;
          }
          if ($scope.$root.instanceName === 'household') {
            $scope.o.reportDTOs.R_1 = $scope.o.reportDTOs.RMe_1;
            $scope.o.reportDTOs.R_2 = $scope.o.reportDTOs.RMe_2;
            $scope.o.reportDTOs.R_3 = $scope.o.reportDTOs.RMe_3;
            $scope.o.reportDTOs.R_4 = $scope.o.reportDTOs.RMe_4;
            $scope.o.reportDTOs.R_5 = $scope.o.reportDTOs.RMe_5;
            $scope.o.leftSvgDonuts.R_1 = $scope.o.leftSvgDonuts.RMe_1;
            $scope.o.leftSvgDonuts.R_2 = $scope.o.leftSvgDonuts.RMe_2;
            $scope.o.leftSvgDonuts.R_3 = $scope.o.leftSvgDonuts.RMe_3;
            $scope.o.leftSvgDonuts.R_4 = $scope.o.leftSvgDonuts.RMe_4;
            $scope.o.leftSvgDonuts.R_5 = $scope.o.leftSvgDonuts.RMe_5;
            $scope.o.rightSvgDonuts.R_1 = $scope.o.rightSvgDonuts.RMe_1;
            $scope.o.rightSvgDonuts.R_2 = $scope.o.rightSvgDonuts.RMe_2;
            $scope.o.rightSvgDonuts.R_3 = $scope.o.rightSvgDonuts.RMe_3;
            $scope.o.rightSvgDonuts.R_4 = $scope.o.rightSvgDonuts.RMe_4;
            $scope.o.rightSvgDonuts.R_5 = $scope.o.rightSvgDonuts.RMe_5;
            $scope.o.svgWebs.R_1 = $scope.o.svgWebs.RMe_1;
            $scope.o.svgWebs.R_2 = $scope.o.svgWebs.RMe_2;
            $scope.o.svgWebs.R_3 = $scope.o.svgWebs.RMe_3;
            $scope.o.svgWebs.R_4 = $scope.o.svgWebs.RMe_4;
            $scope.o.svgWebs.R_5 = $scope.o.svgWebs.RMe_5;
            $scope.o.svgHistograms.R_1 = $scope.o.svgHistograms.RMe_1;
            $scope.o.svgHistograms.R_2 = $scope.o.svgHistograms.RMe_2;
            $scope.o.svgHistograms.R_3 = $scope.o.svgHistograms.RMe_3;
            $scope.o.svgHistograms.R_4 = $scope.o.svgHistograms.RMe_4;
            $scope.o.svgHistograms.R_5 = $scope.o.svgHistograms.RMe_5;
            $scope.o.svgHistogramsCEF.R_1 = $scope.o.svgHistogramsCEF.RMe_1;
            $scope.o.svgHistogramsCEF.R_2 = $scope.o.svgHistogramsCEF.RMe_2;
            $scope.o.svgHistogramsCEF.R_3 = $scope.o.svgHistogramsCEF.RMe_3;
            $scope.o.svgHistogramsCEF.R_4 = $scope.o.svgHistogramsCEF.RMe_4;
            $scope.o.svgHistogramsCEF.R_5 = $scope.o.svgHistogramsCEF.RMe_5;
            $scope.o.reportCEFDTOs.R_1 = $scope.o.reportCEFDTOs.RMe_1;
            $scope.o.reportCEFDTOs.R_2 = $scope.o.reportCEFDTOs.RMe_2;
            $scope.o.reportCEFDTOs.R_3 = $scope.o.reportCEFDTOs.RMe_3;
            $scope.o.reportCEFDTOs.R_4 = $scope.o.reportCEFDTOs.RMe_4;
            $scope.o.reportCEFDTOs.R_5 = $scope.o.reportCEFDTOs.RMe_5;
          }
          if ($scope.$root.instanceName === 'littleemitter') {
            $scope.o.reportDTOs.R_1 = $scope.o.reportDTOs.RPE_1;
            $scope.o.reportDTOs.R_2 = $scope.o.reportDTOs.RPE_2;
            $scope.o.reportDTOs.R_3 = $scope.o.reportDTOs.RPE_3;
            $scope.o.reportDTOs.R_4 = $scope.o.reportDTOs.RPE_4;
            $scope.o.reportDTOs.R_5 = $scope.o.reportDTOs.RPE_5;
            $scope.o.leftSvgDonuts.R_1 = $scope.o.leftSvgDonuts.RPE_1;
            $scope.o.leftSvgDonuts.R_2 = $scope.o.leftSvgDonuts.RPE_2;
            $scope.o.leftSvgDonuts.R_3 = $scope.o.leftSvgDonuts.RPE_3;
            $scope.o.leftSvgDonuts.R_4 = $scope.o.leftSvgDonuts.RPE_4;
            $scope.o.leftSvgDonuts.R_5 = $scope.o.leftSvgDonuts.RPE_5;
            $scope.o.rightSvgDonuts.R_1 = $scope.o.rightSvgDonuts.RPE_1;
            $scope.o.rightSvgDonuts.R_2 = $scope.o.rightSvgDonuts.RPE_2;
            $scope.o.rightSvgDonuts.R_3 = $scope.o.rightSvgDonuts.RPE_3;
            $scope.o.rightSvgDonuts.R_4 = $scope.o.rightSvgDonuts.RPE_4;
            $scope.o.rightSvgDonuts.R_5 = $scope.o.rightSvgDonuts.RPE_5;
            $scope.o.svgWebs.R_1 = $scope.o.svgWebs.RPE_1;
            $scope.o.svgWebs.R_2 = $scope.o.svgWebs.RPE_2;
            $scope.o.svgWebs.R_3 = $scope.o.svgWebs.RPE_3;
            $scope.o.svgWebs.R_4 = $scope.o.svgWebs.RPE_4;
            $scope.o.svgWebs.R_5 = $scope.o.svgWebs.RPE_5;
            $scope.o.svgHistograms.R_1 = $scope.o.svgHistograms.RPE_1;
            $scope.o.svgHistograms.R_2 = $scope.o.svgHistograms.RPE_2;
            $scope.o.svgHistograms.R_3 = $scope.o.svgHistograms.RPE_3;
            $scope.o.svgHistograms.R_4 = $scope.o.svgHistograms.RPE_4;
            $scope.o.svgHistograms.R_5 = $scope.o.svgHistograms.RPE_5;
            $scope.o.svgHistogramsCEF.R_1 = $scope.o.svgHistogramsCEF.RPE_1;
            $scope.o.svgHistogramsCEF.R_2 = $scope.o.svgHistogramsCEF.RPE_2;
            $scope.o.svgHistogramsCEF.R_3 = $scope.o.svgHistogramsCEF.RPE_3;
            $scope.o.svgHistogramsCEF.R_4 = $scope.o.svgHistogramsCEF.RPE_4;
            $scope.o.svgHistogramsCEF.R_5 = $scope.o.svgHistogramsCEF.RPE_5;
            $scope.o.reportCEFDTOs.R_1 = $scope.o.reportCEFDTOs.RPE_1;
            $scope.o.reportCEFDTOs.R_2 = $scope.o.reportCEFDTOs.RPE_2;
            $scope.o.reportCEFDTOs.R_3 = $scope.o.reportCEFDTOs.RPE_3;
            $scope.o.reportCEFDTOs.R_4 = $scope.o.reportCEFDTOs.RPE_4;
            $scope.o.reportCEFDTOs.R_5 = $scope.o.reportCEFDTOs.RPE_5;
          }
          $scope.leftTotalEmissions = 0;
          $scope.leftTotalScope1 = 0;
          $scope.leftTotalScope2 = 0;
          $scope.leftTotalScope3 = 0;
          $scope.rightTotalEmissions = 0;
          $scope.rightTotalScope1 = 0;
          $scope.rightTotalScope2 = 0;
          $scope.rightTotalScope3 = 0;
          _ref = $scope.o.reportDTOs.R_1.reportLines;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            line = _ref[_i];
            $scope.leftTotalScope1 += line.leftScope1Value;
            $scope.leftTotalScope2 += line.leftScope2Value;
            $scope.leftTotalScope3 += line.leftScope3Value;
            $scope.rightTotalScope1 += line.rightScope1Value;
            $scope.rightTotalScope2 += line.rightScope2Value;
            $scope.rightTotalScope3 += line.rightScope3Value;
            $scope.leftTotalEmissions += line.leftScope1Value;
            $scope.leftTotalEmissions += line.leftScope2Value;
            $scope.leftTotalEmissions += line.leftScope3Value;
            $scope.rightTotalEmissions += line.rightScope1Value;
            $scope.rightTotalEmissions += line.rightScope2Value;
            $scope.rightTotalEmissions += line.rightScope3Value;
          }
        }
        return $scope.reloading = false;
      });
    }
  };
  $scope.current_tab = 1;
  $scope.siteSelectionIsEmpty = function() {
    var filtered;
    filtered = $scope.mySites.filter(function(s) {
      return s.selected;
    });
    return filtered.length === 0;
  };
  $scope.downloadAsXls = function() {
    return $window.open('/awac/result/getReportAsXls/' + $scope.$parent.periodKey + "/" + $scope.$parent.scopeId, translationService.get('RESULT_DOWNLOAD_START', null));
  };
  return $scope.downloadPdf = function() {};
});angular.module('app.controllers').controller("UserDataCtrl", function($scope, downloadService, translationService, messageFlash, modalService) {
  $scope.isLoading = false;
  $scope.identifierInfo = {
    fieldTitle: "USER_IDENTIFIER",
    disabled: true,
    field: $scope.$root.currentPerson.identifier
  };
  $scope.passwordInfo = {
    fieldTitle: "USER_PASSWORD",
    fieldType: "password",
    disabled: true,
    field: "*****"
  };
  $scope.lastNameInfo = {
    fieldTitle: "USER_LASTNAME",
    validationRegex: "^.{1,255}$",
    validationMessage: "USER_LASTNAME_WRONG_LENGTH",
    field: $scope.$root.currentPerson.lastName,
    hideIsValidIcon: true,
    isValid: true,
    focus: function() {
      return true;
    }
  };
  $scope.firstNameInfo = {
    fieldTitle: "USER_FIRSTNAME",
    fieldType: "text",
    validationRegex: "^.{1,255}$",
    validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
    field: $scope.$root.currentPerson.firstName,
    hideIsValidIcon: true,
    isValid: true
  };
  $scope.emailInfo = {
    fieldTitle: "USER_EMAIL",
    disabled: true,
    field: $scope.$root.currentPerson.email
  };
  $scope.allFieldValid = function() {
    if ($scope.lastNameInfo.isValid && $scope.firstNameInfo.isValid) {
      return true;
    }
    return false;
  };
  $scope.$root.refreshUserData();
  $scope.send = function() {
    var data;
    if (!$scope.allFieldValid) {
      return false;
    }
    $scope.isLoading = true;
    data = {
      identifier: $scope.identifierInfo.field,
      lastName: $scope.lastNameInfo.field,
      firstName: $scope.firstNameInfo.field,
      email: $scope.emailInfo.field
    };
    downloadService.postJson('/awac/user/profile/save', data, function(result) {
      if (result.success) {
        messageFlash.displaySuccess("CHANGES_SAVED");
        $scope.$root.currentPerson.lastName = $scope.lastNameInfo.field;
        $scope.$root.currentPerson.firstName = $scope.firstNameInfo.field;
        return $scope.isLoading = false;
      } else {
        return $scope.isLoading = false;
      }
    });
    return false;
  };
  $scope.setNewEmail = function(newEmail) {
    $scope.emailInfo.field = newEmail;
    return $scope.$root.currentPerson.email = newEmail;
  };
  $scope.changeEmail = function() {
    return modalService.show(modalService.EMAIL_CHANGE, {
      oldEmail: $scope.emailInfo.field,
      cb: $scope.setNewEmail
    });
  };
  $scope.changePassword = function() {
    return modalService.show(modalService.PASSWORD_CHANGE, {});
  };
  $scope.toForm = function() {
    return $scope.$root.navToLastFormUsed();
  };
  $scope.anonymous = {
    loginInfo: {
      fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE",
      fieldType: "text",
      placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER",
      validationRegex: "^\\S{5,20}$",
      validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH",
      field: $scope.$root.currentPerson.identifier,
      isValid: false,
      focus: function() {
        return true;
      }
    },
    passwordInfo: {
      fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE",
      fieldType: "password",
      validationRegex: "^\\S{5,20}$",
      validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
      field: "",
      isValid: false
    },
    lastNameInfo: {
      fieldTitle: "USER_LASTNAME",
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_LASTNAME_WRONG_LENGTH",
      field: $scope.$root.currentPerson.lastName,
      isValid: true
    },
    firstNameInfo: {
      fieldTitle: "USER_FIRSTNAME",
      fieldType: "text",
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
      field: $scope.$root.currentPerson.firstName,
      isValid: true
    },
    emailInfo: {
      fieldTitle: "USER_EMAIL",
      validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
      validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT",
      field: $scope.$root.currentPerson.email
    }
  };
  $scope.anonymousFieldValid = function() {
    var field, key, _i, _len, _ref;
    _ref = Object.keys($scope.anonymous);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        field = $scope.anonymous[key];
        if (!field.isValid || field.isValid === false) {
          return false;
        }
      }
    }
    return true;
  };
  $scope.sendAnonymous = function() {
    var data;
    if (!$scope.anonymousFieldValid()) {
      return false;
    }
    $scope.isLoading = true;
    data = {
      identifier: $scope.anonymous.loginInfo.field,
      password: $scope.anonymous.passwordInfo.field,
      lastName: $scope.anonymous.lastNameInfo.field,
      firstName: $scope.anonymous.firstNameInfo.field,
      email: $scope.anonymous.emailInfo.field
    };
    downloadService.postJson('/awac/user/profile/anonymous/save', data, function(result) {
      if (result.success) {
        messageFlash.displaySuccess("CHANGES_SAVED");
        messageFlash.displaySuccess("Please login with your new credentials");
        $scope.$root.nav('/login');
        $scope.$root.currentPerson = null;
        $scope.$root.periodSelectedKey = null;
        $scope.$root.scopeSelectedId = null;
        return $scope.isLoading = false;
      } else {
        return $scope.isLoading = false;
      }
    });
    return false;
  };
  return $scope.isAnonymousUser = function() {
    var _ref;
    console.log(angular.copy($scope.$root.currentPerson));
    console.log(angular.copy($scope.$root.currentPerson.identifier));
    if (((_ref = $scope.$root.currentPerson) != null ? _ref.identifier.indexOf("anonymous_user") : void 0) !== -1) {
      console.log("currentUser is an anonymous user");
      return true;
    } else {
      console.log("currentUser is not anonymous user");
      return false;
    }
  };
});angular.module('app.controllers').controller("AdminTranslationBaseListCtrl", function($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper, displayLittleFormMenu, $location) {
  var fixedLists, getActionsBaseLists, getSurveysBaseLists, getSystemBaseLists, sortCodeLabels, updateOrderIndexes;
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  fixedLists = ['ActivityCategory', 'ActivitySource', 'ActivitySubCategory', 'ActivityType', 'IndicatorCategory', 'BASE_INDICATOR', 'INDICATOR', 'INTERFACE_TYPE', 'REDUCING_ACTION_STATUS', 'REDUCING_ACTION_TYPE'];
  $scope.groupsLabels = ["Listes 'système'", "Listes liées aux actions", "Listes liées aux questionnaires"];
  $scope.originalData = [];
  $scope.waitingData = true;
  $scope.isLoading = false;
  $scope.codeLabelToAdd = {
    key: "",
    labelEn: "",
    labelFr: "",
    labelNl: "",
    clear: function() {
      this.key = "";
      this.labelEn = "";
      this.labelFr = "";
      this.labelNl = "";
      return;
    }
  };
  $scope.sortableOptions = {
    axis: 'y'
  };
  $scope.allBaseLists = [];
  $scope.loadBaseLists = function() {
    downloadService.getJson("awac/admin/translations/baselists/load", function(result) {
      var baseLists;
      if (result.success) {
        baseLists = result.data.baseLists;
        $scope.allBaseLists = [getSystemBaseLists(baseLists), getActionsBaseLists(baseLists), getSurveysBaseLists(baseLists)];
        $scope.originalData = angular.copy($scope.allBaseLists);
      }
      $scope.waitingData = false;
      return;
    });
    return;
  };
  getSystemBaseLists = function(allBaseLists) {
    var res;
    res = [];
    res[0] = sortCodeLabels(_.findWhere(allBaseLists, {
      codeList: 'ActivitySource'
    }));
    res[1] = sortCodeLabels(_.findWhere(allBaseLists, {
      codeList: 'ActivityType'
    }));
    res[2] = sortCodeLabels(_.findWhere(allBaseLists, {
      codeList: 'ActivityCategory'
    }));
    res[3] = sortCodeLabels(_.findWhere(allBaseLists, {
      codeList: 'ActivitySubCategory'
    }));
    res[4] = sortCodeLabels(_.findWhere(allBaseLists, {
      codeList: 'IndicatorCategory'
    }));
    res[5] = sortCodeLabels(_.findWhere(allBaseLists, {
      codeList: 'INDICATOR'
    }));
    res[6] = sortCodeLabels(_.findWhere(allBaseLists, {
      codeList: 'BASE_INDICATOR'
    }));
    res[7] = sortCodeLabels(_.findWhere(allBaseLists, {
      codeList: 'INTERFACE_TYPE'
    }));
    return res;
  };
  sortCodeLabels = function(baseList) {
    baseList.codeLabels = codeLabelHelper.sortCodeLabelsByOrder(baseList.codeLabels);
    return baseList;
  };
  getActionsBaseLists = function(allBaseLists) {
    var res;
    res = [];
    res[0] = sortCodeLabels(_.findWhere(allBaseLists, {
      codeList: 'REDUCING_ACTION_TYPE'
    }));
    res[1] = sortCodeLabels(_.findWhere(allBaseLists, {
      codeList: 'REDUCING_ACTION_STATUS'
    }));
    return res;
  };
  getSurveysBaseLists = function(allBaseLists) {
    var baseList, res, _i, _len;
    res = _.filter(allBaseLists, function(baseList) {
      return !_.contains(fixedLists, baseList.codeList);
    });
    res = _.sortBy(res, function(baseList) {
      return baseList.codeList;
    });
    for (_i = 0, _len = res.length; _i < _len; _i++) {
      baseList = res[_i];
      baseList = sortCodeLabels(baseList);
    }
    return res;
  };
  $scope.getCalculatorByKeyPrefix = function(codeKey) {
    if (codeKey.startsWith("I_") || codeKey.startsWith("BI_")) {
      return "enterprise";
    }
    if (codeKey.startsWith("ICo_") || codeKey.startsWith("BICo_")) {
      return "municipality";
    }
    if (codeKey.startsWith("IMe_") || codeKey.startsWith("BIMe_")) {
      return "household";
    }
    if (codeKey.startsWith("IPE_") || codeKey.startsWith("BIPE_")) {
      return "littleemitter";
    }
    if (codeKey.startsWith("IEv_") || codeKey.startsWith("BIEv_")) {
      return "event";
    }
    return;
  };
  $scope.sortItems = function(baseLists) {
    var baseList, res, _i, _len;
    res = _.sortBy(baseLists, "codeList");
    for (_i = 0, _len = res.length; _i < _len; _i++) {
      baseList = res[_i];
      baseList.codeLabels = _.sortBy(baseList.codeLabels, "orderIndex");
    }
    return res;
  };
  $scope.addCodeLabel = function(baseList) {
    if (!(!!$scope.codeLabelToAdd.key && !!$scope.codeLabelToAdd.labelEn)) {
      messageFlash.displayError("Echec de l'ajout: les champs 'clé' et 'Libellé EN' sont obligatoires!");
      return;
    }
    if (_.contains(_.pluck(baseList.codeLabels, 'key'), $scope.codeLabelToAdd.key)) {
      messageFlash.displayError("Echec de l'ajout: la clé '" + $scope.codeLabelToAdd.key + "' est déjà présente dans cette liste!");
      return;
    }
    baseList.codeLabels.push(angular.copy($scope.codeLabelToAdd));
    console.log("$scope.baseLists", $scope.baseLists);
    console.log("baseList.codeLabels", baseList.codeLabels);
    $scope.codeLabelToAdd.clear();
    return;
  };
  updateOrderIndexes = function(allBaseLists) {
    var baseList, baseLists, codeLabel, index, _i, _j, _len, _len2, _ref;
    for (_i = 0, _len = allBaseLists.length; _i < _len; _i++) {
      baseLists = allBaseLists[_i];
      console.log("baseLists", baseLists);
      for (_j = 0, _len2 = baseLists.length; _j < _len2; _j++) {
        baseList = baseLists[_j];
        _ref = baseList.codeLabels;
        for (index in _ref) {
          codeLabel = _ref[index];
          codeLabel.orderIndex = +index + 1;
        }
      }
    }
    return allBaseLists;
  };
  $scope.save = function() {
    var allBaseLists, baseLists, data, _i, _len, _ref;
    $scope.isLoading = true;
    $scope.allBaseLists = updateOrderIndexes($scope.allBaseLists);
    allBaseLists = [];
    _ref = $scope.allBaseLists;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      baseLists = _ref[_i];
      allBaseLists = allBaseLists.concat(baseLists);
    }
    data = {
      baseLists: allBaseLists
    };
    return downloadService.postJson("/awac/admin/translations/baselists/save", data, function(result) {
      $scope.isLoading = false;
      if (result.success) {
        $scope.loadBaseLists();
        translationService.reinitialize();
        messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
      }
      return;
    });
  };
  $scope.ignoreChanges = false;
  $scope.$root.$on('$locationChangeStart', function(event, next, current) {
    var eq, params, updatedData;
    if (!!$scope.ignoreChanges) {
      return;
    }
    updatedData = updateOrderIndexes($scope.allBaseLists);
    eq = angular.equals($scope.originalData, updatedData);
    if (!eq) {
      event.preventDefault();
      params = {
        titleKey: "CONFIRM_EXIT_TITLE",
        messageKey: "CONFIRM_EXIT_MESSAGE",
        onConfirm: function() {
          $scope.ignoreChanges = true;
          return $location.path(next.split('#')[1]);
        }
      };
      return modalService.show(modalService.CONFIRM_DIALOG, params);
    }
  });
  return $scope.loadBaseLists();
});angular.module('app.controllers').controller("VerificationSubmitCtrl", function($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile, $window) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.selectedRequest = null;
  $scope.form = null;
  $scope.requestSelected = null;
  modalService.show(modalService.LOADING);
  downloadService.getJson('/awac/verification/verificationRequestsVerifiedToConfirm', function(result) {
    if (!result.success) {
      return modalService.close(modalService.LOADING);
    } else {
      modalService.close(modalService.LOADING);
      $scope.requests = result.data.list;
      return $scope.displayTable();
    }
  });
  $scope.displayTable = function() {
    if (!$scope.requests) {
      $scope.requests = [];
    }
    if ($scope.tableParams != null) {
      return $scope.tableParams.reload();
    } else {
      return $scope.tableParams = new ngTableParams({
        page: 1,
        count: 100
      }, {
        counts: [],
        total: 1,
        getData: function($defer, params) {
          var orderedData;
          orderedData = $filter("orderBy")($scope.requests, params.orderBy());
          $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
          params.total(orderedData.length);
          return;
        }
      });
    }
  };
  $scope.selectRequest = function(request) {
    var directiveName;
    $('.injectForm:first').empty();
    $('.injectFormMenu:first').empty();
    if ($scope.directiveMenu != null) {
      $scope.directiveMenu.remove();
    }
    if ($scope.directive != null) {
      $scope.directive.remove();
    }
    if (request != null) {
      $scope.requestSelected = request;
    }
    $scope.selectedRequest = $scope.requestSelected;
    $scope.$root.periodSelectedKey = $scope.requestSelected.period.key;
    $scope.$root.scopeSelectedId = $scope.requestSelected.scope.id;
    if (!$scope.form) {
      if ($scope.requestSelected.organizationCustomer.interfaceName === 'enterprise') {
        $scope.form = 'TAB2';
      }
      if ($scope.requestSelected.organizationCustomer.interfaceName === 'municipality') {
        $scope.form = 'TAB_C1';
      }
    }
    if ($scope.form === '/results') {
      directiveName = "<div ng-include=\"'$/angular/views/results.html'\" ng-controller=\"ResultsCtrl\"></div>";
    } else {
      directiveName = "<div ng-include=\"'$/angular/views/enterprise/" + $scope.form + ".html'\" ng-init=\"init('" + $scope.form + "')\" ng-controller=\"FormCtrl\"></div>";
    }
    $scope.directive = $compile(directiveName)($scope);
    $('.injectForm:first').append($scope.directive);
    $scope.directiveMenu = $compile("<mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu form=\"" + $scope.form + "\"></mm-awac-" + $scope.requestSelected.organizationCustomer.interfaceName + "-menu>")($scope);
    $scope.displayMenu = true;
    $('.injectFormMenu:first').append($scope.directiveMenu);
    return $scope.$root.mySites = [request.scope];
  };
  $scope.isMenuCurrentlySelected = function(target) {
    if ('/form/' + $scope.form === target || $scope.form === target) {
      return true;
    }
    return false;
  };
  $scope.downloadFile = function() {
    var url;
    url = '/awac/file/download/' + $scope.selectedRequest.verificationSuccessFileId;
    return $window.open(url);
  };
  $scope.validVerificationFinalization = function(valid) {
    var data, newStatus;
    if (valid === true) {
      if ($scope.requestSelected.status === 'VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_SUCCESS') {
        newStatus = 'VERIFICATION_STATUS_WAIT_CUSTOMER_VERIFIED_CONFIRMATION';
      } else {
        newStatus = 'VERIFICATION_STATUS_CORRECTION';
      }
    } else {
      newStatus = 'VERIFICATION_STATUS_VERIFICATION';
    }
    data = {
      url: "/awac/verification/setStatus",
      successMessage: "CHANGES_SAVED",
      title: "VALID_REQUEST",
      data: {
        scopeId: $scope.selectedRequest.scope.id,
        periodKey: $scope.selectedRequest.period.key,
        newStatus: newStatus
      },
      afterSave: function() {
        return $scope.removeRequest();
      }
    };
    return modalService.show(modalService.PASSWORD_CONFIRMATION, data);
  };
  $scope.navTo = function(target) {
    var form;
    form = target.replace('/form/', '');
    $scope.form = form;
    return $scope.selectRequest();
  };
  $scope.consultFinalComment = function() {
    var data;
    data = {
      comment: $scope.selectedRequest.verificationRejectedComment
    };
    return modalService.show(modalService.VERIFICATION_FINALIZATION_VISUALIZATION, data);
  };
  $scope.consultEvent = function() {
    var data;
    data = {
      organizationCustomer: $scope.requestSelected.organizationCustomer
    };
    return modalService.show(modalService.CONSULT_EVENT, data);
  };
  return $scope.removeRequest = function() {
    var i, request, _i, _len, _ref;
    i = 0;
    _ref = $scope.requests;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      request = _ref[_i];
      if (request.id === $scope.requestSelected.id) {
        $scope.requests.splice(i, 1);
      }
      i++;
    }
    $scope.displayTable();
    $('.injectForm:first').empty();
    $('.injectFormMenu:first').empty();
    return $scope.requestSelected = null;
  };
});angular.module('app.controllers').controller("AdminFactorsManagerCtrl", function($scope, $filter, $q, $timeout, $location, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.factors = null;
  $scope.newFactor = {
    __type: 'eu.factorx.awac.dto.awac.post.CreateFactorDTO',
    valueSince2000: 0.0
  };
  modalService.show(modalService.LOADING);
  $scope.loadPromise = downloadService.getJson("/awac/admin/factors/all", function(result) {
    var f, tt, _i, _len, _ref;
    modalService.close(modalService.LOADING);
    if (result.success === true) {
      $scope.periods = result.data.periods;
      $scope.factors = result.data.factors;
      $scope.newFactor.indicatorCategories = result.data.indicatorCategories;
      $scope.newFactor.activityTypes = result.data.activityTypes;
      $scope.newFactor.activitySources = result.data.activitySources;
      $scope.newFactor.unitCategories = result.data.unitCategories;
      $scope.originalFactors = angular.copy(result.data.factors);
      tt = $filter('translateText');
      _ref = $scope.factors;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        f = _ref[_i];
        f.typeString = tt(f.indicatorCategory) + " / " + tt(f.activityType) + " / " + tt(f.activitySource);
      }
      return $scope.parameters = new ngTableParams({
        page: 1,
        count: 10,
        sorting: {
          indicatorCategory: 'asc'
        }
      }, {
        total: $scope.factors.length,
        getData: function($defer, params) {
          var orderedData;
          console.log('getData');
          orderedData = $scope.factors;
          orderedData = $filter('filter')(orderedData, params.filter());
          params.total(orderedData.length);
          if (params.sorting()) {
            orderedData = $filter('orderBy')(orderedData, params.orderBy());
          }
          return $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
        },
        $scope: {
          $data: {}
        }
      });
    }
  });
  $scope.isModified = function(factor) {
    var compared, eq, original;
    original = _.find($scope.originalFactors, function(o) {
      return o.key === factor.key;
    });
    compared = _.omit(factor, 'typeString');
    eq = angular.equals(original, compared);
    return !eq;
  };
  $scope.getAvailablePeriods = function(factor, fv) {
    return _.reject($scope.periods, function(e) {
      return _.find(factor.factorValues, function(ee) {
        if (ee === fv) {
          return false;
        }
        return ee.dateIn === e.key;
      });
    });
  };
  $scope.addValue = function(factor) {
    var availablePeriods, dateIn, index, lastUsed, _ref;
    availablePeriods = _.sortBy($scope.getAvailablePeriods(factor, null), 'label');
    lastUsed = _.max(factor.factorValues, function(fv) {
      return fv.dateIn;
    });
    index = _.sortedIndex(_.pluck(availablePeriods, 'key'), lastUsed.dateIn);
    dateIn = (_ref = availablePeriods[index]) != null ? _ref.key : void 0;
    return factor.factorValues.push({
      __type: 'eu.factorx.awac.dto.admin.get.FactorValueDTO',
      id: null,
      value: 0,
      dateIn: dateIn,
      dateOut: null
    });
  };
  $scope.removeFactorValue = function(factor, fv) {
    var params;
    params = {
      titleKey: "FACTORS_DELETE_CONFIRMATION_TITLE",
      messageKey: "FACTORS_DELETE_CONFIRMATION_MESSAGE",
      onConfirm: function() {
        return factor.factorValues = _.without(factor.factorValues, fv);
      }
    };
    return modalService.show(modalService.CONFIRM_DIALOG, params);
  };
  $scope.reload = function() {
    return downloadService.getJson("/awac/admin/factors/all", function(result) {
      var f, tt, _i, _len, _ref;
      modalService.close(modalService.LOADING);
      if (result.success === true) {
        $scope.periods = result.data.periods;
        $scope.factors = result.data.factors;
        $scope.originalFactors = angular.copy(result.data.factors);
        tt = $filter('translateText');
        _ref = $scope.factors;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          f = _ref[_i];
          f.typeString = tt(f.indicatorCategory) + " / " + tt(f.activityType) + " / " + tt(f.activitySource);
        }
        return $timeout(function() {
          return $scope.parameters.reload();
        }, 500);
      }
    });
  };
  $scope.exportXls = function() {
    modalService.show(modalService.LOADING);
    return downloadService.getJson("/awac/admin/factors/export", function(result) {
      return modalService.close(modalService.LOADING);
    });
  };
  $scope.save = function() {
    var f, fvs, i, _i, _len, _ref, _ref2;
    if ($scope.hasEdits()) {
      modalService.show(modalService.LOADING);
      _ref = $scope.factors;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        f = _ref[_i];
        if ($scope.isModified(f)) {
          fvs = _.sortBy(f.factorValues, 'dateIn');
          for (i = 1, _ref2 = fvs.length; (1 <= _ref2 ? i < _ref2 : i > _ref2); (1 <= _ref2 ? i += 1 : i -= 1)) {
            fvs[i - 1].dateOut = '' + (fvs[i].dateIn - 1);
          }
          f.factorValues = fvs;
        }
      }
      return downloadService.postJson('/awac/admin/factors/update', {
        __type: 'eu.factorx.awac.dto.awac.post.UpdateFactorsDTO',
        factors: $scope.factors
      }, function(result) {
        if (result.success === true) {
          return $scope.reload();
        }
      });
    }
  };
  $scope.names = function(column) {
    var def;
    def = $q.defer();
    $q.all([$scope.loadPromise]).then(function() {
      var d;
      d = _.pluck($scope.factors, column);
      d = _.uniq(d);
      d = _.map(d, function(e) {
        return {
          id: e,
          title: e
        };
      });
      return def.resolve(d);
    });
    return def;
  };
  $scope.createFactor = function() {
    modalService.show(modalService.LOADING);
    return downloadService.postJson('/awac/admin/factors/create', $scope.newFactor, function(result) {
      modalService.close(modalService.LOADING);
      if (result.success === true) {
        return $scope.reload();
      }
    });
  };
  $scope.hasEdits = function() {
    return _.any($scope.factors, $scope.isModified);
  };
  $scope.ignoreChanges = false;
  return $scope.$root.$on('$locationChangeStart', function(event, next, current) {
    var params;
    if (next === current) {
      return;
    }
    if (!($scope.hasEdits() && !$scope.ignoreChanges)) {
      return;
    }
    event.preventDefault();
    params = {
      titleKey: "FACTORS_CANCEL_CONFIRMATION_TITLE",
      messageKey: "FACTORS_CANCEL_CONFIRMATION_MESSAGE",
      onConfirm: function() {
        $scope.ignoreChanges = true;
        return $location.path(next.split('#')[1]);
      }
    };
    return modalService.show(modalService.CONFIRM_DIALOG, params);
  });
});angular.module('app.controllers').controller("VerificationManageCtrl", function($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, $filter, ngTableParams) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.loadingRequest = [];
  modalService.show(modalService.LOADING);
  downloadService.getJson("/awac/verification/requestsToManage", function(result) {
    if (!result.success) {
      return modalService.close(modalService.LOADING);
    } else {
      $scope.requests = result.data.list;
      $scope.displayTable();
      return modalService.close(modalService.LOADING);
    }
  });
  $scope.displayTable = function() {
    if (!$scope.requests) {
      $scope.requests = [];
    }
    if ($scope.tableParams != null) {
      return $scope.tableParams.reload();
    } else {
      return $scope.tableParams = new ngTableParams({
        page: 1,
        count: 100,
        sorting: {
          code: "asc"
        }
      }, {
        total: 0,
        getData: function($defer, params) {
          var orderedData;
          orderedData = $filter("orderBy")($scope.requests, params.orderBy());
          $defer.resolve(orderedData.slice((params.page() - 1) * params.count(), params.page() * params.count()));
          return params.total($scope.requests.length);
        }
      });
    }
  };
  $scope.changeStatus = function(request, newStatus) {
    var dto;
    dto = {
      scopeId: request.scope.id,
      periodKey: request.period.key,
      newStatus: newStatus
    };
    $scope.loadingRequest[request.id] = true;
    return downloadService.postJson("/awac/verification/setStatus", dto, function(result) {
      var i, r, _i, _len, _ref;
      if (!result.success) {
        return $scope.loadingRequest[request.id] = false;
      } else {
        request.status = newStatus;
        $scope.loadingRequest[request.id] = false;
        if (newStatus === 'VERIFICATION_STATUS_REJECTED') {
          i = 0;
          _ref = $scope.requests;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            r = _ref[_i];
            if (r.id === request.id) {
              $scope.requests.splice(i, 1);
            }
            i++;
          }
        }
        return $scope.displayTable();
      }
    });
  };
  $scope.assignRequest = function(request) {
    var params;
    params = {
      request: request
    };
    return modalService.show(modalService.VERIFICATION_ASSIGN, params);
  };
  return $scope.addKey = function() {
    var dto;
    dto = {
      key: angular.copy($scope.keyToInject)
    };
    $scope.addKeyLoading = true;
    $scope.keyToInject = "";
    return downloadService.postJson("/awac/verification/addRequestByKey", dto, function(result) {
      if (!result.success) {
        return $scope.addKeyLoading = false;
      } else {
        $scope.requests.push(result.data);
        $scope.displayTable();
        return $scope.addKeyLoading = false;
      }
    });
  };
});angular.module('app.controllers').controller("AdminTranslationSurveysLabelsCtrl", function($scope, $compile, downloadService, modalService, messageFlash, translationService, displayLittleFormMenu, codeLabelHelper, $location) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.formLabelsByCalculator = {};
  $scope.initialLabels = {};
  $scope.waitingData = true;
  $scope.isLoading = false;
  $scope.loadCodeLabels = function() {
    downloadService.getJson("/awac/admin/translations/surveyslabels/load", function(result) {
      var allFormLabels;
      if (result.success) {
        allFormLabels = result.data.forms;
        $scope.initialLabels = angular.copy($scope.flattenCodeLabels(allFormLabels));
        console.log("$scope.initialLabels", $scope.initialLabels);
        allFormLabels = _.sortBy(allFormLabels, 'codeKey');
        $scope.formLabelsByCalculator = _.groupBy(allFormLabels, 'calculatorCodeKey');
        $scope.initialFormLabelsByCalculator = angular.copy($scope.formLabelsByCalculator);
      }
      $scope.waitingData = false;
      return;
    });
    return;
  };
  $scope.flattenCodeLabels = function(formLabelsDTOs) {
    var form, questionSet, res, _i, _j, _len, _len2, _ref;
    res = [];
    for (_i = 0, _len = formLabelsDTOs.length; _i < _len; _i++) {
      form = formLabelsDTOs[_i];
      _ref = form.questionSets;
      for (_j = 0, _len2 = _ref.length; _j < _len2; _j++) {
        questionSet = _ref[_j];
        res.concat($scope.addQuestionSetLabels(res, questionSet));
      }
    }
    return _.indexBy(res, "key");
  };
  $scope.addQuestionSetLabels = function(res, questionSet) {
    var childQuestionSet, question, _i, _j, _len, _len2, _ref, _ref2;
    res.push(questionSet.label);
    res.push(questionSet.associatedTipLabel);
    _ref = questionSet.questions;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      question = _ref[_i];
      res.push(question.label);
      res.push(question.associatedTipLabel);
    }
    _ref2 = questionSet.children;
    for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
      childQuestionSet = _ref2[_j];
      res.concat($scope.addQuestionSetLabels(res, childQuestionSet));
    }
    return res;
  };
  $scope.save = function() {
    var codeLabel, data, finalLabels, formLabelsDTOs, initialLabel, key, updatedCodeLabels;
    $scope.isLoading = true;
    formLabelsDTOs = _.flatten(_.values($scope.formLabelsByCalculator));
    finalLabels = $scope.flattenCodeLabels(formLabelsDTOs);
    updatedCodeLabels = [];
    for (key in finalLabels) {
      codeLabel = finalLabels[key];
      initialLabel = $scope.initialLabels[key];
      if (!angular.equals(codeLabel, initialLabel)) {
        console.log(codeLabel, initialLabel);
        if (!codeLabel.labelEn) {
          messageFlash.displayError("Le champ libellé EN est obligatoire. Celui-ci n'est pas défini pour l'élément '" + codeLabel.key + "'");
          $scope.isLoading = false;
          return;
        }
        updatedCodeLabels.push(codeLabel);
      }
    }
    console.log('updatedCodeLabels', updatedCodeLabels);
    if (updatedCodeLabels.length === 0) {
      messageFlash.displayError("Vous n'avez effectué aucun changement");
      $scope.isLoading = false;
      return;
    }
    data = {
      codeLabelsByList: {
        'QUESTION': updatedCodeLabels
      }
    };
    return downloadService.postJson("/awac/admin/translations/surveyslabels/save", data, function(result) {
      if (result.success) {
        $scope.loadCodeLabels();
        messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
        translationService.reinitialize();
      }
      return $scope.isLoading = false;
    });
  };
  $scope.ignoreChanges = false;
  $scope.$root.$on('$locationChangeStart', function(event, next, current) {
    var eq, params;
    if (!!$scope.ignoreChanges) {
      return;
    }
    eq = angular.equals($scope.initialFormLabelsByCalculator, $scope.formLabelsByCalculator);
    if (!eq) {
      event.preventDefault();
      params = {
        titleKey: "CONFIRM_EXIT_TITLE",
        messageKey: "CONFIRM_EXIT_MESSAGE",
        onConfirm: function() {
          $scope.ignoreChanges = true;
          return $location.path(next.split('#')[1]);
        }
      };
      return modalService.show(modalService.CONFIRM_DIALOG, params);
    }
  });
  return $scope.loadCodeLabels();
});angular.module('app.controllers').controller("AdminAverageCtrl", function($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, ngTableParams, $filter, $compile, $window) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.isLoading = false;
  $scope.naceCodes = [
    {
      key: null,
      label: 'tous les NACEs'
    }, {
      key: 'SECTEURPRIMAIRE',
      label: 'Industrie primaire, hormis le secteur agricole'
    }, {
      key: 'SECTEURSECONDAIRE',
      label: 'Production de biens intermédiaires'
    }, {
      key: 'SECTEURSECONDAIRE',
      label: 'Production de biens de consommation'
    }, {
      key: 'SECTEURTERTIAIRE',
      label: 'Tertiaire'
    }
  ];
  $scope.onlyVerifiedForm = true;
  downloadService.getJson('/awac/admin/average/naceCodes', function(result) {
    var code, codeList, _i, _len, _ref, _results;
    if (result.success) {
      _ref = result.data.list;
      _results = [];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        codeList = _ref[_i];
        _results.push((function() {
          var _i, _len, _ref, _results;
          _ref = codeList.codeLabels;
          _results = [];
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            code = _ref[_i];
            _results.push($scope.naceCodes.push({
              key: codeList.code + "/" + code.key,
              label: code.label
            }));
          }
          return _results;
        })());
      }
      return _results;
    }
  });
  $scope.interfaceNames = ["enterprise", "municipality", "household", "event", "littleemitter"];
  $scope.results = {
    interface: null,
    period: null
  };
  $scope.naceCode = null;
  $scope.allFieldValid = function() {
    var key, _i, _len, _ref;
    _ref = Object.keys($scope.results);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        if (!($scope.results[key] != null)) {
          return false;
        }
      }
    }
    return true;
  };
  return $scope.askAverage = function(interfaceName) {
    var data, naceCodeKey, naceCodeListKey;
    $scope.isLoading = true;
    if ($scope.naceCode != null) {
      naceCodeListKey = $scope.naceCode.split("/")[0];
      if ($scope.naceCode.split("/").length > 1) {
        naceCodeKey = $scope.naceCode.split("/")[1];
      }
    }
    data = {
      interfaceName: $scope.results.interface,
      periodKey: $scope.results.period,
      naceCodeListKey: naceCodeListKey,
      naceCodeKey: naceCodeKey,
      onlyVerifiedForm: $scope.onlyVerifiedForm
    };
    console.log(data);
    return downloadService.postJson('/awac/admin/average/computeAverage', data, function(result) {
      $scope.isLoading = false;
      if (result.success) {
        if (!!result.data && !!result.data.message) {
          return messageFlash.displayInfo(result.data.message);
        } else {
          return messageFlash.displayInfo("STATS_SUCCESS_MESSAGE");
        }
      }
    });
  };
});angular.module('app.controllers').controller("AdminOrganizationDataCtrl", function($scope, $compile, downloadService, modalService, messageFlash, displayLittleFormMenu) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  downloadService.getJson("/awac/admin/organizationData", function(result) {
    if (result.success) {
      console.log(result.data);
      return $scope.computeData(result.data);
    }
  });
  downloadService.getJson("/awac/admin/organizationData/getLanguagesData", function(result) {
    var k, v, _ref, _results;
    if (result.success) {
      _ref = result.data.map;
      _results = [];
      for (k in _ref) {
        v = _ref[k];
        _results.push($scope.data[k] ? ($scope.data[k].frEnabled = v.frEnabled, $scope.data[k].nlEnabled = v.nlEnabled, $scope.data[k].enEnabled = v.enEnabled) : void 0);
      }
      return _results;
    }
  });
  $scope.data = {
    enterprise: {
      total: 0,
      shareData: 0,
      sites: 0,
      products: 0,
      closedForm: {},
      frEnabled: false,
      nlEnabled: false,
      enEnabled: false
    },
    municipality: {
      total: 0,
      shareData: 0,
      sites: 0,
      products: 0,
      closedForm: {},
      frEnabled: false,
      nlEnabled: false,
      enEnabled: false
    },
    event: {
      total: 0,
      shareData: 0,
      sites: 0,
      products: 0,
      closedForm: {},
      frEnabled: false,
      nlEnabled: false,
      enEnabled: false
    },
    household: {
      total: 0,
      shareData: 0,
      sites: 0,
      products: 0,
      closedForm: {},
      frEnabled: false,
      nlEnabled: false,
      enEnabled: false
    },
    littleemitter: {
      total: 0,
      shareData: 0,
      sites: 0,
      products: 0,
      closedForm: {},
      frEnabled: false,
      nlEnabled: false,
      enEnabled: false
    }
  };
  $scope.computeData = function(data) {
    var key, org, _i, _j, _len, _len2, _ref, _ref2;
    _ref = data.list;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      org = _ref[_i];
      $scope.data[org.organization.interfaceName].total++;
      if (org.organization.statisticsAllowed === true) {
        $scope.data[org.organization.interfaceName].shareData++;
      }
      $scope.data[org.organization.interfaceName].sites += org.siteNb;
      $scope.data[org.organization.interfaceName].products += org.productNb;
      if (org.closedFormMap != null) {
        _ref2 = Object.keys(org.closedFormMap);
        for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
          key = _ref2[_j];
          if (!$scope.data[org.organization.interfaceName].closedForm[key]) {
            $scope.data[org.organization.interfaceName].closedForm[key] = org.closedFormMap[key];
          } else {
            $scope.data[org.organization.interfaceName].closedForm[key] += org.closedFormMap[key];
          }
        }
      }
    }
    return console.log($scope.data);
  };
  $scope.sendEmail = function(calculatorTarget, onlyOrganizationSharedData) {
    var params;
    params = {
      calculatorTarget: calculatorTarget,
      onlyOrganizationSharedData: onlyOrganizationSharedData
    };
    return modalService.show(modalService.SEND_EMAIL, params);
  };
  return $scope.toggleLanguageEnabled = function(calculator, lang) {
    modalService.show(modalService.LOADING);
    return downloadService.getJson("/awac/admin/organizationData/toggleLanguage/" + calculator + "/" + lang, function(result) {
      modalService.close(modalService.LOADING);
      if (result.success) {
        return $scope.data[calculator][lang + 'Enabled'] = !$scope.data[calculator][lang + 'Enabled'];
      }
    });
  };
});angular.module('app.controllers').controller("AdminDriverManageCtrl", function($scope, displayLittleFormMenu, downloadService, modalService, $location) {
  var wasEdited;
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.tempIdCounter = 0;
  $scope.drivers = null;
  wasEdited = false;
  modalService.show(modalService.LOADING);
  downloadService.getJson("/awac/admin/driver/all", function(result) {
    var driver, value, _i, _j, _len, _len2, _ref, _ref2;
    modalService.close(modalService.LOADING);
    if (result.success === true) {
      $scope.drivers = result.data.list;
      _ref = $scope.drivers;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        driver = _ref[_i];
        _ref2 = driver.driverValues;
        for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
          value = _ref2[_j];
          value.tempId = ++$scope.tempIdCounter;
        }
      }
      $scope.orderDriverValues();
      return $scope.originalDrivers = angular.copy($scope.drivers);
    }
  });
  $scope.wasEdited = function() {
    return wasEdited = true;
  };
  $scope.addValue = function(driver) {
    driver.driverValues.push({
      tempId: ++$scope.tempIdCounter
    });
    return wasEdited = true;
  };
  $scope.getPeriod = function(driver, currentDriverValue) {
    var driverValue, founded, period, periods, _i, _j, _len, _len2, _ref, _ref2;
    periods = [];
    _ref = $scope.$root.periods;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      period = _ref[_i];
      founded = false;
      _ref2 = driver.driverValues;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        driverValue = _ref2[_j];
        if (period.key === (driverValue != null ? driverValue.fromPeriodKey : void 0) && period.key !== (currentDriverValue != null ? currentDriverValue.fromPeriodKey : void 0)) {
          founded = true;
          break;
        }
      }
      if (founded === false) {
        periods.push(period);
      }
    }
    return periods;
  };
  $scope.orderDriverValues = function() {
    var driver, driverValue, driverValues, founded, i, sortedDriver, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _results;
    _ref = $scope.drivers;
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      driver = _ref[_i];
      driverValues = [];
      _ref2 = driver.driverValues;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        driverValue = _ref2[_j];
        i = 0;
        founded = false;
        for (_k = 0, _len3 = driverValues.length; _k < _len3; _k++) {
          sortedDriver = driverValues[_k];
          if (parseFloat(sortedDriver.fromPeriodKey) > parseFloat(driverValue.fromPeriodKey)) {
            driverValues.splice(i, 0, driverValue);
            founded = true;
            break;
          }
          i++;
        }
        if (founded === false) {
          driverValues.push(driverValue);
        }
      }
      _results.push(driver.driverValues = driverValues);
    }
    return _results;
  };
  $scope.save = function() {
    modalService.show(modalService.LOADING);
    return downloadService.postJson('/awac/admin/driver/update', {
      list: $scope.drivers
    }, function(result) {
      modalService.close(modalService.LOADING);
      return wasEdited = false;
    });
  };
  $scope.remove = function(driver, valueTempId) {
    var params;
    params = {
      titleKey: 'DRIVER_CONFIRM_REMOVE_MODAL_TITLE',
      messageKey: 'DRIVER_CONFIRM_REMOVE_MODAL_BODY',
      onConfirm: $scope.removeConfirmed,
      confirmParams: [driver, valueTempId]
    };
    return modalService.show(modalService.CONFIRM_DIALOG, params);
  };
  $scope.removeConfirmed = function(driver, valueTempId) {
    var i, value, _i, _len, _ref, _results;
    i = 0;
    _ref = driver.driverValues;
    _results = [];
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      value = _ref[_i];
      if (value.tempId === valueTempId) {
        driver.driverValues.splice(i, 1);
        console.log("wasEdited => remove");
        wasEdited = true;
        break;
      }
      _results.push(i++);
    }
    return _results;
  };
  $scope.isModified = function(drivers) {
    var compared, eq, original;
    original = _.find($scope.originalDrivers, function(o) {
      return o.key === drivers.key;
    });
    compared = _.omit(drivers, 'typeString');
    eq = angular.equals(original, compared);
    console.log(original);
    console.log(compared);
    console.log(eq);
    return !eq;
  };
  $scope.ignoreChanges = false;
  return $scope.$root.$on('$locationChangeStart', function(event, next, current) {
    var params;
    if (!!$scope.ignoreChanges) {
      return;
    }
    if (wasEdited === true) {
      event.preventDefault();
      params = {
        titleKey: "DIVERS_CANCEL_CONFIRMATION_TITLE",
        messageKey: "DIVERS_CANCEL_CONFIRMATION_MESSAGE",
        onConfirm: function() {
          $scope.ignoreChanges = true;
          return $location.path(next.split('#')[1]);
        }
      };
      return modalService.show(modalService.CONFIRM_DIALOG, params);
    }
  });
});angular.module('app').run(function(loggerService) {
  var log;
  loggerService.initialize();
  $('body').keydown(function(evt) {
    var loggerElement, state;
    if (evt.which === 32 && evt.altKey && evt.ctrlKey) {
      loggerElement = $('#logger');
      state = parseInt(loggerElement.attr('data-state'));
      return loggerElement.attr('data-state', (state + 1) % 3);
    }
  });
  log = loggerService.get('initializer');
  return log.info("Application is started");
});
angular.module('app.controllers').controller("MainCtrl", function($scope, downloadService, translationService, $sce, $location, $route, $routeParams, modalService, $timeout, messageFlash, $compile, $element, $window) {
  var directive, p;
  $scope.displayMenu = false;
  $scope.displayLittleMenu = false;
  $scope.isLoading = function() {
    var k;
    for (k in $scope.initialLoad) {
      if (!$scope.initialLoad[k]) {
        return true;
      }
    }
    return false;
  };
  $scope.initialLoad = {
    translations: false
  };
  if ($scope.$root.instanceName !== "verification" && $scope.$root.instanceName !== "admin") {
    directive = $compile("<mm-awac-" + $scope.$root.instanceName + "-menu></mm-awac-" + $scope.$root.instanceName + "_menu>")($scope);
    p = $('.inject-menu:first', $element);
    $(directive).insertAfter(p);
  }
  $scope.missedTranslationLoadings = 0;
  $scope.$on("LOAD_FINISHED", function(event, args) {
    if (args.type === "TRANSLATIONS") {
      if (!args.success && $scope.missedTranslationLoadings < 3) {
        $scope.missedTranslationLoadings += 1;
        translationService.initialize($rootScope.language);
      } else {
        if (args.success) {
          $scope.missedTranslationLoadings = 0;
        }
        $scope.initialLoad.translations = args.success;
      }
    }
    return;
  });
  $scope.isMenuCurrentlySelected = function(loc) {
    if ($location.path().substring(0, loc.length) === loc) {
      return true;
    } else {
      return false;
    }
  };
  window.onbeforeunload = function(event) {
    var canBeContinue, result, _base;
    canBeContinue = true;
    if (($scope.getMainScope() != null) && (typeof $scope.getMainScope === "function" ? $scope.getMainScope().validNavigation : void 0) !== void 0) {
      result = typeof $scope.getMainScope === "function" ? typeof (_base = $scope.getMainScope()).validNavigation === "function" ? _base.validNavigation() : void 0 : void 0;
      if (result.valid === false) {
        return translationService.get('MODAL_CONFIRMATION_EXIT_FORM_MESSAGE');
      }
    }
  };
  $scope.$on('$routeChangeSuccess', function(event, args) {
    return $timeout(function() {
      return $scope.computeDisplayMenu();
    }, 0);
  });
  $scope.computeDisplayMenu = function() {
    if (($scope.getMainScope() != null) && ($scope.getMainScope().displayFormMenu != null) && $scope.getMainScope().displayFormMenu === true) {
      $scope.displayMenu = true;
    } else {
      $scope.displayMenu = false;
    }
    if (($scope.getMainScope() != null) && ($scope.getMainScope().displayLittleFormMenu != null) && $scope.getMainScope().displayLittleFormMenu === true) {
      return $scope.displayLittleMenu = true;
    } else {
      return $scope.displayLittleMenu = false;
    }
  };
  $scope.periodKey = null;
  $scope.$watch('$root.scopeSelectedId', function(o, n) {
    if (o !== n) {
      return $scope.computeScopeAndPeriod();
    }
  });
  $scope.$watch('$root.periodSelectedKey', function(o, n) {
    if (o !== n) {
      $scope.$root.periodToCompare = void 0;
      return $scope.computeScopeAndPeriod();
    }
  });
  $scope.computeScopeAndPeriod = function() {
    var k, url, v;
    if (($scope.$root.periodSelectedKey != null) && ($scope.$root.scopeSelectedId != null)) {
      $routeParams.form = $route.current.params.form;
      if ($route.current) {
        p = $route.current.$$route.originalPath;
        if (p === "/noScope") {
          url = $scope.$root.getDefaultRoute();
          $scope.$root.nav(url);
        } else {
          p = p.replace(new RegExp("\\/:period\\b", 'g'), '');
          p = p.replace(new RegExp("\\/:scope\\b", 'g'), '');
          for (k in $routeParams) {
            v = $routeParams[k];
            p = p.replace(new RegExp("\\:" + k + "\\b", 'g'), v);
          }
          if (p !== $route.current.$$route.originalPath) {
            $scope.$root.nav(p);
          }
        }
      }
      $scope.$root.computeAvailablePeriod();
      $scope.loadPeriodForComparison();
      $scope.loadFormProgress();
      return $scope.$root.testCloseable();
    }
  };
  $scope.periodsForComparison = [
    {
      'key': 'default',
      'label': translationService.get('NO_PERIOD_SELECTED')
    }
  ];
  $scope.$root.periodToCompare = 'default';
  $scope.save = function() {
    $scope.$broadcast('SAVE');
    return $scope.$root.$broadcast("REFRESH_LAST_SAVE_TIME");
  };
  $scope.getMainScope = function() {
    var mainScope;
    return mainScope = angular.element($('[ng-view]')[0]).scope();
  };
  $scope.loadPeriodForComparison = function() {
    var url;
    if (!!$scope.$root.scopeSelectedId) {
      url = '/awac/answer/getPeriodsForComparison/' + $scope.$root.scopeSelectedId;
      return downloadService.getJson(url, function(result) {
        var currentComparisonFounded, period, _i, _len, _ref;
        if (result.success) {
          $scope.periodsForComparison = [
            {
              'key': 'default',
              'label': translationService.get('NO_PERIOD_SELECTED')
            }
          ];
          currentComparisonFounded = false;
          _ref = result.data.periodDTOList;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            period = _ref[_i];
            if (period.key !== $scope.$root.periodSelectedKey) {
              $scope.periodsForComparison.push(period);
              if (period.key === $scope.$root.periodToCompare) {
                currentComparisonFounded = true;
              }
            }
          }
          if (currentComparisonFounded === false || $scope.$root.periodSelectedKey === $scope.$root.periodToCompare) {
            return $scope.$root.periodToCompare = 'default';
          }
        }
      });
    }
  };
  $scope.getProgress = function(form) {
    var formProgress, _i, _len, _ref;
    if ($scope.formProgress !== null) {
      _ref = $scope.formProgress;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        formProgress = _ref[_i];
        if (formProgress.form === form) {
          return formProgress.percentage;
        }
      }
    }
    return 0;
  };
  $scope.formProgress = null;
  $scope.loadFormProgress = function() {
    if (!!$scope.$root.scopeSelectedId && !!$scope.$root.periodSelectedKey) {
      return downloadService.getJson("/awac/answer/formProgress/" + $scope.$root.periodSelectedKey + "/" + $scope.$root.scopeSelectedId, function(result) {
        if (result.success) {
          return $scope.formProgress = result.data.listFormProgress;
        }
      });
    }
  };
  $scope.$on("REFRESH_LAST_SAVE_TIME", function(event, args) {
    var date, minuteToAdd;
    if (args !== void 0) {
      if (args.time === null) {
        date = null;
      } else {
        date = new Date(args.time);
        minuteToAdd = new Date().getTimezoneOffset();
        date = new Date(date.getTime() - minuteToAdd * 60000);
      }
    } else {
      date = new Date();
    }
    return $scope.lastSaveTime = date;
  });
  $scope.getClassContent = function() {
    if ($scope.$root.hideHeader() === false) {
      if ($scope.getMainScope() != null) {
        if (($scope.getMainScope().displayFormMenu != null) && $scope.getMainScope().displayFormMenu === true) {
          return 'content-with-menu';
        } else if (($scope.getMainScope().displayLittleFormMenu != null) && $scope.getMainScope().displayLittleFormMenu === true) {
          return 'content-with-little-menu';
        } else {
          return 'content-without-menu';
        }
      }
    }
    return '';
  };
  $scope.requestVerification = function() {
    return modalService.show('REQUEST_VERIFICATION');
  };
  $scope.cancelVerification = function() {
    var data;
    data = {
      url: "/awac/verification/setStatus",
      desc: 'CANCEL_VERIFICATION_DESC',
      successMessage: 'CHANGES_SAVED',
      title: 'CANCEL_VERIFICATION_TITLE',
      data: {
        scopeId: $scope.$root.scopeSelectedId,
        periodKey: $scope.$root.periodSelectedKey,
        newStatus: 'VERIFICATION_STATUS_REJECTED'
      },
      afterSave: function() {
        $scope.$root.verificationRequest = null;
        return $scope.$root.$broadcast('CLEAN_VERIFICATION');
      }
    };
    return modalService.show(modalService.PASSWORD_CONFIRMATION, data);
  };
  $scope.confirmVerifier = function() {
    return modalService.show(modalService.VERIFICATION_CONFIRMATION);
  };
  $scope.consultVerificationFinalComment = function() {
    var data;
    if ($scope.$root.verificationRequest != null) {
      data = {
        comment: $scope.$root.verificationRequest.verificationRejectedComment
      };
      return modalService.show(modalService.VERIFICATION_FINALIZATION_VISUALIZATION, data);
    }
  };
  $scope.resubmitVerification = function() {
    var data;
    data = {
      url: "/awac/verification/setStatus",
      desc: "VERIFICATION_RESUBMIT_DESC",
      successMessage: "CHANGES_SAVED",
      title: "VERIFICATION_RESUBMIT_TITLE",
      data: {
        scopeId: $scope.$root.scopeSelectedId,
        periodKey: $scope.$root.periodSelectedKey,
        newStatus: 'VERIFICATION_STATUS_VERIFICATION'
      },
      afterSave: function() {
        var _ref;
        return (_ref = $scope.$root.verificationRequest) != null ? _ref.status = 'VERIFICATION_STATUS_REJECTED' : void 0;
      }
    };
    return modalService.show(modalService.PASSWORD_CONFIRMATION, data);
  };
  $scope.consultVerification = function() {
    return modalService.show(modalService.VERIFICATION_DOCUMENT);
  };
  $scope.navTo = function(target) {
    return $scope.$root.nav(target);
  };
  if (typeof String.prototype.startsWith !== 'function') {
    return String.prototype.startsWith = function(str) {
      return this.slice(0, str.length) === str;
    };
  }
});
angular.module('app').run(function($rootScope, $location, downloadService, messageFlash, $timeout, translationService, tmhDynamicLocale, $routeParams, $route, modalService) {
  $rootScope.languages = [];
  $rootScope.closeableForms = false;
  $rootScope.closedForms = false;
  console.log("$rootScope.instanceName == ", $rootScope.instanceName);
  downloadService.getJson('/awac/translations/available/' + $rootScope.instanceName, function(result) {
    if (result.success) {
      if (result.data.frEnabled) {
        $rootScope.languages.push({
          value: 'fr',
          label: 'Français'
        });
      }
      if (result.data.nlEnabled) {
        $rootScope.languages.push({
          value: 'nl',
          label: 'Neederlands'
        });
      }
      if (result.data.enEnabled) {
        $rootScope.languages.push({
          value: 'en',
          label: 'English'
        });
      }
      translationService.initialize($rootScope.languages[0].value);
      return $rootScope.language = $rootScope.languages[0].value;
    }
  });
  $rootScope.$watch('language', function(lang) {
    if (lang) {
      translationService.initialize(lang);
      return tmhDynamicLocale.set(lang.toLowerCase());
    }
  });
  $rootScope.getRegisterKey = function() {
    return $rootScope.key;
  };
  $rootScope.isLogin = function() {
    return $location.path().substring(0, 6) === "/login";
  };
  $rootScope.hideHeader = function() {
    return $location.path().substring(0, 6) === "/login" || $location.path().substring(0, 13) === "/registration" || $location.path().substring(0, 26) === "/verification_registration";
  };
  $rootScope.logout = function() {
    return $rootScope.nav('/logout');
  };
  $rootScope.testForm = function(period, scope) {
    var periodToFind, site, _i, _j, _k, _len, _len2, _len3, _ref, _ref2, _ref3;
    if ($rootScope.mySites != null) {
      _ref = $rootScope.mySites;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        site = _ref[_i];
        if (parseFloat(site.id) === parseFloat(scope)) {
          if ($rootScope.instanceName === 'enterprise') {
            _ref2 = site.listPeriodAvailable;
            for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
              periodToFind = _ref2[_j];
              if (period + "" === periodToFind.key + "") {
                return true;
              }
            }
          } else {
            _ref3 = $rootScope.periods;
            for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
              periodToFind = _ref3[_k];
              if (period + "" === periodToFind.key + "") {
                return true;
              }
            }
          }
        }
      }
    }
    return false;
  };
  $rootScope.loginSuccess = function(data, skipRedirect) {
    var periodSelectedKey, scope, scopeSelectedId, _i, _j, _len, _len2, _ref, _ref2;
    console.log("DATA connection : ");
    console.log(angular.copy(data));
    $rootScope.periods = data.availablePeriods;
    $rootScope.currentPerson = data.person;
    $rootScope.organizationName = data.organizationName;
    $rootScope.mySites = data.myScopes;
    if (!!data.defaultSiteId && !!data.defaultPeriod) {
      if ($rootScope.testForm(data.defaultSiteId, data.defaultPeriod) === true) {
        scopeSelectedId = data.defaultSiteId;
        periodSelectedKey = data.defaultPeriod;
      }
    } else if (data.defaultSiteId != null) {
      _ref = $rootScope.mySites;
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        scope = _ref[_i];
        if (scope.id === data.defaultSiteId) {
          scopeSelectedId = data.defaultSiteId;
        }
      }
    }
    if (!(scopeSelectedId != null)) {
      if ($rootScope.mySites.length > 0) {
        scopeSelectedId = $rootScope.mySites[0].id;
      }
    }
    if ((scopeSelectedId != null) && !(periodSelectedKey != null)) {
      _ref2 = $rootScope.mySites;
      for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
        scope = _ref2[_j];
        if (scope.id === scopeSelectedId) {
          if ($rootScope.instanceName === 'enterprise' || $rootScope.instanceName === 'event') {
            if ((scope.listPeriodAvailable != null) && scope.listPeriodAvailable.length > 0) {
              periodSelectedKey = scope.listPeriodAvailable[0].key;
            }
          } else {
            periodSelectedKey = $rootScope.periods[0].key;
          }
        }
      }
    }
    $rootScope.scopeSelectedId = scopeSelectedId;
    $rootScope.periodSelectedKey = periodSelectedKey;
    if ($rootScope.scopeSelectedId != null) {
      $rootScope.computeAvailablePeriod($rootScope.mySites[0].scope);
    }
    if (!skipRedirect) {
      return $rootScope.toDefaultForm();
    }
  };
  $rootScope.toDefaultForm = function() {
    return $rootScope.nav($rootScope.getDefaultRoute());
  };
  $rootScope.$watch("mySites", function() {
    return $rootScope.computeAvailablePeriod();
  });
  $rootScope.computeAvailablePeriod = function(scopeId) {
    var currentPeriodFound, currentPeriodFounded, period, site, _i, _j, _k, _l, _len, _len2, _len3, _len4, _ref, _ref2, _ref3, _ref4;
    if ($rootScope.instanceName === 'enterprise') {
      if (!(scopeId != null)) {
        scopeId = $rootScope.scopeSelectedId;
      }
      if (scopeId != null) {
        _ref = $rootScope.mySites;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          site = _ref[_i];
          if (site.scope === scopeId) {
            $rootScope.availablePeriods = site.listPeriodAvailable;
          }
        }
        currentPeriodFounded = false;
        if ($rootScope.availablePeriods != null) {
          _ref2 = $rootScope.availablePeriods;
          for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
            period = _ref2[_j];
            if (period.key === $rootScope.periodSelectedKey) {
              currentPeriodFounded = true;
            }
          }
          if (!currentPeriodFounded) {
            if ($rootScope.availablePeriods.length > 0) {
              return $rootScope.periodSelectedKey = $rootScope.availablePeriods[0].key;
            }
          }
        } else {
          $rootScope.availablePeriods = null;
          return $location.path("noScope");
        }
      }
    } else if ($rootScope.instanceName === 'event') {
      if (!(scopeId != null)) {
        scopeId = $rootScope.scopeSelectedId;
      }
      if (scopeId != null) {
        _ref3 = $rootScope.mySites;
        for (_k = 0, _len3 = _ref3.length; _k < _len3; _k++) {
          site = _ref3[_k];
          if (site.id === scopeId) {
            $rootScope.availablePeriods = site.listPeriodAvailable;
          }
        }
        currentPeriodFound = false;
        if ($rootScope.availablePeriods != null) {
          _ref4 = $rootScope.availablePeriods;
          for (_l = 0, _len4 = _ref4.length; _l < _len4; _l++) {
            period = _ref4[_l];
            if (period.key === $rootScope.periodSelectedKey) {
              currentPeriodFound = true;
            }
          }
          if (!currentPeriodFound) {
            if ($rootScope.availablePeriods.length > 0) {
              return $rootScope.periodSelectedKey = $rootScope.availablePeriods[0].key;
            }
          }
        } else {
          $rootScope.availablePeriods = null;
          return $location.path("noScope");
        }
      }
    } else {
      return $rootScope.availablePeriods = $rootScope.periods;
    }
  };
  $rootScope.getUserByIdentifier = function(identifier) {
    var user, _i, _len, _ref;
    _ref = $rootScope.users;
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      user = _ref[_i];
      if (user.identifier === identifier) {
        return user;
      }
    }
    return null;
  };
  $rootScope.refreshUserData = function() {
    return downloadService.getJson('/awac/user/profile', function(result) {
      if (result.success) {
        return $rootScope.currentPerson = result.data;
      }
    });
  };
  $rootScope.refreshNotifications = function() {
    downloadService.getJson('/awac/notifications/get_notifications', function(result) {
      var n, _i, _len, _ref, _results;
      if (result.success) {
        _ref = result.data.notifications;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          n = _ref[_i];
          _results.push(messageFlash.display(n.kind.toLowerCase(), n.messageFr, {
            hideAfter: 3600
          }));
        }
        return _results;
      }
    });
    return $timeout($rootScope.refreshNotifications, 3600 * 1000);
  };
  $rootScope.refreshNotifications();
  $rootScope.$on("$routeChangeSuccess", function(event, current, previous) {
    if ($routeParams.period != null) {
      $rootScope.periodSelectedKey = $routeParams.period;
    }
    if ($routeParams.scope != null) {
      return $rootScope.scopeSelectedId = parseInt($routeParams.scope);
    }
  });
  $rootScope.navToLastFormUsed = function() {
    if ($rootScope.getFormPath != null) {
      return $rootScope.nav($rootScope.getFormPath());
    } else {
      return $rootScope.nav($rootScope.getDefaultRoute());
    }
  };
  $rootScope.nav = function(loc, confirmed) {
    var canBeContinue, params, result, route, routeWithScopeAndPeriod, _base, _i, _len, _ref;
    if (confirmed == null) {
      confirmed = false;
    }
    console.log("NAV : " + loc);
    canBeContinue = true;
    if (($rootScope.getMainScope() != null) && $rootScope.getMainScope().validNavigation !== void 0 && confirmed === false) {
      result = typeof $rootScope.getMainScope === "function" ? typeof (_base = $rootScope.getMainScope()).validNavigation === "function" ? _base.validNavigation() : void 0 : void 0;
      if (result.valid === false) {
        canBeContinue = false;
        params = {};
        params.loc = loc;
        modalService.show(result.modalForConfirm, params);
      }
    }
    if (canBeContinue) {
      if ((((_ref = $rootScope.getMainScope()) != null ? _ref.formIdentifier : void 0) != null) && ($rootScope.currentPerson != null) && !!$rootScope.lastPeriodSelectedKey && !!$rootScope.lastScopeSelectedId && !!$rootScope.lastFormIdentifier) {
        downloadService.getJson("/awac/answer/unlockForm/" + $rootScope.lastFormIdentifier + "/" + $rootScope.lastPeriodSelectedKey + "/" + $rootScope.lastScopeSelectedId, function(result) {});
      }
      routeWithScopeAndPeriod = ['/form', '/results', '/actions'];
      for (_i = 0, _len = routeWithScopeAndPeriod.length; _i < _len; _i++) {
        route = routeWithScopeAndPeriod[_i];
        if (loc.substring(0, route.length) === route) {
          if (!$rootScope.periodSelectedKey || !$rootScope.scopeSelectedId) {
            $location.path('/noScope');
          } else {
            $location.path(loc + "/" + $rootScope.periodSelectedKey + "/" + $rootScope.scopeSelectedId);
          }
          return;
        }
      }
      $location.path(loc);
      return;
    }
  };
  $rootScope.getMainScope = function() {
    var mainScope;
    return mainScope = angular.element($('[ng-view]')[0]).scope();
  };
  $rootScope.testCloseable = function() {
    if ($rootScope.instanceName !== 'verification') {
      if (!!$rootScope.periodSelectedKey && !!$rootScope.scopeSelectedId) {
        return downloadService.getJson("/awac/answer/testClosing/" + $rootScope.periodSelectedKey + "/" + $rootScope.scopeSelectedId, function(result) {
          if (result.success) {
            $rootScope.closeableForms = result.data.closeable;
            $rootScope.closedForms = result.data.closed;
            return $rootScope.verificationRequest = result.data.verificationRequest;
          }
        });
      }
    }
  };
  $rootScope.closeForms = function() {
    if (($rootScope.periodSelectedKey != null) && ($rootScope.scopeSelectedId != null)) {
      return modalService.show(modalService.CONFIRM_CLOSING);
    }
  };
  $rootScope.showHelp = function() {
    if ($route.current.locals.helpPage != null) {
      return modalService.show(modalService.HELP, {
        template: $route.current.locals.helpPage
      });
    }
  };
  $rootScope.showConfidentiality = function() {
    return modalService.show(modalService.HELP, {
      template: 'confidentiality'
    });
  };
  $rootScope.getVerificationRequestStatus = function() {
    var _ref;
    return (_ref = $rootScope.verificationRequest) != null ? _ref.status : void 0;
  };
  return $rootScope.$on('PROMISE', function(event, uuid) {
    console.log(uuid);
    return setTimeout(function() {
      return downloadService.getJson("/awac/promises/" + uuid);
    }, 2000);
  });
});angular.module('app.controllers').controller("AdminAdvicesManagerCtrl", function($scope, displayLittleFormMenu, downloadService, modalService, messageFlash, translationService, codeLabelHelper) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.actionAdvices = [];
  $scope.typeCodeLabels = [];
  $scope.baseIndicatorCodeLabels = [];
  $scope.interfaceTypeCodeLabels = [];
  $scope.loadActionAdvices = function() {
    return downloadService.getJson("awac/admin/advices/load", function(result) {
      var codeLists;
      if (result.success) {
        $scope.actionAdvices = result.data.reducingActionAdvices;
        codeLists = result.data.codeLists;
        $scope.typeCodeLabels = _.findWhere(codeLists, {
          code: 'REDUCING_ACTION_TYPE'
        }).codeLabels;
        $scope.baseIndicatorCodeLabels = _.findWhere(codeLists, {
          code: 'BASE_INDICATOR'
        }).codeLabels;
        return $scope.interfaceTypeCodeLabels = _.findWhere(codeLists, {
          code: 'INTERFACE_TYPE'
        }).codeLabels;
      }
    });
  };
  $scope.createActionAdvice = function() {
    var params;
    params = {
      typeCodeLabels: $scope.typeCodeLabels,
      baseIndicatorCodeLabels: $scope.baseIndicatorCodeLabels,
      interfaceTypeCodeLabels: $scope.interfaceTypeCodeLabels,
      cb: $scope.loadActionAdvices
    };
    return modalService.show(modalService.CREATE_OR_EDIT_REDUCTION_ACTION_ADVICE, params);
  };
  $scope.updateActionAdvice = function(actionAdvice) {
    var params;
    params = {
      typeCodeLabels: $scope.typeCodeLabels,
      baseIndicatorCodeLabels: $scope.baseIndicatorCodeLabels,
      interfaceTypeCodeLabels: $scope.interfaceTypeCodeLabels,
      actionAdvice: actionAdvice
    };
    return modalService.show(modalService.CREATE_OR_EDIT_REDUCTION_ACTION_ADVICE, params);
  };
  $scope.deleteActionAdvice = function(actionAdvice) {
    $scope.isLoading = true;
    return downloadService.postJson("awac/admin/advices/delete", actionAdvice, function(result) {
      var e, index, _ref, _results;
      $scope.isLoading = false;
      if (result.success) {
        messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
        _ref = $scope.actionAdvices;
        _results = [];
        for (index in _ref) {
          e = _ref[index];
          if (e.id === actionAdvice.id) {
            $scope.actionAdvices.splice(index, 1);
            break;
          }
        }
        return _results;
      }
    });
  };
  $scope.confirmDelete = function(action) {
    var params;
    params = {
      titleKey: "REDUCING_ACTION_ADVICE_DELETE_CONFIRMATION_TITLE",
      messageKey: "REDUCING_ACTION_ADVICE_DELETE_CONFIRMATION_MESSAGE",
      onConfirm: function() {
        return $scope.deleteActionAdvice(action);
      }
    };
    return modalService.show(modalService.CONFIRM_DIALOG, params);
  };
  $scope.exportActionAdvicesToXls = function() {
    return $window.open('/awac/admin/advices/exportToXls', translationService.get('RESULT_DOWNLOAD_START', null));
  };
  $scope.getTypeLabel = function(typeKey) {
    return codeLabelHelper.getLabelByKey($scope.typeCodeLabels, typeKey);
  };
  $scope.getBaseIndicatorLabel = function(baseIndicatorKey) {
    return baseIndicatorKey + " - " + codeLabelHelper.getLabelByKey($scope.baseIndicatorCodeLabels, baseIndicatorKey);
  };
  $scope.getInterfaceTypeLabel = function(interfaceTypeKey) {
    return codeLabelHelper.getLabelByKey($scope.interfaceTypeCodeLabels, interfaceTypeKey);
  };
  return $scope.loadActionAdvices();
});angular.module('app.controllers').controller("AdminTranslationInterfaceCtrl", function($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper, displayLittleFormMenu, $location) {
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.codeLabelsByList = {};
  $scope.codeLabelsGroups = [];
  $scope.waitingData = true;
  $scope.isLoading = false;
  $scope.sortableOptions = {
    axis: 'y',
    stop: function(e, ui) {
      var index, _results;
      _results = [];
      for (index in $scope.subList.items) {
        _results.push($scope.subList.items[index].orderIndex = +index);
      }
      return _results;
    }
  };
  $scope.loadCodeLabels = function() {
    downloadService.getJson("/awac/admin/translations/codelabels/load/TRANSLATIONS_INTERFACE", function(result) {
      var codeLabels, codeLabelsByTopic, topic, topicCodeLabels;
      if (result.success) {
        $scope.codeLabelsByList = result.data.codeLabelsByList;
        codeLabels = codeLabelHelper.sortCodeLabelsByOrder($scope.codeLabelsByList["TRANSLATIONS_INTERFACE"]);
        codeLabelsByTopic = _.groupBy(codeLabels, function(codeLabel) {
          return codeLabel.topic || "";
        });
        for (topic in codeLabelsByTopic) {
          topicCodeLabels = codeLabelsByTopic[topic];
          $scope.codeLabelsGroups.push({
            topic: topic,
            codeLabels: topicCodeLabels
          });
        }
        $scope.initialCodeLabelsGroups = angular.copy($scope.codeLabelsGroups);
      }
      $scope.waitingData = false;
      return;
    });
    return;
  };
  $scope.save = function() {
    var codeLabels, codeLabelsGroup, data, i, _ref;
    $scope.isLoading = true;
    codeLabels = [];
    _ref = $scope.codeLabelsGroups;
    for (i in _ref) {
      codeLabelsGroup = _ref[i];
      codeLabels = codeLabels.concat(codeLabelsGroup.codeLabels);
    }
    $scope.codeLabelsByList["TRANSLATIONS_INTERFACE"] = codeLabels;
    data = {
      codeLabelsByList: $scope.codeLabelsByList
    };
    return downloadService.postJson("/awac/admin/translations/codelabels/save", data, function(result) {
      $scope.isLoading = false;
      if (result.success) {
        messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
        $scope.loadCodeLabels();
        return translationService.reinitialize();
      }
    });
  };
  $scope.ignoreChanges = false;
  $scope.$root.$on('$locationChangeStart', function(event, next, current) {
    var eq, params;
    if (!!$scope.ignoreChanges) {
      return;
    }
    eq = angular.equals($scope.initialCodeLabelsGroups, $scope.codeLabelsGroups);
    if (!eq) {
      event.preventDefault();
      params = {
        titleKey: "CONFIRM_EXIT_TITLE",
        messageKey: "CONFIRM_EXIT_MESSAGE",
        onConfirm: function() {
          $scope.ignoreChanges = true;
          return $location.path(next.split('#')[1]);
        }
      };
      return modalService.show(modalService.CONFIRM_DIALOG, params);
    }
  });
  return $scope.loadCodeLabels();
});angular.module('app.controllers').controller("RegistrationCtrl", function($scope, downloadService, messageFlash, $compile, $timeout, translationService, $routeParams) {
  downloadService.postJson('/awac/logout', null, function(result) {});
  $scope.loading = false;
  $scope.tabActive = [];
  $scope.enterEvent = function() {
    if ($scope.tabActive[0] === true) {
      return $scope.send();
    }
  };
  $scope.fields = {
    loginInfo: {
      fieldTitle: "LOGIN_FORM_LOGIN_FIELD_TITLE",
      fieldType: "text",
      placeholder: "LOGIN_FORM_LOGIN_FIELD_PLACEHOLDER",
      validationRegex: "^\\S{5,20}$",
      validationMessage: "LOGIN_VALIDATION_WRONG_LENGTH",
      field: "",
      isValid: false,
      focus: function() {
        return true;
      }
    },
    passwordInfo: {
      fieldTitle: "LOGIN_FORM_PASSWORD_FIELD_TITLE",
      fieldType: "password",
      validationRegex: "^\\S{5,20}$",
      validationMessage: "PASSWORD_VALIDATION_WRONG_LENGTH",
      field: "",
      isValid: false
    },
    lastNameInfo: {
      fieldTitle: "USER_LASTNAME",
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_LASTNAME_WRONG_LENGTH",
      isValid: true
    },
    firstNameInfo: {
      fieldTitle: "USER_FIRSTNAME",
      fieldType: "text",
      validationRegex: "^.{1,255}$",
      validationMessage: "USER_FIRSTNAME_WRONG_LENGTH",
      isValid: true
    },
    emailInfo: {
      fieldTitle: "USER_EMAIL",
      validationRegex: "[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?",
      validationMessage: "EMAIL_VALIDATION_WRONG_FORMAT"
    }
  };
  $scope.connectionFieldValid = function() {
    var field, key, _i, _len, _ref;
    _ref = Object.keys($scope.fields);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      key = _ref[_i];
      if (key !== '$$hashKey') {
        field = $scope.fields[key];
        if (!field.isValid || field.isValid === false) {
          return false;
        }
      }
    }
    return true;
  };
  $scope.send = function() {
    var data, personDTO;
    $scope.isLoading = true;
    personDTO = {
      identifier: $scope.fields.loginInfo.field,
      lastName: $scope.fields.lastNameInfo.field,
      firstName: $scope.fields.firstNameInfo.field,
      email: $scope.fields.emailInfo.field
    };
    data = {
      person: personDTO,
      password: $scope.fields.passwordInfo.field,
      key: $routeParams.key
    };
    downloadService.postJson('/awac/register', data, function(result) {
      if (result.success) {
        messageFlash.displaySuccess(translationService.get('REGISTRATION_SUCCESS'));
        $scope.isLoading = false;
        console.log('registration');
        return $scope.$root.nav('/login');
      } else {
        messageFlash.displaySuccess(translationService.get('REGISTRATION_FAILED'));
        return $scope.isLoading = false;
      }
    });
    return false;
  };
  $scope.injectRegistrationDirective = function() {
    var directive, directiveName;
    if ($scope.$root != null) {
      if ($scope.$root.instanceName === 'enterprise') {
        directiveName = "mm-awac-registration-enterprise";
      } else if ($scope.$root.instanceName === 'municipality') {
        directiveName = "mm-awac-registration-municipality";
      }
      directive = $compile("<" + directiveName + "></" + directiveName + ">")($scope);
      return $('.inject-registration-form').append(directive);
    }
  };
  return $timeout(function() {
    return $scope.injectRegistrationDirective();
  }, 0);
});angular.module('app.controllers').controller("SiteManagerCtrl", function($scope, translationService, modalService, downloadService, messageFlash) {
  $scope.isLoading = {};
  if ($scope.$root.periods != null) {
    $scope.assignPeriod = $scope.$root.periods[0].key;
  } else {
    $scope.$watch('$root.periods', function() {
      return $scope.assignPeriod = $scope.$root.periods[0].key;
    });
  }
  $scope.isPeriodChecked = {};
  $scope.selectedPeriodForEvent = $scope.$root.periods[0].key;
  modalService.show(modalService.LOADING);
  return downloadService.getJson('awac/organization/getMyOrganization', function(result) {
    if (!result.success) {
      return modalService.close(modalService.LOADING);
    } else {
      modalService.close(modalService.LOADING);
      $scope.organization = result.data;
      $scope.$watchCollection('assignPeriod', function() {
        return $scope.refreshPeriod();
      });
      $scope.refreshPeriod = function() {
        var site, _i, _len, _ref, _results;
        _ref = $scope.organization.sites;
        _results = [];
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          site = _ref[_i];
          _results.push($scope.isPeriodChecked[site.id] = $scope.periodAssignTo(site));
        }
        return _results;
      };
      $scope.toForm = function() {
        return $scope.$root.navToLastFormUsed();
      };
      $scope.getSiteList = function() {
        return $scope.organization.sites;
      };
      $scope.editOrCreateSite = function(site) {
        var params;
        params = {};
        if (site != null) {
          params.site = site;
        }
        params.organization = $scope.organization;
        params.refreshMySites = function() {
          $scope.refreshMySites();
          return $scope.refreshPeriod();
        };
        return modalService.show(modalService.EDIT_SITE, params);
      };
      $scope.addUsers = function(site) {
        var params;
        params = {};
        if (site != null) {
          params.site = site;
        }
        params.organization = $scope.organization;
        return modalService.show(modalService.ADD_USER_SITE, params);
      };
      $scope.periodAssignTo = function(site) {
        var period, _i, _len, _ref;
        if (site.listPeriodAvailable != null) {
          _ref = site.listPeriodAvailable;
          for (_i = 0, _len = _ref.length; _i < _len; _i++) {
            period = _ref[_i];
            if (period.key === $scope.assignPeriod) {
              return true;
            }
          }
        }
        return false;
      };
      $scope.assignPeriodToSite = function(site) {
        var data;
        $scope.isLoading[site.id] = true;
        data = {};
        data.periodKeyCode = $scope.assignPeriod;
        data.siteId = site.id;
        data.assign = !$scope.periodAssignTo(site);
        return downloadService.postJson('awac/site/assignPeriodToSite', data, function(result) {
          $scope.isLoading[site.id] = false;
          if (result.success) {
            site.listPeriodAvailable = result.data.periodsList;
            return $scope.refreshMySites();
          }
        });
      };
      return $scope.refreshMySites = function() {
        var mySites, person, site, _i, _j, _len, _len2, _ref, _ref2;
        mySites = [];
        _ref = $scope.organization.sites;
        for (_i = 0, _len = _ref.length; _i < _len; _i++) {
          site = _ref[_i];
          if (site.listPersons != null) {
            _ref2 = site.listPersons;
            for (_j = 0, _len2 = _ref2.length; _j < _len2; _j++) {
              person = _ref2[_j];
              if (person.identifier === $scope.$root.currentPerson.identifier) {
                mySites.push(site);
              }
            }
          }
        }
        console.log("voilà tes nouveaux sites : ");
        console.log($scope.$root.mySites);
        return $scope.$root.mySites = mySites;
      };
    }
  });
});angular.module('app.controllers').controller("AdminTranslationLinkedListCtrl", function($scope, $compile, downloadService, modalService, messageFlash, translationService, codeLabelHelper, displayLittleFormMenu, $location) {
  var updateOrderIndexes;
  $scope.displayLittleFormMenu = displayLittleFormMenu;
  $scope.linkedLists = [];
  $scope.activitySourcesLabels = [];
  $scope.activityTypesLabels = [];
  $scope.waitingData = true;
  $scope.isLoading = false;
  $scope.itemToAdd = {
    key: "",
    labelEn: "",
    labelFr: "",
    labelNl: "",
    activitySourceKey: "",
    activityTypeKey: "",
    clear: function() {
      this.key = "";
      this.labelEn = "";
      this.labelFr = "";
      this.labelNl = "";
      this.activitySourceKey = "";
      this.activityTypeKey = "";
      return;
    }
  };
  $scope.sortableOptions = {
    axis: 'y'
  };
  $scope.loadLinkedLists = function() {
    downloadService.getJson("awac/admin/translations/linkedlists/load", function(result) {
      var codeLabels;
      if (result.success) {
        $scope.linkedLists = $scope.sortLinkedListsItems(result.data.linkedLists);
        $scope.initialLinkedLists = angular.copy($scope.linkedLists);
        codeLabels = result.data.codeLabels;
        $scope.activitySourcesLabels = codeLabelHelper.sortCodeLabelsByOrder(_.findWhere(codeLabels, {
          code: 'ActivitySource'
        }).codeLabels);
        $scope.activityTypesLabels = codeLabelHelper.sortCodeLabelsByOrder(_.findWhere(codeLabels, {
          code: 'ActivityType'
        }).codeLabels);
      }
      $scope.waitingData = false;
      return;
    });
    return;
  };
  updateOrderIndexes = function(linkedLists) {
    var index, item, linkedList, _i, _len, _ref;
    for (_i = 0, _len = linkedLists.length; _i < _len; _i++) {
      linkedList = linkedLists[_i];
      _ref = linkedList.items;
      for (index in _ref) {
        item = _ref[index];
        item.orderIndex = +index + 1;
      }
    }
    return linkedLists;
  };
  $scope.save = function() {
    var data;
    $scope.isLoading = true;
    data = {
      linkedLists: updateOrderIndexes($scope.linkedLists)
    };
    downloadService.postJson("awac/admin/translations/linkedlists/save", data, function(result) {
      $scope.isLoading = false;
      if (result.success) {
        messageFlash.displaySuccess(translationService.get("CHANGES_SAVED"));
        $scope.loadLinkedLists();
        translationService.reinitialize();
      }
      return;
    });
    return;
  };
  $scope.sortLinkedListsItems = function(linkedLists) {
    var linkedList, _i, _len;
    for (_i = 0, _len = linkedLists.length; _i < _len; _i++) {
      linkedList = linkedLists[_i];
      linkedList.items = _.sortBy(linkedList.items, function(item) {
        return item.orderIndex;
      });
    }
    return linkedLists;
  };
  $scope.addItem = function(linkedList) {
    if (!(!!$scope.itemToAdd.key && !!$scope.itemToAdd.labelEn && !!$scope.itemToAdd.activitySourceKey && !!$scope.itemToAdd.activityTypeKey)) {
      messageFlash.displayError("Echec de l'ajout: les champs 'clé', 'Libellé EN', 'ActivitySource' et 'ActivityType' sont obligatoires!");
      return;
    }
    if (!!_.findWhere(linkedList.items, {
      'key': $scope.itemToAdd.key
    })) {
      messageFlash.displayError("Echec de l'ajout: la clé '" + $scope.itemToAdd.key + "' est déjà présente dans cette liste!");
      return;
    }
    if (!!_.findWhere(linkedList.items, {
      'activitySourceKey': $scope.itemToAdd.activitySourceKey,
      'activityTypeKey': $scope.itemToAdd.activityTypeKey
    })) {
      messageFlash.displayError("Echec de l'ajout: un élément avec la même combinaison ActivityType - ActivitySource est déjà présent dans cette liste!");
      return;
    }
    linkedList.items.push(angular.copy($scope.itemToAdd));
    $scope.itemToAdd.clear();
    return;
  };
  $scope.getActivitySourceLabel = function(key) {
    return key + " - " + codeLabelHelper.getLabelByKey($scope.activitySourcesLabels, key);
  };
  $scope.getActivityTypeLabel = function(key) {
    return key + " - " + codeLabelHelper.getLabelByKey($scope.activityTypesLabels, key);
  };
  $scope.ignoreChanges = false;
  $scope.$root.$on('$locationChangeStart', function(event, next, current) {
    var eq, params;
    if (!!$scope.ignoreChanges) {
      return;
    }
    eq = angular.equals($scope.initialLinkedLists, updateOrderIndexes($scope.linkedLists));
    if (!eq) {
      event.preventDefault();
      params = {
        titleKey: "CONFIRM_EXIT_TITLE",
        messageKey: "CONFIRM_EXIT_MESSAGE",
        onConfirm: function() {
          $scope.ignoreChanges = true;
          return $location.path(next.split('#')[1]);
        }
      };
      return modalService.show(modalService.CONFIRM_DIALOG, params);
    }
  });
  return $scope.loadLinkedLists();
});angular.module('app.directives').run(function($templateCache) {$templateCache.put('$/angular/views/site_manager.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1 ng-bind-html=\"'SITE_MANAGER_BUTTON' | translateText\"></h1><div class=\"site_manager\"><h4 ng-bind-html=\"'SITE_MANAGER_SITE_LIST_TITLE' | translateText\"></h4><div class=\"desc\" ng-bind-html=\"'SITE_MANAGER_SITE_LIST_DESC' | translateText\"></div><table class=\"site_table\"><tr class=\"site_table_header\"><td ng-bind-html=\"'SITE_MANAGER_EDIT_SITE_BUTTON' | translateText\"></td><td ng-bind-html=\"'NAME' | translateText\"></td><td ng-bind-html=\"'DESCRIPTION' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_NACE_CODE' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_ORGANIZATIONAL_STRUCTURE' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_PERCENT_OWNED' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_USERS_BUTTON' | translateText\"></td><td><select ng-model=\"assignPeriod\" ng-options=\"p.key as p.label for p in $root.periods\"></select></td></tr><tr ng-repeat=\"site in getSiteList()\"><td><button ng-click=\"editOrCreateSite(site)\" type=\"button\" title=\"{{'SITE_MANAGER_EDIT_SITE_BUTTON' | translateText}}\" class=\"edit_icon glyphicon glyphicon-pencil\"></button></td><td>{{site.name}}</td><td>{{site.description}}</td><td>{{site.naceCode}}</td><td>{{site.organizationalStructure | translateText }}</td><td>{{site.percentOwned}} %</td><td><button ng-click=\"addUsers(site)\" type=\"button\" title=\"{{'SITE_MANAGER_ADD_USERS_BUTTON' | translateText}}\" class=\"edit_icon glyphicon glyphicon-pencil\"></button></td><td><input ng-model=\"isPeriodChecked[site.id]\" ng-click=\"assignPeriodToSite(site)\" type=\"checkbox\" ng-hide=\"isLoading[site.id]=== true\"><img ng-show=\"isLoading[site.id]=== true\" src=\"/assets/images/modal-loading.gif\"></td></tr></table><button class=\"button add\" ng-click=\"editOrCreateSite()\" ng-bind-html=\"'SITE_MANAGER_ADD_SITE_BUTTON' | translateText\" type=\"button\"></button></div></div>");$templateCache.put('$/angular/views/user_registration.html', "<div class=\"loginBackground\"><div class=\"router-bar\"><div class=\"awac_logo\"></div></div><div class=\"registrationFrame\" ng-enter=\"enterEvent()\"><select ng-model=\"$root.language\" ng-options=\"l.value as l.label for l in $root.languages\" style=\"float:right\"></select><tabset><tab class=\"tab-color-lightgreen\" active=\"tabActive[0]\"><tab-heading><span ng-bind-html=\"'USER_REGISTRATION' | translate\"></span></tab-heading><div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.emailInfo\"></mm-awac-modal-field-text></div><div ng-hide=\"isLoading === true\"><button class=\"button btn btn-primary\" ng-click=\"send()\" ng-bind-html=\"'USER_REGISTER_BUTTON' | translate\" type=\"button\" ng-disabled=\"!connectionFieldValid()\"></button></div><img ng-show=\"isLoading === true\" src=\"/assets/images/modal-loading.gif\"></div></tab></tabset></div></div>");$templateCache.put('$/angular/views/user_manager.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1 ng-bind-html=\"'USER_MANAGER_TITLE' | translate\"></h1><div class=\"element\"><button class=\"button add\" ng-show=\"true\" ng-click=\"inviteUser()\" ng-bind-html=\"'USER_MANAGER_INVIT_USER' | translate\" type=\"button\"></button><table class=\"user_table\"><tr class=\"user_table_header\"><td ng-bind-html=\"'NAME' | translate\"></td><td ng-bind-html=\"'USER_MANAGER_ADMINISTRATOR' | translate\" ng-hide=\"$root.instanceName==='verification'\"></td><td ng-show=\"$root.instanceName==='verification'\" ng-bind-html=\"'STATUS' | translate\"></td><td ng-bind-html=\"'USER_MANAGER_ACTIF' | translate\"></td></tr><tr ng-class=\"{user_deleted : user.isActive === false}\" ng-repeat=\"user in getUserList()\"><td>{{user.firstName}} {{user.lastName}} ({{user.email}})</td><td ng-hide=\"$root.instanceName==='verification'\"><input ng-model=\"user.isAdmin\" ng-click=\"isAdminUser(user)\" type=\"checkbox\" ng-hide=\"isLoading['admin'][user.email]=== true\" ng-disabled=\"getMyself().isAdmin === false || getMyself().email === user.email || user.isActive == false\"><img ng-show=\"isLoading['admin'][user.email] === true\" src=\"/assets/images/modal-loading.gif\"></td><td ng-show=\"$root.instanceName==='verification'\"><select ng-change=\"changeStatusForVerification(user)\" ng-model=\"user.tempStatus\" ng-options=\"l.id as l.label | translateText for l in statusesForVerification\" ng-hide=\"isLoading['admin'][user.email]=== true\" ng-disabled=\"getMyself().isAdmin === false || getMyself().email === user.email || user.isActive == false\"></select><img ng-show=\"isLoading['admin'][user.email] === true\" src=\"/assets/images/modal-loading.gif\"></td><td><input ng-model=\"user.isActive\" ng-click=\"activeUser(user)\" type=\"checkbox\" ng-hide=\"isLoading['isActive'][user.email]=== true\" ng-disabled=\"getMyself().isAdmin === false || getMyself().email === user.email\"><img ng-show=\"isLoading['isActive'][user.email] === true\" src=\"/assets/images/modal-loading.gif\"></td></tr></table></div></div>");$templateCache.put('$/angular/views/verification/help_archive_fr.html', "<h1>Accès vérificateur sur les calculateurs de l'AWAC: aide sur l'archive</h1><p>Cette interface vous permet simplement de garder une trace des demandes de vérification qui se sont achevées avec succès, ainsi que de celles qui ont été annulées en cours de route par le demandeur</p>");$templateCache.put('$/angular/views/verification/verification.html', "<div class=\"verification\"><div class=\"menu-verification\"><div class=\"list-request\"><table class=\"admin-table-import-bad table\" ng-table=\"tableParams\"><tr ng-click=\"$parent.selectRequest(request)\" ng-class=\"{'selected': $parent.requestSelected.id == request.id}\" ng-repeat=\"request in $data\"><td data-title=\"'ORGANIZATION' | translateText\">{{request.organizationCustomer.name}}</td><td data-title=\"'CALCULATOR_TYPE'| translateText\" sortable=\"'organizationCustomer.interfaceName'\">{{request.organizationCustomer.interfaceName}}</td><td data-title=\"'SITE_PRODUCT'| translateText\" sortable=\"'scope.name'\">{{request.scope.name}}</td><td data-title=\"'SITE_MANAGER_EVENT_PERIOD'| translateText\" sortable=\"'period.label'\">{{request.period.label}}</td><td data-title=\"'CONTACT'| translateText\" sortable=\"'contact.lastName'\">{{request.contact.firstName}} {{request.contact.lastName}} {{request.contactPhoneNumber}}</td><td data-title=\"'COMMENT'| translateText\">{{request.comment}}</td><td data-title=\"'STATUS'| translateText\" sortable=\"'status'\">{{request.status | translateText}}</td></tr></table></div></div><div class=\"data_menu\" ng-hide=\"requestSelected === null\"><div class=\"data_date_compare\"><button class=\"button verification\" ng-click=\"consultEvent()\" ng-bind-html=\"'CONSULT_ORGANIZATION_EVENT' | translate\" type=\"button\"></button></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><div ng-bind-html=\"'SURVEY_INTERFACE_COMPARE_TO' | translate\"></div><select ng-model=\"$root.periodToCompare\" ng-options=\"p.key as p.label for p in periodsForComparison\"></select></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><button class=\"button verification\" ng-click=\"isDisabled || finalizeVerification()\" ng-bind-html=\"'FINALIZE_VERIFICATION_BUTTON' | translate\" type=\"button\" ng-disabled=\"verificationFinalization.finalized != true\"></button></div></div><div class=\"injectFormMenu\"></div><div class=\"injectForm\"></div></div>");$templateCache.put('$/angular/views/verification/manage.html', "<div class=\"verification_manage\"><div class=\"my-share-table\" loading-container=\"tableParams.settings().$loading\"><div class=\"verification\"><table class=\"admin-table-import-bad table\" ng-table=\"tableParams\"><tr ng-repeat=\"request in $data\"><td data-title=\"'ORGANIZATION' | translateText\">{{request.organizationCustomer.name}}</td><td data-title=\"'CALCULATOR_TYPE'| translateText\" sortable=\"'organizationCustomer.interfaceName'\">{{request.organizationCustomer.interfaceName}}</td><td data-title=\"'SITE_PRODUCT'| translateText\" sortable=\"'scope.name'\">{{request.scope.name}}</td><td data-title=\"'SITE_MANAGER_EVENT_PERIOD'| translateText\" sortable=\"'period.label'\">{{request.period.label}}</td><td data-title=\"'CONTACT'| translateText\" sortable=\"'contact.lastName'\">{{request.contact.firstName}} {{request.contact.lastName}} {{request.contactPhoneNumber}} <br />({{request.contact.email}})</td><td data-title=\"'COMMENT'| translateText\">{{request.comment}}</td><td data-title=\"'STATUS'| translateText\" sortable=\"'status'\">{{request.status | translateText}}</td><td data-title=\"'VERIFICATION_ASSIGNED_USERS'| translateText\" sortable=\"'request.type'\"><div ng-repeat=\"user in request.verifierList\">{{user.firstName}} {{user.lastName}}</div></td><td data-title=\"'MODAL_DOCUMENT_MANAGER_ACTION'| translateText\"><div ng-show=\"loadingRequest[request.id] === true\"><img src=\"/assets/images/modal-loading.gif\"></div><div ng-hide=\"loadingRequest[request.id] === true\"><button class=\"button verification\" ng-show=\"request.status === 'VERIFICATION_STATUS_WAIT_VERIFIER_CONFIRMATION'\" ng-click=\"changeStatus(request,'VERIFICATION_STATUS_WAIT_CUSTOMER_CONFIRMATION')\" ng-bind-html=\"'VERIFICATION_REQUEST_ACCEPT' | translate\" type=\"button\"></button><button class=\"button verification\" ng-show=\"request.status === 'VERIFICATION_STATUS_WAIT_VERIFIER_CONFIRMATION'\" ng-click=\"changeStatus(request,'VERIFICATION_STATUS_REJECTED')\" ng-bind-html=\"'VERIFICATION_REQUEST_REJECT' | translate\" type=\"button\"></button><button class=\"button verification\" ng-show=\"request.status === 'VERIFICATION_STATUS_WAIT_ASSIGNATION' || request.status === 'VERIFICATION_STATUS_VERIFICATION'\" ng-click=\"assignRequest(request)\" ng-bind-html=\"'VERIFICATION_REQUEST_ASSIGN' | translate\" type=\"button\"></button></div></td></tr></table></div><div><div class=\"desc_block\" ng-bind-html=\"'ADD_REQUEST_WITH_KEY_DESC'| translate\"></div><span ng-bind-html=\"'ADD_REQUEST_WITH_KEY' | translate\"></span><input ng-model=\"keyToInject\"><button class=\"button\" ng-click=\"isDisabled || addKey()\" ng-bind-html=\"'SUBMIT' | translate\" type=\"button\" ng-disabled=\"keyToInject==null || keyToInject.length == 0\"></button></div></div></div>");$templateCache.put('$/angular/views/verification/help_submit_fr.html', "<h1>Accès vérificateur sur les calculateurs de l'AWAC: aide sur la soumission</h1><p>Cette interface permet aux <b>vérificateurs principaux</b> de relire le travail effectué par leurs collègues dans \"Vérifier\" et de décider alors de faire suivre ou non au demandeur.</p><img src=\"/assets/images/help/verification_soumettre.png\"><p>Une fois un dossier sélectionné dans la première table, toutes les données afférentes à celui-ci apparaissent, comme dans l'interface \"Vérifier\".</p><p>Mais, ici, le vérificateur principal ne peut que relire le travail de ses collègues, en regardant les statuts de vérification qui ont été assignés à chaque rubrique. En cas de statut négatif, cliquer sur le bouton orange sur fond rouge permet de relire le commentaire associé au rejet de ces données.</p><p>Le bouton \"Consulter le commentaire\" permet de relire le commentaire final de la vérification lorsque celle-ci s'est conclue de manière négative. En cas de vérification positive, ce bouton est remplacé par \"Consulter le rapport de vérification\", qui permet de lire le rapport final qu'il est prévu de fournir au demandeur.</p><p>Pour conclure cette étape, le vérificateur principal décide alors s'il accepte ou non la vérification qui a été effectuée.</p><p>S'il l'accepte, elle est retournée au demandeur.</p><p>S'il la refuse, elle revient chez ses collègues dans \"Vérifier\" pour qu'ils y re-travaillent.</p>");$templateCache.put('$/angular/views/verification/help_user_manager_fr.html', "<h1>Accès vérificateur sur les calculateurs de l'AWAC: aide sur la gestion des utilisateurs</h1><h2>Introduction</h2><p>Accessible aux seuls utilisateurs avec un statut <b>administrateur</b>, cette interface permet de gérer l'ensemble des utilisateurs de l'organisation, en déterminant qui est administrateur et en invitant de nouveaux utilisateurs ou en en désactivant certains.</p><h2>Octroyer le statut administrateur</h2><p>Pour octroyer le statut d'administrateur, il suffit de cocher la case à cet effet à côté de son nom.</p><p><em>Note</em>: un administrateur ne peut jamais se défaire lui-même de son statut: un autre administrateur doit le faire pour lui, ce qui permet de s'assurer qu'il reste toujours un administrateur associé à l'organisation.</p><h2>Statut de vérificateur principal</h2><p>Seuls des vérificateurs avec le statut de vérificateur principal ont le droit d'utiliser l'interface \"Soumettre\" pour renvoyer des résultats de vérification aux demandeurs.</p><p>Pour octroyer ce statut à un utilisateur, il suffit de choisir le bon statut à côté de son nom.</p><p><em>Note</em>: un administrateur est d'office vérificateur principal</p><h2>Inviter un nouveau utilisateur sur le compte vérificateur</h2><img src=\"/assets/images/help/inviter_utilisateur.png\"><p>Un clic sur ce bouton ouvre une fenêtre dans laquelle il suffit de mentionner l'adresse courriel de l'utilisateur à inviter: il reçoit alors un courriel avec un lien pour s'enregistrer.</p><h2>Désactiver un utilisateur</h2><p>Pour désactiver un utilisateur-vérificateur, il suffit de décocher la case \"Actif\" à côté de son nom. Dès ce moment, l'utilisateur ne saura plus se connecter à l'application avec son identifiant. Aucune des données fournies par cet utilisateur n'est perdue. Elles restent accessibles aux autres utilisateurs, avec mention claire de ce qu'elles ont été fournies par leur ancien collègue.</p><img src=\"/assets/images/help/verificateurs_actif.png\"><p><em>Note</em>: un administrateur ne peut jamais se désactiver lui-même: un autre administrateur doit le faire pour lui, ce qui permet de s'assurer qu'il reste toujours un administrateur associé à l'organisation.</p><p><em>Note 2</em>: Un utilisateur inactif peut être re-rendu actif à tout moment grâce au bouton vert qui est apparu à côté de son nom.</p>");$templateCache.put('$/angular/views/verification/help_verification_fr.html', "<h1>Accès vérificateur sur les calculateurs de l'AWAC: aide sur la vérification</h1><p>Tout vérificateur assigné à un travail de vérification voit apparaître les tâches dans la table du dessus. Lorsqu'il clique sur une des lignes, l'entièreté des données à vérifier apparaît en dessous.</p><img src=\"/assets/images/help/verification_verification.png\"><p>Pour chaque rubrique de données, le vérificateur n'a que deux choix: les accepter, ou les refuser, en cliquant sur les symboles appropriés.</p><p>Chaque refus nécessite un commentaire d'explication qui sera fourni au demandeur.</p><p>Lorsque toutes les rubriques ont été vérifiées, il est alors possible de clôturer la vérification en cliquant sur la bouton \"Finaliser\": le système demandera alors soit un commentaire final en cas de problèmes, soit de joindre le document de rapport final de vérification. Cela sera alors envoyé aux vérificateurs principaux qui pourront faire suivre le tout au demandeur dans l'interface \"Soumettre\".</p>");$templateCache.put('$/angular/views/verification/confidentiality_fr.html', "<h1>Accès vérificateur sur les calculateurs de l'AWAC: gestion de la confidentialité de vos données</h1><h2>Introduction</h2><p>L'AWAC ne se considère a aucun moment être le propriétaire des données fournies via l'outil. Tant vous que les autres utilisateurs restez chaque fois les propriétaires des données que vous fournissez. L'AWAC en le gardien, et n'en fera aucun usage hormis ceux décrits ci-dessous.</p><h2>Protection de la vie privée</h2><p>Si vous nous fournissez des informations personnelles, elles seront traitées conformément aux dispositions de la loi du 8 décembre 1992. Les autres données seront traitées, moyennant votre accord (voir plus bas),  anonymement à des fins statistiques et pour vous adresser des messages électroniques généraux; elles ne seront pas communiquées à des tiers, à l’exception du sous-traitant avec lequel l’AWAC a conclu une convention relative à la confidentialité des données, ni utilisées à des fins commerciales. L’AWAC s'engage à prendre les meilleures mesures de sécurité afin d'éviter que des tiers n'abusent des données à caractère personnel qui lui ont été communiquées.</p><h2>Confidentialité de vos données</h2><p>Afin de garantir cette confidentialité, divers mécanismes techniques sont utilisés: la connexion au service s’établit par protocole https, avec certificat TSL/SSL1, à l’instar de ce qui se fait pour les transactions financières électroniques, et non pas simple protocole http. De plus, aucun mot de passe utilisateur ne circule en clair: ils sont systématiquement tous encryptés et seules les versions cryptées sont mémorisées.</p><h2>Usage statistique de vos données</h2><p>Un tel usage prend place uniquement si vous avez explicitement marqué votre accord, soit lors de la création de votre compte, soit à tout moment via l'interface de \"gérer l'organisation\" en activant le choix \"Permettre à l’AWAC d’inclure mes données dans son analyse statistique\".</p><p>Les données saisies en ligne et les résultats associés sont traités collectivement et anonymement. Ces données et résultats pourront alors être exploités, en totalité ou en partie, en vue de réalisations statistiques, de réalisations de rapports ou d’études relatives à la consommation d’énergie et d’émission de gaz à effet de serre.</p>");$templateCache.put('$/angular/views/verification/help_user_data_fr.html', "<h1>Accès vérificateur sur les calculateurs de l'AWAC: aide sur le profil individuel</h1><p>Cette interface de gestion permet à chaque utilisateur, individuellement, de gérer son profil.</p><p>Il peut ainsi y modifier ses données personnelles (prénom, nonm et adresse courriel) ainsi que le mot de passe utilisés pour se connecter à l'application. L'identifiant n'est pas modifiable.</p>");$templateCache.put('$/angular/views/verification/archive.html', "<div class=\"verification_manage\"><div class=\"my-share-table\" loading-container=\"tableParams.settings().$loading\"><div class=\"verification\"><table class=\"admin-table-import-bad table\" ng-table=\"tableParams\"><tr ng-repeat=\"request in $data\"><td data-title=\"'ORGANIZATION' | translateText\">{{request.organizationCustomer.name}}</td><td data-title=\"'CALCULATOR_TYPE'| translateText\" sortable=\"'organizationCustomer.interfaceName'\">{{request.organizationCustomer.interfaceName}}</td><td data-title=\"'SITE_PRODUCT'| translateText\" sortable=\"'scope.name'\">{{request.scope.name}}</td><td data-title=\"'SITE_MANAGER_EVENT_PERIOD'| translateText\" sortable=\"'period.label'\">{{request.period.label}}</td><td data-title=\"'CONTACT'| translateText\" sortable=\"'contact.lastName'\">{{request.contact.firstName}} {{request.contact.lastName}} {{request.contactPhoneNumber}}</td><td data-title=\"'COMMENT'| translateText\">{{request.comment}}</td><td data-title=\"'FINALIZED'| translateText\"><span ng-show=\"request.status === 'VERIFICATION_STATUS_VERIFIED'\" ng-bind-html=\"'YES' | translate\"></span><span ng-bind-html=\"'NO' | translate\" ng-hide=\"request.status === 'VERIFICATION_STATUS_VERIFIED'\"></span></td><td data-title=\"'VERIFICATION_ASSIGNED_USERS'| translateText\" sortable=\"'request.type'\"><div ng-repeat=\"user in request.verifierList\">{{user.firstName}} {{user.lastName}}</div></td><td data-title=\"'MODAL_DOCUMENT_MANAGER_ACTION'| translateText\"><button class=\"button verification\" ng-show=\"request.status === 'VERIFICATION_STATUS_VERIFIED'\" ng-click=\"downloadReport(request)\" ng-bind-html=\"'DOWNLOAD_VERIFICATION_REPORT' | translate\" type=\"button\"></button></td></tr></table></div></div></div>");$templateCache.put('$/angular/views/verification/agreement_en.html', "<h1>Je suis l'agreement verification EN</h1>");$templateCache.put('$/angular/views/verification/help_organization_manager_fr.html', "<h1>Accès vérificateur sur les calculateurs de l'AWAC: aide sur la gestion de l'organisation</h1><p>Vous pouvez changer ici le nom global de votre organisation tel qu'il apparaît tout en haut du calculateur, et dans vos échanges de courriels avec les personnes dont vosu vérifiez le bilan.</p>");$templateCache.put('$/angular/views/verification/help_manage_fr.html', "<h1>Accès vérificateur sur les calculateurs de l'AWAC: aide sur la gestion</h1><p>L'interface gérer permet aux <b>administrateurs</b> du compte de voir les demandes en cours ou d'en encoder des nouvelles via la clé reçue par courriel.</p><p>Pour chaque nouvelle demande, il est possible de l'accepter ou de la refuser. Les statuts évolueront au fur et à mesure des échanges avec le demandeur: ces échanges sont sytématiquement tracés par courriel.</p><img src=\"/assets/images/help/verification_manage.png\"><p>Il est également possible d'encoder en direct une clé de demande de vérification reçue par un collègue non inscrit dans l'outil.</p><p>Une fois une demande acceptée, il faut l'assigner à un ou plusieurs vérificateurs qui auront alors accès aux données dans leur partie \"Vérifier\".</p><img src=\"/assets/images/help/verification_assign.png\">");$templateCache.put('$/angular/views/verification/submit.html', "<div class=\"verification\"><div class=\"menu-verification\"><div class=\"list-request\"><table class=\"admin-table-import-bad table\" ng-table=\"tableParams\"><tr ng-click=\"$parent.selectRequest(request)\" ng-class=\"{'selected': $parent.requestSelected.id == request.id}\" ng-repeat=\"request in $data\"><td data-title=\"'ORGANIZATION' | translateText\">{{request.organizationCustomer.name}}</td><td data-title=\"'CALCULATOR_TYPE'| translateText\" sortable=\"'organizationCustomer.interfaceName'\">{{request.organizationCustomer.interfaceName}}</td><td data-title=\"'SITE_PRODUCT'| translateText\" sortable=\"'scope.name'\">{{request.scope.name}}</td><td data-title=\"'SITE_MANAGER_EVENT_PERIOD'| translateText\" sortable=\"'period.label'\">{{request.period.label}}</td><td data-title=\"'CONTACT'| translateText\" sortable=\"'contact.lastName'\">{{request.contact.firstName}} {{request.contact.lastName}} {{request.contactPhoneNumber}}</td><td data-title=\"'COMMENT'| translateText\">{{request.comment}}</td><td data-title=\"'STATUS'| translateText\" sortable=\"'status'\">{{request.status | translateText}}</td></tr></table></div></div><div class=\"data_menu\" ng-hide=\"requestSelected === null\"><div class=\"data_date_compare\"><button class=\"button verification\" ng-click=\"consultEvent()\" ng-bind-html=\"'CONSULT_ORGANIZATION_EVENT' | translate\" type=\"button\"></button></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><div ng-bind-html=\"'SURVEY_INTERFACE_COMPARE_TO' | translate\"></div><select ng-model=\"$root.periodToCompare\" ng-options=\"p.key as p.label for p in periodsForComparison\"></select></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><button class=\"button verification\" ng-click=\"isDisabled || validVerificationFinalization(true)\" ng-bind-html=\"'VERIFICATION_SUBMIT_ACCEPT' | translate\" type=\"button\"></button><button class=\"button verification\" ng-click=\"isDisabled || validVerificationFinalization(false)\" ng-bind-html=\"'VERIFICATION_SUBMIT_REJECT' | translate\" type=\"button\"></button><button class=\"button verification\" ng-show=\"selectedRequest.status == 'VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_SUCCESS' \" ng-click=\"isDisabled || downloadFile()\" ng-bind-html=\"'DOWNLOAD_VERIFICATION_REPORT' | translate\" type=\"button\"></button><button class=\"button verification\" ng-show=\"selectedRequest.status == 'VERIFICATION_STATUS_WAIT_VERIFICATION_CONFIRMATION_REJECT' \" ng-click=\"isDisabled || consultFinalComment()\" ng-bind-html=\"'VERIFICATION_FINALIZATION_CONSULT_COMMENT' | translate\" type=\"button\"></button></div></div><div class=\"injectFormMenu\"></div><div class=\"injectForm\"></div></div>");$templateCache.put('$/angular/views/verification/agreement_fr.html', "<h1>Mentions légales de l'accès vérificateur sur les calculateurs CO2 de l'AWAC</h1><h2> Information juridique - Conditions d'utilisation du site</h2><p>L'utilisation du présent site est soumise au respect des conditions générales décrites ci-après. En accédant à ce site, vous déclarez avoir pris connaissance et avoir accepté, sans la moindre réserve, ces conditions générales d'utilisation.</p><h2>Qualité de l'information et du service – Clause de non-responsabilité</h2><p>Nous apportons le plus grand soin à la gestion de ce site. Nous ne garantissons toutefois pas l'exactitude des informations qui y sont proposées. Celles-ci sont par ailleurs susceptibles d'être modifiées sans avis préalable. Il en résulte que nous déclinons toute responsabilité quant à l'utilisation qui pourrait être faite du contenu de ce site. Les liens hypertextes présents sur le site et aiguillant les utilisateurs vers d'autres sites Internet n'engagent pas notre responsabilité quant au contenu de ces sites.</p><h2>Protection des données à caractère personnel</h2><p>Il vous est loisible de visiter notre site internet sans nous fournir de données à caractère personnel (1). Vous pouvez accéder à la base de calcul et de résultats en ne saisissant que des données générales à caractère non personnel.</p><p>Si vous nous fournissez des informations personnelles (2), elles seront traitées conformément aux dispositions de la loi du 8 décembre 1992. Elles seront traitées, moyennant votre accord,  anonymement à des fins statistiques et pour vous adresser des messages électroniques généraux; elles ne seront pas communiquées à des tiers, à l’exception du sous-traitant avec lequel l’AWAC a conclu une convention relative à la confidentialité des données, ni utilisées à des fins commerciales.  L’AWAC s'engage à prendre les meilleures mesures de sécurité afin d'éviter que des tiers n'abusent des données à caractère personnel qui lui ont été communiquées.</p><p>Les données saisies en ligne et les résultats associés sont traités collectivement et anonymement. Ces données et résultats pourront être exploités, en totalité ou en partie, en vue de réalisations statistiques, de réalisations de rapports ou d’études relatives à la consommation d’énergie et d’émission de gaz à effet de serre.</p><h2>Droits de propriété intellectuelle</h2><p>Tous droits sur le contenu et l'architecture de notre site, et notamment les éléments suivants : textes, créations graphiques, logos, éléments sonores, publications, mises en page, bases de données, algorithmes de calculs... sont protégés par le droit national et international de la propriété intellectuelle.</p><p>A ce titre, vous ne pouvez procéder à une quelconque reproduction, représentation, adaptation, traduction et/ou transformation partielle ou intégrale, ou un transfert sur un autre site web de tout élément composant le site.</p><p>Cependant si vous souhaitez utiliser les éléments disponibles sur notre site, nous vous invitons à adresser votre demande d’autorisation à l’AWAC en précisant les éléments à reproduire et l’utilisation souhaitée.</p><h2>Création d'hyperliens vers le site</h2><p>Nous autorisons la création sans demande préalable de liens en surface (surface linking) qui renvoient à la page d'accueil du site ou à toute autre page dans sa globalité. Par contre, le recours à toutes techniques visant à inclure tout ou partie du site dans un site Internet en masquant ne serait-ce que partiellement l'origine exacte de l'information ou pouvant prêter à confusion sur l'origine de l'information, telle que le framing ou l'inlining, requiert une autorisation écrite.</p><h2>Modification des mentions légales</h2><p>L’AWAC se réserve le droit de modifier les présentes mentions à tout moment. L’utilisateur est lié par les conditions en vigueur lors de sa visite.</p><h2>Droit applicable en cas de litige</h2><p>Le droit applicable est le droit belge.</p><p>Tout litige relèvera de la compétence exclusive des tribunaux de Namur.</p><h3>Personne de Contact</h3><p>Cécile Batungwnanayo</p><p>Agence wallone de l'air et du Climat (AWAC)</p><p>7, Avenue Prince de Liège</p><p>B-5100 Jambes</p><P>Belgium</P><h2>Notes</h2><p>(1) La loi du 8 décembre 1992 relative au traitement de données à caractère personnel entend par « données à caractère personnel », toute information concernant une personne physique identifiée ou identifiable […] ; est réputée identifiable une personne qui peut être identifiée, directement ou indirectement, notamment par référence à un numéro d’identification ou à un plusieurs éléments spécifiques, propres à son identité physique, physiologique, psychique, économique, culturelle ou sociale. »</p><p>(2) Pour les personnes physiques, via la création d’un espace personnel sur le site avec votre e-mail et un mot de passe.  Si votre adresse e-mail est constituée de votre nom de famille, elle peut permettre votre identification.</p>");$templateCache.put('$/angular/views/event/TAB_EV4.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AEV94\"><mm-awac-question ng-optional=\"true\" question-code=\"AEV95\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AEV96\"><mm-awac-sub-title question-code=\"AEV97\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AEV98\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AEV98\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AEV98')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV99\" ng-map-index=\"{'AEV98':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV100\" ng-map-index=\"{'AEV98':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV101\" ng-map-index=\"{'AEV98':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV102\" ng-map-index=\"{'AEV98':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV103\" ng-map-index=\"{'AEV98':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV104\" ng-map-index=\"{'AEV98':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV105\" ng-map-index=\"{'AEV98':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AEV98\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/event/TAB_EV1.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AEV2\"><mm-awac-question question-code=\"AEV3\"></mm-awac-question><mm-awac-question question-code=\"AEV4\" ng-condition=\"getAnswer('AEV3').value == '4'\"></mm-awac-question><mm-awac-question question-code=\"AEV5\"></mm-awac-question><mm-awac-question question-code=\"AEV6\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AEV6').value | nullToZero)-(getAnswer('AEV5').value | nullToZero)\" question-code=\"AEV7\"></mm-awac-question><mm-awac-question question-code=\"AEV8\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AEV9\"></mm-awac-question></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/event/help_product_manager_fr.html', "<h1>Calculateur CO2 évènement de l'AWAC: aide sur la gestion des évènements</h1>");$templateCache.put('$/angular/views/event/help_user_manager_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur la gestion des utilisateurs</h1><h2>Introduction</h2><p>Accessible aux seuls utilisateurs avec un statut <b>administrateur</b>, cette interface permet de gérer l'ensemble des utilisateurs de l'organisation, en déterminant qui est administrateur et en invitant de nouveaux utilisateurs ou en en désactivant certains.</p><h2>Octroyer le statut administrateur</h2><p>Pour octroyer le statut d'administrateur, il suffit de cocher la case à cet effet à côté de son nom.</p><p><em>Note</em>: un administrateur ne peut jamais se défaire lui-même de son statut: un autre administrateur doit le faire pour lui, ce qui permet de s'assurer qu'il reste toujours un administrateur associé à l'organisation.</p><h2>Inviter un nouvel utilisateur</h2><img src=\"/assets/images/help/inviter_utilisateur.png\"><p>Un clic sur ce bouton ouvre une fenêtre dans laquelle il suffit de mentionner l'adresse courriel de l'utilisateur à inviter: il reçoit alors un courriel avec un lien pour s'enregistrer.</p><p><em>Note</em>: dès que l'utilisateur se sera enregistré, il ne faudra pas oublier de l'associer à des sites dans l'interface de gestion des sites.</p><h2>Désactiver un utilisateur</h2><p>Pour désactiver un utilisateur, il suffit de décocher la case \"Actif\" à côté de son nom. Dès ce moment, l'utilisateur ne saura plus se connecter à l'application avec son identifiant. Aucune des données fournies par cet utilisateur n'est perdue. Elles restent accessibles aux autres utilisateurs, avec mention claire de ce qu'elles ont été fournies par leur ancien collègue.</p><img src=\"/assets/images/help/utilisateurs_actif.png\"><p><em>Note</em>: un administrateur ne peut jamais se désactiver lui-même: un autre administrateur doit le faire pour lui, ce qui permet de s'assurer qu'il reste toujours un administrateur associé à l'organisation.</p><p><em>Note 2</em>: Un utilisateur inactif peut être re-rendu actif à tout moment grâce au bouton vert qui est apparu à côté de son nom.</p>");$templateCache.put('$/angular/views/event/confidentiality_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: gestion de la confidentialité de vos données</h1><h2>Introduction</h2><p>L'AWAC ne se considère a aucun moment être le propriétaire des données fournies via l'outil. Tant vous que les autres utilisateurs restez chaque fois les propriétaires des données que vous fournissez. L'AWAC en le gardien, et n'en fera aucun usage hormis ceux décrits ci-dessous.</p><h2>Protection de la vie privée</h2><p>Si vous nous fournissez des informations personnelles, elles seront traitées conformément aux dispositions de la loi du 8 décembre 1992. Les autres données seront traitées, moyennant votre accord (voir plus bas),  anonymement à des fins statistiques et pour vous adresser des messages électroniques généraux; elles ne seront pas communiquées à des tiers, à l’exception du sous-traitant avec lequel l’AWAC a conclu une convention relative à la confidentialité des données, ni utilisées à des fins commerciales. L’AWAC s'engage à prendre les meilleures mesures de sécurité afin d'éviter que des tiers n'abusent des données à caractère personnel qui lui ont été communiquées.</p><h2>Confidentialité de vos données</h2><p>Afin de garantir cette confidentialité, divers mécanismes techniques sont utilisés: la connexion au service s’établit par protocole https, avec certificat TSL/SSL1, à l’instar de ce qui se fait pour les transactions financières électroniques, et non pas simple protocole http. De plus, aucun mot de passe utilisateur ne circule en clair: ils sont systématiquement tous encryptés et seules les versions cryptées sont mémorisées.</p><h2>Usage statistique de vos données</h2><p>Un tel usage prend place uniquement si vous avez explicitement marqué votre accord, soit lors de la création de votre compte, soit à tout moment via l'interface de \"gérer l'organisation\" en activant le choix \"Permettre à l’AWAC d’inclure mes données dans son analyse statistique\".</p><p>Les données saisies en ligne et les résultats associés sont traités collectivement et anonymement. Ces données et résultats pourront alors être exploités, en totalité ou en partie, en vue de réalisations statistiques, de réalisations de rapports ou d’études relatives à la consommation d’énergie et d’émission de gaz à effet de serre.</p>");$templateCache.put('$/angular/views/event/help_results_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur la présentation des résultats</h1><h2>Introduction</h2><p>La partie Résultats vous présente le bilan GES de votre organisation, selon vos critères et présenté de différentes manières. Petit tour d'horizon des options qui s'ouvrent à vous.</p><h2>Choisir les sites</h2><p>Une première option est que vous pouvez choisir l'ensemble des sites dont les données seront consolidées ensemble (au prorata des poucentages à prendre en compte définis dans la partie gestion des sites) et sommées pour réaliser le résultat final.</p><img src=\"/assets/images/help/resultats_entreprise_main.png\"><h2>Résultat annuel ou comparaison</h2><p>Une seconde option vous permet de choisir si souhaitez voir le résultat pour une année seulement, ou au contraire comparer le résultat de deux années différentes, comme illustré ci-dessous.</p><img src=\"/assets/images/help/resultats_entreprise_comparaison.png\"><h2>Export du résultat</h2><p>Deux boutons, disponibles juste au-dessus des différentes manières de visualiser le résultat, vous permettent de télécharger celui-ci au format Excel ou PDF sur votre ordinateur. Dans chaque cas, après avoir cliqué sur le bouton, vous devrez patienter une bonne dizaine de secondes, le temps que le serveur assemble le document pour vous. Ensuite, votre navigateur sauvegardera le ficher demandé selon son mécanisme habituel (endroit habituel, avec ou sans pop-up, selon la configuration de votre navigateur).</p><p>Le ficher Excel contient juste le résultat brut et les données saisies, triées dans des cases différentes pour vous permettre de les re-travailler à votre guise et/ou de les mettre en page différement.</p><p>Le ficher PDF contient les données saisies, mais également tous les graphiques de présentation du résultat.</p><h2>Différentes vues</h2><p>Le résultat est présenté de différentes manières, chacune accesssible via les onglets sur le bord droit du navigateur. Voici les explications de l'objectif de chaque mode de présentation.</p><h3>Valeurs par rubrique</h3><p>Cette vue présente l'ensemble des rubriques du résultat sous forme de \"bar-chart\" (diagramme par bâtons), afin de mettre en évidence les impacts dominants de votre bilan GES. En mode de comparaison de deux années, les rubriques de chacune des deux années sont présentées côte à côte dans deux couleurs différentes.</p><h3>Répartition des impacts</h3><p>Cette vue présente les résultats scindés selon les 3 périmètres (scopes) de l'ISO 14064, dans des \"pie-charts\" (présentation en fromage). Cela permet de mettre en évidence les rubriques principales qui contribuent à chacun de ces périmètres.</p><h3>Diagramme araignée</h3><p>Cette vue est la même que celle des \"valeurs par rubrique\", mais sous forme de diagramme araignée, où chaque rubrique est représentée sur un axe différent. En cas de comparaison de deux années, cela permet de mieux visualiser les divergences entre les années.</p><h3>Chiffres</h3><p>Cette vue présente les résultats sous forme d'un tableau de chiffres.</p><h3>Comparaison à facteurs constants</h3><p>Cette vue présente à nouveau un \"bar-chart\" (diagramme par bâtons) comme les \"valeurs par rubrique\". Actif seulement en cas de comparaison de deux années, les données de l'année de comparaison ont cette fois étés multipliées par les facteurs d'émission de l'année de référence (et non pas par ceux de leur année de données, comme dans les autres vues). Cela permet de visualiser le changement dû seulement aux consommations engendrées par les données, indépendamment de tout changement de facteur d'émission.</p><h3>Explication du calcul</h3><p>Cette dernière rubrique, qui est également exportée tant en Excel qu'en PDF, vous permet de comprendre le raisonnement qui a mené au calcul du résultat qui vous est présenté. Elle vous présente chaque fois la valeur résultant du jeu de données fournies avec le facteur d'émission qui y a été appliqué, et la contribution au résultat qui en découle. Si certaines phrases apparaissent en rouge, cela signifie qu'un faceteur d'émission n'a pas été trouvé, et nous vous invitons alors à le signaler à l'AWAC.</p><h2>Résultats vérifiés de manière externe</h2><p>Si une vérification a été effectuée (cf. pages principales), le rapport de vérification, pour chacun des sites pour lequel un tel rapport existe pour l'année sélectionnée, devient disponible en dessous de la sélection des sites.</p>");$templateCache.put('$/angular/views/event/help_user_data_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur le profil individuel</h1><p>Cette interface de gestion permet à chaque utilisateur, individuellement, de gérer son profil.</p><p>Il peut ainsi y modifier ses données personnelles (prénom, nonm et adresse courriel) ainsi que le mot de passe utilisés pour se connecter à l'application. L'identifiant n'est pas modifiable.</p>");$templateCache.put('$/angular/views/event/TAB_EV2.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AEV11\"><mm-awac-question ng-optional=\"true\" question-code=\"AEV12\"></mm-awac-question><mm-awac-repetition-name question-code=\"AEV13\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AEV13\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AEV13')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV14\" ng-map-index=\"{'AEV13':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV15\" ng-map-index=\"{'AEV13':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV16\" ng-map-index=\"{'AEV13':$index}\" ng-condition=\"getAnswer('AEV15', itLevel1).value == '1' &amp;&amp; (getAnswer('AEV14', itLevel1).value == 'AS_1' || getAnswer('AEV14', itLevel1).value == 'AS_3' || getAnswer('AEV14', itLevel1).value == 'AS_376' || getAnswer('AEV14', itLevel1).value == 'AS_377' || getAnswer('AEV14', itLevel1).value == 'AS_4')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV17\" ng-map-index=\"{'AEV13':$index}\" ng-condition=\"getAnswer('AEV15', itLevel1).value == '1' &amp;&amp; (getAnswer('AEV14', itLevel1).value == 'AS_10' || getAnswer('AEV14', itLevel1).value == 'AS_11' || getAnswer('AEV14', itLevel1).value == 'AS_13')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV18\" ng-map-index=\"{'AEV13':$index}\" ng-condition=\"getAnswer('AEV15', itLevel1).value == '2'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV19\" ng-map-index=\"{'AEV13':$index}\" ng-condition=\"getAnswer('AEV15', itLevel1).value == '2' &amp;&amp; getAnswer('AEV14', itLevel1).value == 'AS_1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV20\" ng-map-index=\"{'AEV13':$index}\" ng-condition=\"getAnswer('AEV15', itLevel1).value == '2' &amp;&amp; (getAnswer('AEV14', itLevel1).value == 'AS_3' || getAnswer('AEV14', itLevel1).value == 'AS_376' || getAnswer('AEV14', itLevel1).value == 'AS_377' || getAnswer('AEV14', itLevel1).value == 'AS_4')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV21\" ng-map-index=\"{'AEV13':$index}\" ng-condition=\"getAnswer('AEV15', itLevel1).value == '2' &amp;&amp; (getAnswer('AEV14', itLevel1).value == 'AS_11' || getAnswer('AEV14', itLevel1).value == 'AS_13')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV22\" ng-map-index=\"{'AEV13':$index}\" ng-condition=\"getAnswer('AEV15', itLevel1).value == '2' &amp;&amp; getAnswer('AEV14', itLevel1).value == 'AS_10'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AEV18', itLevel1).value | nullToZero)/(getAnswer('AEV19', itLevel1).value | nullToZero)\" question-code=\"AEV23\" ng-map-index=\"{'AEV13':$index}\" ng-condition=\"getAnswer('AEV15', itLevel1).value == '2' &amp;&amp; getAnswer('AEV14', itLevel1).value == 'AS_1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AEV18', itLevel1).value | nullToZero)/(getAnswer('AEV20', itLevel1).value | nullToZero)\" question-code=\"AEV24\" ng-map-index=\"{'AEV13':$index}\" ng-condition=\"getAnswer('AEV15', itLevel1).value == '2' &amp;&amp; (getAnswer('AEV14', itLevel1).value == 'AS_3' || getAnswer('AEV14', itLevel1).value == 'AS_376' || getAnswer('AEV14', itLevel1).value == 'AS_377' || getAnswer('AEV14', itLevel1).value == 'AS_4')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AEV18', itLevel1).value | nullToZero)/(getAnswer('AEV21', itLevel1).value | nullToZero)\" question-code=\"AEV25\" ng-map-index=\"{'AEV13':$index}\" ng-condition=\"getAnswer('AEV15', itLevel1).value == '2' &amp;&amp; (getAnswer('AEV14', itLevel1).value == 'AS_11' || getAnswer('AEV14', itLevel1).value == 'AS_13')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AEV18', itLevel1).value | nullToZero)/(getAnswer('AEV22', itLevel1).value | nullToZero)\" question-code=\"AEV26\" ng-map-index=\"{'AEV13':$index}\" ng-condition=\"getAnswer('AEV15', itLevel1).value == '2' &amp;&amp; getAnswer('AEV14', itLevel1).value == 'AS_10'\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AEV13\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AEV27\"><mm-awac-question ng-optional=\"true\" question-code=\"AEV28\"></mm-awac-question><mm-awac-question question-code=\"AEV29\"></mm-awac-question><mm-awac-question question-code=\"AEV30\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AEV31\"><mm-awac-question ng-optional=\"true\" question-code=\"AEV32\"></mm-awac-question><mm-awac-question question-code=\"AEV33\"></mm-awac-question><mm-awac-question question-code=\"AEV34\" ng-condition=\"getAnswer('AEV33').value == '1'\"></mm-awac-question><mm-awac-question question-code=\"AEV35\" ng-condition=\"getAnswer('AEV33').value == '1'\"></mm-awac-question><mm-awac-question question-code=\"AEV36\" ng-condition=\"getAnswer('AEV33').value == '2'\"></mm-awac-question></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/event/help_actions_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur les actions de réduction</h1><h2>Introduction</h2><p>En vous aidant à établir votre bilan de gaz à effet de serre, l'objectif du calculateur proposé par l'AWAC est de vous aider à définir des actions visant à réduire votre impact climatique. Comment?</p><p>Sur base du bilan établi, vous pouvez déterminer les parties les plus impactantes de vos activités, et ainsi choisir de vous y prendre autrement afin de réduire votre impact. C'est ce que l'on appelle une \"action de réduction\": un objectif qui vous est propre et que vous vous fixez.</p><p>Parfois, avant que de déterminer une action de réduction, il y a lieu de bien s'assurer de son impact, et pour ce faire d'affiner les données utilisées dans le bilan. Cela s'appelle une action de \"meilleure mesure\" des données.</p><p>Afin de vous aider dans ce travail, l'AWAC a également établi certains conseils, qui sont des actions relativement génériques et partiellement pré-remplies, que vous pouvez utiliser comme base pour vous définir une action. Lorsque c'est possible, le potentiel de gain CO2 associé au conseil a été calculé automatiquement par le système sur base de vos données.</p><h2>Créer une action</h2><img src=\"/assets/images/help/bouton_ajouter_action.png\"><p>Cliquez sur ce bouton pour créer une action que vous souhaitez mettre en oeuvre.</p><p>Cela ouvre une fenêtre qui vous permet de décrire l'action que vous souhaitez mettre en oeuvre: objectif, implications financières, responsable... Afin de mieux en tracer les détails opérationnels, vous avez également la possibilité d'associer un ou plusieurs fichiers à l'action.</p><h2>Exporter les actions</h2><img src=\"/assets/images/help/bouton_export_XLS.png\"><p>A tout moment, ce bouton vous permet d'exporter en format .xls l'ensemble de toutes vos actions afin de pouvoir l'utiliser hors outil de la manière qui vous convient le mieux pour suivre et implémenter ces actions.</p><h2>Consulter et modifier une action</h2><img src=\"/assets/images/help/details_actions.png\"><p>Pour relire l'ensemble des détails liés à une action, un simple clic sur le symbole + dans la colonne \"Détails\" fournit tous les détails de l'action choisie. En bas de ces détails, trois boutons vous permettent de re-travailler vos actions.</p><h3>Editer une action</h3><p>Ce bouton vous permet de re-modifier à l'envi tous les champs descriptifs de l'action</p><h3> Indiquer une action comme réalisée</h3><p>Le bouton \"Marquer une action comme réalisée\" permet de changer instantanément son statut en \"réalisé\" lors de l'année actuelle, sans devoir passer par le mode édition.</p><h3>Supprimer une action</h3><p>Contrairement au fait de passer une action en statut \"annulé\" pour ce souvenir de ce que l'action avait été identifiée mais s'est avérée infaisable, il est possible de purement et simplement supprimer une action, qui n'apparaîtra dès lors plus nulle part.</p><h2>Transformer un conseil en action</h2><p>Cliquez simplement sur le bouton \"En faire une action\", et une action basée sur le conseil sera créée pour vous. Libre ensuite à vous de la modifier comme n'importe quelle autre action que vous gérez.</p>");$templateCache.put('$/angular/views/event/TAB_EV6.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AEV124\"><mm-awac-question ng-optional=\"true\" question-code=\"AEV125\"></mm-awac-question><mm-awac-sub-title question-code=\"AEV126\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AEV127\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AEV127\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AEV127')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV128\" ng-map-index=\"{'AEV127':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV129\" ng-map-index=\"{'AEV127':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AEV127\"></mm-awac-repetition-add-button><mm-awac-sub-title question-code=\"AEV130\"></mm-awac-sub-title><mm-awac-question question-code=\"AEV131\"></mm-awac-question><mm-awac-sub-title question-code=\"AEV132\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"AEV133\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"AEV134\"></mm-awac-question><mm-awac-question question-code=\"AEV135\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"AEV136\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"AEV137\"></mm-awac-question></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/event/agreement_en.html', "<h1>Je suis l'agreement enterprise EN</h1>");$templateCache.put('$/angular/views/event/help_organization_manager_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur la gestion de l'organisation</h1><h2>Introduction</h2><p>Accessible aux seuls utilisateurs avec un statut <b>administrateur</b>, cette interface permet de gérer trois aspects au niveau général de votre organisation: son nom, le partage des données avec l'AWAC pour un usage statitstique (cf. confidetnailité) et les facteurs infulants le bilan GES.</p><h2>Nom de l'organisation</h2><p>Vous pouvez changer ici le nom global de votre organisation tel qu'il apparaît tout en haut du calculateur.</p><h2>Partage des données avec l'AWAC</h2><p>En accord avec les principes édictés dans la confidentialité de l'outil, c'est ici que vous pouvez décider de partager ou non vos données avec l'AWAC pour alimenter la représentativitié de ses statistiques.</p><h2>Facteurs influants sur la performance GES</h2><p>Comme indiqué par le texte explicatif présent dans l'interface, vous pouvez ici décrire les changements principaux survenus telle ou telle année, et qui expliquent des variations significatives de votre bilan d'une année sur l'autre.</p><p>Cet aspect est pour votre bonne compréhension interne, mais peut également s'avérer particulièrement utile lorsque vous demandez une vérification externe de vos données: le vérificateur aura en effet accès à ces facteurs afin de mieux comprendre la nature de votre bilan.</p>");$templateCache.put('$/angular/views/event/help_form_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide générale</h1><h2>Introduction</h2><p>Le but du présent calculateur est de vous permettre de réaliser le bilan GES de votre organisation de la manière la plus simple possible tout en respectant le prescrit du GHG protocol. C'est pourquoi l'AWAC a sélectionné pour vous les bonnes questions à se poser, et pour lesquelles il vous \"suffit\" de fournir les données vous concernant afin d'obtenir un bilan GES valide. La présente page d'aide générale vous détaille d'abord l'ensemble des boutons disponibles dans les menus du haut de l'application, avant de détailler la manière de fournir les données nécessaires au calcul du bilan.</p><h2>Interface générale</h2><p>L'image ci-dessous vous présente le menu principal de l'application:</p><img src=\"/assets/images/help/entreprise_menu.png\"><p>Les annotations numérotées  correspondent aux explications ci-dessous:</p><p>1. Chaque utilisateur se connecte à l'application via son identifiant et son mot de passe. Il peut à tout moment modifier ses coordonnées personnelles via le menu \"Mon profil\".</p><p>2. Chaque utilisateur peut demander à avoir les interfaces et les questions liées à la collecte de données dans la langue de son choix parmi les langues disponibles dans l'application.</p><p>3. Le calculateur entreprise permettant de consolider le bilan de l'organisation comme la somme de bilans sur différents sites, il faut que l'utilisateur choisisse un site parmis ceux auxquels il a accès. La gestion des sites (pour les créer et y donner accès) est effectuée par les administrateurs (le créateur d'un compte est en administrateur par défaut) via le bouton \"Gérer les sites\".</p><p>4. Tout administrateur peut également \"Gérer les utilisateurs\" pour les rendre administrateurs, inviter de nouveaux utilisateurs ou en désactiver certains.</p><p>5. Et les administrateurs peuvent également modifier certains paramètres d'organisation via le bouton \"Gérer l'organisation\".</p><p>6. L'utilisateur choisit alors pour quelle année (parmis celles demandées, cf. \"Gérer les sites\") il souhaite fournir des données</p><p>7. A tout moment, il peut enregistrer les données fournies. Pour chaque formulaire thématique de données (voir plus bas), l'application indique le moment du dernier enregitrement effectué.</p><p>8. L'utilisateur a aussi la possibilité de comparer les données avec celle d'une autre année, ce qui peut facilité le recopiage des données restées similaires (voir plus bas).</p><p>9. Les administrateurs peuvent également demander une vérification externe des données (voir plus bas).</p><h2>Fournir des données</h2><p>Les données à fournir sont divisées en plusieurs parties distinctes. Vous accédez à chaque partie en cliquant sur son titre.</p><img src=\"/assets/images/help/entreprise_forms.png\"><p></p><p>La partie sélectionnée est présentée sur fond vert. Pour chaque partie, une barre de progression vous indique quelle fraction des données escomptées vous avez déjà remplie.</p><p>Tout à droite, la partie \"résultats\" vous donne accès à la présentation du bilan CO2 en fonction des données que vous avez remplies, et la partie \"actions de réduction\" vous permet de définir de telles actions pour améliorer votre bilan d'année en année.</p><h3>Types de données</h3><p>Chaque utilisateur impliqué est invité à fournir les données qu'il maîtrise. Pour ce faire, il lui suffit de remplir les cases faites pour. Plusieurs types de données différentes sont indiquées avec des icones différentes:</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-share-alt\"></span> indique des questions dont les réponses sont nécessaires au calcul du résultat.</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-paperclip\"></span> indique à l'inverse des questions qui ne servent pas au résultat mais uniquement à vous aider à mémoriser le contexte.</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-file\"></span> indique des questions documentaires qui vous permettent de stocker vos documents relatifs à certains jeux de données, afin de pouvoir les re-consulter à tout moment.</p><p><span class=\"glyphicon glyphicon-record\"></span> indique des questions regroupées en un bloc qu'il vous appartient de répliquer autant de fois que nécessaire (voir ci-dessous, création de blocs de données)</p><p></p><p>Une icone est encore présente derrière la donnée: <button class=\"button edit_comment_icon glyphicon glyphicon-pencil\"></button> Il s'agit d'un champs de commentaires libres auquel vous accéder en cliquant dessus: cela vous permet de noter des aspects compélmentaires à la donnée fournie. Lorsqu'un commentaire est effectivement présent, cette icône devient verte: <button class=\"button edit_comment_icon glyphicon glyphicon-pencil edit_comment_icon_selected\"></button></p><p>Finalement, certaines questions sont également suivies par un symbole:</p><img src=\"/assets/images/info.png\"><p>Il s'agit d'informations complémentaires sur la question qui apparaissent en infobulle lorsqu'on passe la souris sur cette icône.</p><h3>Remplissage des données</h3><p>Pour toutes les questions attendues par le système pour offrir un résultat le plus exhaustif possible, une icône sous forme de main vient donner une indication complémentaire:</p><img src=\"/assets/images/status/pending.png\"><p>indique une question attendue mais pas encore répondue;</p><img src=\"/assets/images/status/answer_temp.png\"><p>indique une question répondue mais pas encore sauvée;</p><img src=\"/assets/images/status/answer.png\"><p>indique une question répondue et dont la réponse est bien enregistrée;</p><img src=\"/assets/images/status/pending_temp.png\"><p>indique une question dont on a effacé la réponse, mais pas encore sauvegardé ce changement.</p><p>Pour remplir la donnée, il suffit d'encoder celle-ci dans la case blanche. Une fois la donnée enregistrée, une case avec des initiales indique quel utilisateur a fourni cette donnée. Si on laisse la souris dessus, le nom complet de la personne apparaît.</p><img src=\"/assets/images/help/question_repondue.png\"><h3>Création de blocs de données</h3><p>Comme énoncé plus haut, le symbole <span class=\"glyphicon glyphicon-record\"></span> indique des questions regroupées en un bloc qu'il vous appartient de répliquer autant de fois que nécessaire.</p><img src=\"/assets/images/help/bloc_donnees.png\"><p>Dans de tels cas, le bouton \"Ajouter...\" vous permet de créer autant de blocs de données identiques que nécessaire dans votre cas. A tout moment, le bouton \"Supprimer\" vous permet d'effacer un de ces blocs.</p><h3>Comparaison des données d'une autre année et recopiage d'une année à l'autre</h3><p>Dès que vous choisissez de comparer les données avec celle d'une autre année (cf. point 8 de l'introduction du menu principal), les données passent en deux colonnes pour vous permettre de comparer, donnée par donnée, les deux années.</p><img src=\"/assets/images/help/comparaison_donnees.png\"><p>Le bouton <button class=\"button\" title=\"Copier la valeur\" >&lt;&lt;</button> vous permet de recopier d'un seul clic la valeur de l'année de référence dans la case de l'année en cours.</p><p>Dans le cas de blocs de données (cf. ci-dessus), il vous faut d'abord créer le bloc pour l'année en cours, et à ce moment-là les données de l'année de référence viendront se placer à côté et seront recopiables.</p><h3>Données alternatives</h3><p>A plusieurs endroits, des méthodes différentes existent pour collecter les données nécessaires. Elles vous sont chaque fois présentées de la meilleure, en vert clair, à la plus approximative (vert foncé, puis jaune, puis orange). L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Pour le calcul du résultat, le système utilisera les données de la meilleure méthode entièrement complétée, ce qui est indiqué par le symbole <i class=\"glyphicon glyphicon-bell\"></i>.</p><img src=\"/assets/images/help/alternative.png\"><p>Dans l'exemple ci-dessus, les approches \"calcul par euros dépensés\" et \"calcul par les kilomètres\" ont été entièrement remplies, tandis que l'approche \"calcul par les consommations\" n'est pas complet. Le système indique donc qu'il utilisera les données de l'approche \"calcul par les kilomètres\". S'il n'y a pas de symbole <i class=\"glyphicon glyphicon-bell\"></i>, cela signifie qu'aucune des alternatives n'est complète et que le système ne saura pas prendre cette partie en compte dans le résultat.</p><h2>Travailler à plusieurs</h2><p>Afin de  faciliter la collecte des données nécessaires à un bilan complet, il est souvent plus efficace de pouvoir travailler à plusieurs collègues. C'est pourquoi plusieurs utilisateurs peuvent être invités sur le compte de l'organsation par le ou les administrateur(s). Il est toutefois important qu'un utilisateur n'annule pas le travail des autres. Divers mécanismes sont prévus pour cela.</p><p>Tout d'abord, il est impossible pour deux utilisateurs d'accéder en même temps à la même partie des données. Si un autre utilisateur est déjà en train de travailler sur une partie, vous recevrez un message vous y refusant l'accès en vous annonçant qui y travaille déjà.</p><p>Ensuite, deux mécanismes complémentaires permettent de gérer la collaboration.</p><h3>Se réserver des données</h3><p>Le cadenas qui figure en dessous de chaque rubrique de données permet à un utilisateur de bloquer l'ensemble des données de la rubrique pour son seul usage.</p><img src=\"/assets/images/help/localisation_cadenas.png\"><p>Seul cet utilisateur aura encore la possibilité de travailler sur ces données, tandis que les autres utilisateurs verront un cadenas fermé (le nom de l'utilisateur l'ayant fermé apparaît lorsque la souris est passée sur le cadenas).</p><p>L'utilisateur qui a verrouillé peut rouvrir le cadenas à tout moment en cliquant dessus.</p><h3>Valider des données</h3><p>En dessous du cadenas figure une autre icône dont le principe de fonctionnement est similaire.</p><img src=\"/assets/images/status/validate_not.png\"><p>Cette icône permet à un utilisateur de valider l'ensemble des données de la rubrique. C'est-à-dire d'indiquer que ces données ont été relues, sont correctes, et ne peuvent dès lors plus être modifiées. Dès lors, plus personne, cet utilisateur inclus, ne peut modifier les données concernées.</p><p><em>Note</em>: un administrateur peut toujours, à n'importe quel moment, annuler le cadenas ou la validation de n'importe quel utilisateur.</p><h2>Faire vérifier les données de manière externe</h2><p>Encore en dessous des icônes de validation, figure une icône de vérification:</p><img src=\"/assets/images/status/verified_unverified.png\"><p>Il s'agit d'indique le statut lorsque les données ont été vérifiées par une personne externe, typiquement un auditeur.</p><p>Pour pouvoir demander une vérification externe, il faut d'abord que toutes les données ait étées validées en interne (cf. ci-dessus).</p><img src=\"/assets/images/help/bouton_verification.png\"><p>Le bouton de demande de vérification devient alors vert pour indiquer qu'il est actif. Seuls les administrateurs le verront apparaître dans leur interface, et pour toute action qu'ils souhaiteront prendre eu égard à la vérification externe, ils devront chaque fois la confirmer avec leur mot de passe.</p><img src=\"/assets/images/help/demander_verification.png\"><p>Cliquer sur le bouton ouvre une fenêtre qui permet de demander la vérification à une personne externe en fournissant l'adresse de courrier électronique de la dite personne. Il appartiendra ensuite à cette personne de réagir et d'accepter le travail qui lui est demandé. Le suivi du processus se fait de manière très simple: à chaque fois qu'une action est nécessaire de la part de votre organisation, le ou les administrateur(s) du compte reçoivent un courriel leur expliquant le statut et la marche à suivre. En effet, ce bouton qui indique intialement \"Demander une vérification\" va changer de texte au fur et à mesure que le processus avance.</p><p>Tout à la fin du processus, si tout s'est bien passé, le rapport de vérification deviendra alors disponible dans la partie \"résultats\".</p>");$templateCache.put('$/angular/views/event/TAB_EV3.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AEV38\"><mm-awac-question ng-optional=\"true\" question-code=\"AEV39\"></mm-awac-question><mm-awac-question question-code=\"AEV40\"></mm-awac-question><mm-awac-question question-code=\"AEV41\"></mm-awac-question><mm-awac-sub-title question-code=\"AEV42\"></mm-awac-sub-title><mm-awac-question question-code=\"AEV43\"></mm-awac-question><mm-awac-question question-code=\"AEV47\"></mm-awac-question><mm-awac-question question-code=\"AEV44\"></mm-awac-question><mm-awac-question question-code=\"AEV45\"></mm-awac-question><mm-awac-question question-code=\"AEV46\"></mm-awac-question><mm-awac-question question-code=\"AEV48\"></mm-awac-question><mm-awac-question question-code=\"AEV49\"></mm-awac-question><mm-awac-question question-code=\"AEV50\"></mm-awac-question><mm-awac-question question-code=\"AEV51\"></mm-awac-question><mm-awac-question question-code=\"AEV52\"></mm-awac-question><mm-awac-question question-code=\"AEV53\"></mm-awac-question><mm-awac-question question-code=\"AEV54\"></mm-awac-question><mm-awac-question question-code=\"AEV55\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AEV43').value | nullToZero)+(getAnswer('AEV47').value | nullToZero)+(getAnswer('AEV48').value | nullToZero)+(getAnswer('AEV50').value | nullToZero)+(getAnswer('AEV51').value | nullToZero)+(getAnswer('AEV52').value | nullToZero)+(getAnswer('AEV53').value | nullToZero)+(getAnswer('AEV54').value | nullToZero)+(getAnswer('AEV55').value | nullToZero)\" question-code=\"AEV56\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AEV57\"><mm-awac-repetition-name question-code=\"AEV58\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AEV58\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AEV58')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV59\" ng-map-index=\"{'AEV58':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV60\" ng-map-index=\"{'AEV58':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV61\" ng-map-index=\"{'AEV58':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV62\" ng-map-index=\"{'AEV58':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV63\" ng-map-index=\"{'AEV58':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AEV58\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AEV64\"><mm-awac-question ng-optional=\"true\" question-code=\"AEV65\"></mm-awac-question><mm-awac-sub-title question-code=\"AEV66\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AEV67\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AEV67\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AEV67')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV68\" ng-map-index=\"{'AEV67':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV74\" ng-map-index=\"{'AEV67':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV76\" ng-map-index=\"{'AEV67':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV77\" ng-map-index=\"{'AEV67':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AEV67\"></mm-awac-repetition-add-button><mm-awac-sub-title question-code=\"AEV78\"></mm-awac-sub-title><mm-awac-question question-code=\"AEV79\"></mm-awac-question><mm-awac-question question-code=\"AEV80\"></mm-awac-question><mm-awac-question question-code=\"AEV81\"></mm-awac-question><mm-awac-question question-code=\"AEV82\"></mm-awac-question><mm-awac-question question-code=\"AEV83\"></mm-awac-question><mm-awac-sub-title question-code=\"AEV84\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AEV85\" ng-condition=\"getAnswer('AEV80').value == '1'\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AEV85\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AEV85')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV86\" ng-map-index=\"{'AEV85':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV87\" ng-map-index=\"{'AEV85':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV88\" ng-map-index=\"{'AEV85':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV89\" ng-map-index=\"{'AEV85':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV90\" ng-map-index=\"{'AEV85':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AEV85\"></mm-awac-repetition-add-button><mm-awac-sub-title question-code=\"AEV91\"></mm-awac-sub-title><mm-awac-question question-code=\"AEV92\"></mm-awac-question><mm-awac-question question-code=\"AEV93\"></mm-awac-question></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/event/TAB_EV5.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AEV106\"><mm-awac-question ng-optional=\"true\" question-code=\"AEV107\"></mm-awac-question><mm-awac-repetition-name question-code=\"AEV109\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AEV109\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AEV109')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV110\" ng-map-index=\"{'AEV109':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV111\" ng-map-index=\"{'AEV109':$index}\" ng-condition=\"getAnswer('AEV110', itLevel1).value != '13' &amp;&amp;&nbsp;getAnswer('AEV110', itLevel1).value != '14' &amp;&amp;&nbsp;getAnswer('AEV110', itLevel1).value != '15' &amp;&amp;&nbsp;getAnswer('AEV110', itLevel1).value != '16'\"></mm-awac-question><mm-awac-sub-title ng-repetition-map=\"itLevel1\" ng-show=\"getAnswer('AEV111', itLevel1).value == '1'\" question-code=\"AEV112\" ng-map-index=\"{'AEV109':$index}\"></mm-awac-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV113\" ng-map-index=\"{'AEV109':$index}\" ng-condition=\"getAnswer('AEV111', itLevel1).value == '1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV114\" ng-map-index=\"{'AEV109':$index}\" ng-condition=\"getAnswer('AEV111', itLevel1).value == '1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV115\" ng-map-index=\"{'AEV109':$index}\" ng-condition=\"getAnswer('AEV111', itLevel1).value == '1'\"></mm-awac-question><mm-awac-sub-title ng-repetition-map=\"itLevel1\" ng-show=\"getAnswer('AEV111, itLevel1').value == '2'\" question-code=\"AEV116\" ng-map-index=\"{'AEV109':$index}\"></mm-awac-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV117\" ng-map-index=\"{'AEV109':$index}\" ng-condition=\"getAnswer('AEV111', itLevel1).value == '2'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AEV122\" ng-map-index=\"{'AEV109':$index}\" ng-condition=\"getAnswer('AEV110', itLevel1).value == '13' ||&nbsp;getAnswer('AEV110', itLevel1).value == '14' ||&nbsp;getAnswer('AEV110', itLevel1).value == '15' ||&nbsp;getAnswer('AEV110', itLevel1).value == '16'\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AEV109\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/event/agreement_fr.html', "<h1>Mentions légales du calculateur CO2 entreprise de l'AWAC</h1><h2> Information juridique - Conditions d'utilisation du site</h2><p>L'utilisation du présent site est soumise au respect des conditions générales décrites ci-après. En accédant à ce site, vous déclarez avoir pris connaissance et avoir accepté, sans la moindre réserve, ces conditions générales d'utilisation.</p><h2>Qualité de l'information et du service – Clause de non-responsabilité</h2><p>Nous apportons le plus grand soin à la gestion de ce site. Nous ne garantissons toutefois pas l'exactitude des informations qui y sont proposées. Celles-ci sont par ailleurs susceptibles d'être modifiées sans avis préalable. Il en résulte que nous déclinons toute responsabilité quant à l'utilisation qui pourrait être faite du contenu de ce site. Les liens hypertextes présents sur le site et aiguillant les utilisateurs vers d'autres sites Internet n'engagent pas notre responsabilité quant au contenu de ces sites.</p><h2>Protection des données à caractère personnel</h2><p>Il vous est loisible de visiter notre site internet sans nous fournir de données à caractère personnel (1). Vous pouvez accéder à la base de calcul et de résultats en ne saisissant que des données générales à caractère non personnel.</p><p>Si vous nous fournissez des informations personnelles (2), elles seront traitées conformément aux dispositions de la loi du 8 décembre 1992. Elles seront traitées, moyennant votre accord,  anonymement à des fins statistiques et pour vous adresser des messages électroniques généraux; elles ne seront pas communiquées à des tiers, à l’exception du sous-traitant avec lequel l’AWAC a conclu une convention relative à la confidentialité des données, ni utilisées à des fins commerciales.  L’AWAC s'engage à prendre les meilleures mesures de sécurité afin d'éviter que des tiers n'abusent des données à caractère personnel qui lui ont été communiquées.</p><p>Les données saisies en ligne et les résultats associés sont traités collectivement et anonymement. Ces données et résultats pourront être exploités, en totalité ou en partie, en vue de réalisations statistiques, de réalisations de rapports ou d’études relatives à la consommation d’énergie et d’émission de gaz à effet de serre.</p><h2>Droits de propriété intellectuelle</h2><p>Tous droits sur le contenu et l'architecture de notre site, et notamment les éléments suivants : textes, créations graphiques, logos, éléments sonores, publications, mises en page, bases de données, algorithmes de calculs... sont protégés par le droit national et international de la propriété intellectuelle.</p><p>A ce titre, vous ne pouvez procéder à une quelconque reproduction, représentation, adaptation, traduction et/ou transformation partielle ou intégrale, ou un transfert sur un autre site web de tout élément composant le site.</p><p>Cependant si vous souhaitez utiliser les éléments disponibles sur notre site, nous vous invitons à adresser votre demande d’autorisation à l’AWAC en précisant les éléments à reproduire et l’utilisation souhaitée.</p><h2>Création d'hyperliens vers le site</h2><p>Nous autorisons la création sans demande préalable de liens en surface (surface linking) qui renvoient à la page d'accueil du site ou à toute autre page dans sa globalité. Par contre, le recours à toutes techniques visant à inclure tout ou partie du site dans un site Internet en masquant ne serait-ce que partiellement l'origine exacte de l'information ou pouvant prêter à confusion sur l'origine de l'information, telle que le framing ou l'inlining, requiert une autorisation écrite.</p><h2>Modification des mentions légales</h2><p>L’AWAC se réserve le droit de modifier les présentes mentions à tout moment. L’utilisateur est lié par les conditions en vigueur lors de sa visite.</p><h2>Droit applicable en cas de litige</h2><p>Le droit applicable est le droit belge.</p><p>Tout litige relèvera de la compétence exclusive des tribunaux de Namur.</p><h3>Personne de Contact</h3><p>Cécile Batungwnanayo</p><p>Agence wallone de l'air et du Climat (AWAC)</p><p>7, Avenue Prince de Liège</p><p>B-5100 Jambes</p><P>Belgium</P><h2>Notes</h2><p>(1) La loi du 8 décembre 1992 relative au traitement de données à caractère personnel entend par « données à caractère personnel », toute information concernant une personne physique identifiée ou identifiable […] ; est réputée identifiable une personne qui peut être identifiée, directement ou indirectement, notamment par référence à un numéro d’identification ou à un plusieurs éléments spécifiques, propres à son identité physique, physiologique, psychique, économique, culturelle ou sociale. »</p><p>(2) Pour les personnes physiques, via la création d’un espace personnel sur le site avec votre e-mail et un mot de passe.  Si votre adresse e-mail est constituée de votre nom de famille, elle peut permettre votre identification.</p>");$templateCache.put('$/angular/views/household/TAB_M5.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AM169\"><mm-awac-sub-sub-title question-code=\"AM171\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"AM172\"></mm-awac-question><mm-awac-question question-code=\"AM173\"></mm-awac-question><mm-awac-question question-code=\"AM174\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AM175\"><mm-awac-question question-code=\"AM177\"></mm-awac-question><mm-awac-question question-code=\"AM178\"></mm-awac-question><mm-awac-question question-code=\"AM179\"></mm-awac-question></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/household/TAB_M4.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AM150\"><mm-awac-question question-code=\"AM151\"></mm-awac-question><mm-awac-question question-code=\"AM152\" ng-condition=\"getAnswer('AM151').value == false\"></mm-awac-question><mm-awac-block ng-show=\"getAnswer('AM151').value == true\"><!--questions déchets--><mm-awac-repetition-name ng-show=\"getAnswer('AM151').value == true\" question-code=\"AM153\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AM153\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AM153')\" ng-condition=\"getAnswer('AM151').value == true\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM154\" ng-map-index=\"{'AM153':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM155\" ng-map-index=\"{'AM153':$index}\" ng-condition=\"getAnswer('AM154',itLevel1).value != '13' &amp;&amp; getAnswer('AM154',itLevel1).value != '14' &amp;&amp; getAnswer('AM154',itLevel1).value != '15' &amp;&amp; getAnswer('AM154',itLevel1).value != '16'\"></mm-awac-question><mm-awac-sub-sub-title ng-repetition-map=\"itLevel1\" ng-show=\"getAnswer('AM155',itLevel1).value == 1\" question-code=\"AM156\"></mm-awac-sub-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM157\" ng-map-index=\"{'AM153':$index}\" ng-condition=\"getAnswer('AM155',itLevel1).value == 1\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM158\" ng-map-index=\"{'AM153':$index}\" ng-condition=\"getAnswer('AM155',itLevel1).value == 1\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM159\" ng-map-index=\"{'AM153':$index}\" ng-condition=\"getAnswer('AM155',itLevel1).value == 1\"></mm-awac-question><mm-awac-sub-sub-title ng-repetition-map=\"itLevel1\" ng-show=\"getAnswer('AM155',itLevel1).value == 2\" question-code=\"AM160\"></mm-awac-sub-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM161\" ng-map-index=\"{'AM153':$index}\" ng-condition=\"getAnswer('AM155',itLevel1).value == 2\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM166\" ng-map-index=\"{'AM153':$index}\" ng-condition=\"getAnswer('AM154',itLevel1).value == '13' || getAnswer('AM154',itLevel1).value == '14' || getAnswer('AM154',itLevel1).value == '15' || getAnswer('AM154',itLevel1).value == '16'\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-show=\"getAnswer('AM151').value == true\" question-set-code=\"AM153\"></mm-awac-repetition-add-button></mm-awac-block></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/household/confidentiality_fr.html', "<h1>Calculateur CO2 ménages de l'AWAC: gestion de la confidentialité de vos données</h1><h2>Introduction</h2><p>L'AWAC ne se considère a aucun moment être le propriétaire des données fournies via l'outil. Tant vous que les autres utilisateurs restez chaque fois les propriétaires des données que vous fournissez. L'AWAC en le gardien, et n'en fera aucun usage hormis ceux décrits ci-dessous.</p><h2>Protection de la vie privée</h2><p>Si vous nous fournissez des informations personnelles, elles seront traitées conformément aux dispositions de la loi du 8 décembre 1992. Les autres données seront traitées, moyennant votre accord (voir plus bas),  anonymement à des fins statistiques et pour vous adresser des messages électroniques généraux; elles ne seront pas communiquées à des tiers, à l’exception du sous-traitant avec lequel l’AWAC a conclu une convention relative à la confidentialité des données, ni utilisées à des fins commerciales. L’AWAC s'engage à prendre les meilleures mesures de sécurité afin d'éviter que des tiers n'abusent des données à caractère personnel qui lui ont été communiquées.</p><h2>Confidentialité de vos données</h2><p>Afin de garantir cette confidentialité, divers mécanismes techniques sont utilisés: la connexion au service s’établit par protocole https, avec certificat TSL/SSL1, à l’instar de ce qui se fait pour les transactions financières électroniques, et non pas simple protocole http. De plus, aucun mot de passe utilisateur ne circule en clair: ils sont systématiquement tous encryptés et seules les versions cryptées sont mémorisées.</p><h2>Usage statistique de vos données</h2><p>Un tel usage prend place uniquement si vous avez explicitement marqué votre accord, soit lors de la création de votre compte, soit à tout moment via l'interface de \"gérer l'organisation\" en activant le choix \"Permettre à l’AWAC d’inclure mes données dans son analyse statistique\".</p><p>Les données saisies en ligne et les résultats associés sont traités collectivement et anonymement. Ces données et résultats pourront alors être exploités, en totalité ou en partie, en vue de réalisations statistiques, de réalisations de rapports ou d’études relatives à la consommation d’énergie et d’émission de gaz à effet de serre.</p>");$templateCache.put('$/angular/views/household/help_results_fr.html', "<h1>Calculateur CO2 ménages de l'AWAC: aide sur la présentation des résultats</h1><h2>Introduction</h2><p>La partie Résultats vous présente le bilan GES de votre organisation, selon vos critères et présenté de différentes manières. Petit tour d'horizon des options qui s'ouvrent à vous.</p><h2>Choisir les sites</h2><p>Une première option est que vous pouvez choisir l'ensemble des sites dont les données seront consolidées ensemble (au prorata des poucentages à prendre en compte définis dans la partie gestion des sites) et sommées pour réaliser le résultat final.</p><img src=\"/assets/images/help/resultats_entreprise_main.png\"><h2>Résultat annuel ou comparaison</h2><p>Une seconde option vous permet de choisir si souhaitez voir le résultat pour une année seulement, ou au contraire comparer le résultat de deux années différentes, comme illustré ci-dessous.</p><img src=\"/assets/images/help/resultats_entreprise_comparaison.png\"><h2>Export du résultat</h2><p>Deux boutons, disponibles juste au-dessus des différentes manières de visualiser le résultat, vous permettent de télécharger celui-ci au format Excel ou PDF sur votre ordinateur. Dans chaque cas, après avoir cliqué sur le bouton, vous devrez patienter une bonne dizaine de secondes, le temps que le serveur assemble le document pour vous. Ensuite, votre navigateur sauvegardera le ficher demandé selon son mécanisme habituel (endroit habituel, avec ou sans pop-up, selon la configuration de votre navigateur).</p><p>Le ficher Excel contient juste le résultat brut et les données saisies, triées dans des cases différentes pour vous permettre de les re-travailler à votre guise et/ou de les mettre en page différement.</p><p>Le ficher PDF contient les données saisies, mais également tous les graphiques de présentation du résultat.</p><h2>Différentes vues</h2><p>Le résultat est présenté de différentes manières, chacune accesssible via les onglets sur le bord droit du navigateur. Voici les explications de l'objectif de chaque mode de présentation.</p><h3>Valeurs par rubrique</h3><p>Cette vue présente l'ensemble des rubriques du résultat sous forme de \"bar-chart\" (diagramme par bâtons), afin de mettre en évidence les impacts dominants de votre bilan GES. En mode de comparaison de deux années, les rubriques de chacune des deux années sont présentées côte à côte dans deux couleurs différentes.</p><h3>Répartition des impacts</h3><p>Cette vue présente les résultats scindés selon les 3 périmètres (scopes) de l'ISO 14064, dans des \"pie-charts\" (présentation en fromage). Cela permet de mettre en évidence les rubriques principales qui contribuent à chacun de ces périmètres.</p><h3>Diagramme araignée</h3><p>Cette vue est la même que celle des \"valeurs par rubrique\", mais sous forme de diagramme araignée, où chaque rubrique est représentée sur un axe différent. En cas de comparaison de deux années, cela permet de mieux visualiser les divergences entre les années.</p><h3>Chiffres</h3><p>Cette vue présente les résultats sous forme d'un tableau de chiffres.</p><h3>Comparaison à facteurs constants</h3><p>Cette vue présente à nouveau un \"bar-chart\" (diagramme par bâtons) comme les \"valeurs par rubrique\". Actif seulement en cas de comparaison de deux années, les données de l'année de comparaison ont cette fois étés multipliées par les facteurs d'émission de l'année de référence (et non pas par ceux de leur année de données, comme dans les autres vues). Cela permet de visualiser le changement dû seulement aux consommations engendrées par les données, indépendamment de tout changement de facteur d'émission.</p><h3>Explication du calcul</h3><p>Cette dernière rubrique, qui est également exportée tant en Excel qu'en PDF, vous permet de comprendre le raisonnement qui a mené au calcul du résultat qui vous est présenté. Elle vous présente chaque fois la valeur résultant du jeu de données fournies avec le facteur d'émission qui y a été appliqué, et la contribution au résultat qui en découle. Si certaines phrases apparaissent en rouge, cela signifie qu'un faceteur d'émission n'a pas été trouvé, et nous vous invitons alors à le signaler à l'AWAC.</p><h2>Résultats vérifiés de manière externe</h2><p>Si une vérification a été effectuée (cf. pages principales), le rapport de vérification, pour chacun des sites pour lequel un tel rapport existe pour l'année sélectionnée, devient disponible en dessous de la sélection des sites.</p>");$templateCache.put('$/angular/views/household/help_user_data_fr.html', "<h1>Calculateur CO2 ménages de l'AWAC: aide sur le profil individuel</h1><p>Cette interface de gestion permet à chaque utilisateur, individuellement, de gérer son profil.</p><p>Il peut ainsi y modifier ses données personnelles (prénom, nonm et adresse courriel) ainsi que le mot de passe utilisés pour se connecter à l'application. L'identifiant n'est pas modifiable.</p>");$templateCache.put('$/angular/views/household/help_actions_fr.html', "<h1>Calculateur CO2 ménages de l'AWAC: aide sur les actions de réduction</h1><h2>Introduction</h2><p>En vous aidant à établir votre bilan de gaz à effet de serre, l'objectif du calculateur proposé par l'AWAC est de vous aider à définir des actions visant à réduire votre impact climatique. Comment?</p><p>Sur base du bilan établi, vous pouvez déterminer les parties les plus impactantes de vos activités, et ainsi choisir de vous y prendre autrement afin de réduire votre impact. C'est ce que l'on appelle une \"action de réduction\": un objectif qui vous est propre et que vous vous fixez.</p><p>Parfois, avant que de déterminer une action de réduction, il y a lieu de bien s'assurer de son impact, et pour ce faire d'affiner les données utilisées dans le bilan. Cela s'appelle une action de \"meilleure mesure\" des données.</p><p>Afin de vous aider dans ce travail, l'AWAC a également établi certains conseils, qui sont des actions relativement génériques et partiellement pré-remplies, que vous pouvez utiliser comme base pour vous définir une action. Lorsque c'est possible, le potentiel de gain CO2 associé au conseil a été calculé automatiquement par le système sur base de vos données.</p><h2>Créer une action</h2><img src=\"/assets/images/help/bouton_ajouter_action.png\"><p>Cliquez sur ce bouton pour créer une action que vous souhaitez mettre en oeuvre.</p><p>Cela ouvre une fenêtre qui vous permet de décrire l'action que vous souhaitez mettre en oeuvre: objectif, implications financières, responsable... Afin de mieux en tracer les détails opérationnels, vous avez également la possibilité d'associer un ou plusieurs fichiers à l'action.</p><h2>Exporter les actions</h2><img src=\"/assets/images/help/bouton_export_XLS.png\"><p>A tout moment, ce bouton vous permet d'exporter en format .xls l'ensemble de toutes vos actions afin de pouvoir l'utiliser hors outil de la manière qui vous convient le mieux pour suivre et implémenter ces actions.</p><h2>Consulter et modifier une action</h2><img src=\"/assets/images/help/details_actions.png\"><p>Pour relire l'ensemble des détails liés à une action, un simple clic sur le symbole + dans la colonne \"Détails\" fournit tous les détails de l'action choisie. En bas de ces détails, trois boutons vous permettent de re-travailler vos actions.</p><h3>Editer une action</h3><p>Ce bouton vous permet de re-modifier à l'envi tous les champs descriptifs de l'action</p><h3> Indiquer une action comme réalisée</h3><p>Le bouton \"Marquer une action comme réalisée\" permet de changer instantanément son statut en \"réalisé\" lors de l'année actuelle, sans devoir passer par le mode édition.</p><h3>Supprimer une action</h3><p>Contrairement au fait de passer une action en statut \"annulé\" pour ce souvenir de ce que l'action avait été identifiée mais s'est avérée infaisable, il est possible de purement et simplement supprimer une action, qui n'apparaîtra dès lors plus nulle part.</p><h2>Transformer un conseil en action</h2><p>Cliquez simplement sur le bouton \"En faire une action\", et une action basée sur le conseil sera créée pour vous. Libre ensuite à vous de la modifier comme n'importe quelle autre action que vous gérez.</p>");$templateCache.put('$/angular/views/household/TAB_M2.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AM13\"><mm-awac-question question-code=\"AM14\"></mm-awac-question><mm-awac-question question-code=\"AM15\"></mm-awac-question><mm-awac-question question-code=\"AM16\" ng-condition=\"getAnswer('AM14').value == '2'\"></mm-awac-question><mm-awac-question question-code=\"AM17\"></mm-awac-question><mm-awac-question question-code=\"AM18\"></mm-awac-question><mm-awac-question question-code=\"AM19\"></mm-awac-question><mm-awac-question question-code=\"AM20\" ng-condition=\"getAnswer('AM19').value == '1'\"></mm-awac-question><mm-awac-question question-code=\"AM21\" ng-condition=\"getAnswer('AM19').value == '1'\"></mm-awac-question><mm-awac-question question-code=\"AM22\" ng-condition=\"getAnswer('AM19').value == '1'\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AM24\"><mm-awac-repetition-name question-code=\"AM26\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AM26\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AM26')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM27\" ng-map-index=\"{'AM26':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM28\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM27',itLevel1).value == 'AS_43'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM29\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM27',itLevel1).value != 'AS_43' || getAnswer('AM28',itLevel1).value == '0'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM30\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '1' &amp;&amp; (getAnswer('AM27',itLevel1).value == 'AS_1' || getAnswer('AM27',itLevel1).value == 'AS_3' || getAnswer('AM27',itLevel1).value == 'AS_376' || getAnswer('AM27',itLevel1).value == 'AS_377' || getAnswer('AM27',itLevel1).value == 'AS_4' || getAnswer('AM27',itLevel1).value == 'AS_15' || getAnswer('AM27',itLevel1).value == 'AS_16')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM31\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '1' &amp;&amp; (getAnswer('AM27',itLevel1).value == 'AS_10' || getAnswer('AM27',itLevel1).value == 'AS_11' || getAnswer('AM27',itLevel1).value == 'AS_13')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM32\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '1' &amp;&amp; getAnswer('AM27',itLevel1).value == 'AS_43'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM33\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM34\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; getAnswer('AM27',itLevel1).value == 'AS_1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM35\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; (getAnswer('AM27',itLevel1).value == 'AS_3' || getAnswer('AM27',itLevel1).value == 'AS_4' || getAnswer('AM27',itLevel1).value == 'AS_376' || getAnswer('AM27',itLevel1).value == 'AS_377')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM36\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; getAnswer('AM27',itLevel1).value == 'AS_15'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM37\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; getAnswer('AM27',itLevel1).value == 'AS_16'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM38\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; (getAnswer('AM27',itLevel1).value == 'AS_11' || getAnswer('AM27',itLevel1).value == 'AS_13')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM39\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; getAnswer('AM27',itLevel1).value == 'AS_10'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM40\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; getAnswer('AM27',itLevel1).value == 'AS_43'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AM33',itLevel1).value | nullToZero)/(getAnswer('AM34',itLevel1).value | nullToZero)\" question-code=\"AM41\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; getAnswer('AM27',itLevel1).value == 'AS_1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AM33',itLevel1).value | nullToZero)/(getAnswer('AM35',itLevel1).value | nullToZero)\" question-code=\"AM42\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; (getAnswer('AM27',itLevel1).value == 'AS_3' || getAnswer('AM27',itLevel1).value == 'AS_4' || getAnswer('AM27',itLevel1).value == 'AS_376' || getAnswer('AM27',itLevel1).value == 'AS_377')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AM33',itLevel1).value | nullToZero)/(getAnswer('AM36',itLevel1).value | nullToZero)\" question-code=\"AM43\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; getAnswer('AM27',itLevel1).value == 'AS_15'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AM33',itLevel1).value | nullToZero)/(getAnswer('AM37',itLevel1).value | nullToZero)\" question-code=\"AM44\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; getAnswer('AM27',itLevel1).value == 'AS_16'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AM33',itLevel1).value | nullToZero)/(getAnswer('AM38',itLevel1).value | nullToZero)\" question-code=\"AM45\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; (getAnswer('AM27',itLevel1).value == 'AS_11' || getAnswer('AM27',itLevel1).value == 'AS_13')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AM33',itLevel1).value | nullToZero)/(getAnswer('AM39',itLevel1).value | nullToZero)\" question-code=\"AM46\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; getAnswer('AM27',itLevel1).value == 'AS_10'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AM33',itLevel1).value | nullToZero)/(getAnswer('AM40',itLevel1).value | nullToZero)\" question-code=\"AM47\" ng-map-index=\"{'AM26':$index}\" ng-condition=\"getAnswer('AM29',itLevel1).value == '2' &amp;&amp; getAnswer('AM27',itLevel1).value == 'AS_43'\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AM26\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AM48\"><mm-awac-question question-code=\"AM49\"></mm-awac-question><mm-awac-question question-code=\"AM50\" ng-condition=\"getAnswer('AM49').value == 'AS_43'\"></mm-awac-question><mm-awac-question question-code=\"AM51\" ng-condition=\"getAnswer('AM49').value != 'AS_43' || getAnswer('AM50').value == false\"></mm-awac-question><mm-awac-question question-code=\"AM52\" ng-condition=\"getAnswer('AM51').value == '1' &amp;&amp; getAnswer('AM49').value != 'AS_43'\"></mm-awac-question><mm-awac-question question-code=\"AM53\" ng-condition=\"getAnswer('AM51').value == '1' &amp;&amp; getAnswer('AM49').value == 'AS_43'\"></mm-awac-question><mm-awac-question question-code=\"AM54\" ng-condition=\"getAnswer('AM51').value == '2'\"></mm-awac-question><mm-awac-question question-code=\"AM55\" ng-condition=\"getAnswer('AM51').value == '2' &amp;&amp; (getAnswer('AM49').value == 'AS_3' || getAnswer('AM49').value == 'AS_4' || getAnswer('AM49').value == 'AS_376' || getAnswer('AM49').value == 'AS_377')\"></mm-awac-question><mm-awac-question question-code=\"AM56\" ng-condition=\"getAnswer('AM51').value == '2' &amp;&amp; getAnswer('AM49').value == 'AS_15'\"></mm-awac-question><mm-awac-question question-code=\"AM57\" ng-condition=\"getAnswer('AM51').value == '2' &amp;&amp; getAnswer('AM49').value == 'AS_16'\"></mm-awac-question><mm-awac-question question-code=\"AM58\" ng-condition=\"getAnswer('AM51').value == '2' &amp;&amp; getAnswer('AM49').value == 'AS_43'\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AM54').value | nullToZero)/(getAnswer('AM55').value | nullToZero)\" question-code=\"AM59\" ng-condition=\"getAnswer('AM51').value == '2' &amp;&amp; (getAnswer('AM49').value == 'AS_3' || getAnswer('AM49').value == 'AS_4' || getAnswer('AM49').value == 'AS_376' || getAnswer('AM49').value == 'AS_377')\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AM54').value | nullToZero)/(getAnswer('AM56').value | nullToZero)\" question-code=\"AM60\" ng-condition=\"getAnswer('AM51').value == '2' &amp;&amp; getAnswer('AM49').value == 'AS_15'\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AM54').value | nullToZero)/(getAnswer('AM57').value | nullToZero)\" question-code=\"AM61\" ng-condition=\"getAnswer('AM51').value == '2' &amp;&amp; getAnswer('AM49').value == 'AS_16'\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AM54').value | nullToZero)/(getAnswer('AM58').value | nullToZero)\" question-code=\"AM62\" ng-condition=\"getAnswer('AM51').value == '2' &amp;&amp; getAnswer('AM49').value == 'AS_43'\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AM63\"><mm-awac-question question-code=\"AM65\"></mm-awac-question><mm-awac-question question-code=\"AM66\"></mm-awac-question><mm-awac-question question-code=\"AM67\" ng-condition=\"getAnswer('AM66').value == '1'\"></mm-awac-question><mm-awac-question question-code=\"AM68\" ng-condition=\"getAnswer('AM66').value == '2'\"></mm-awac-question><mm-awac-question question-code=\"AM69\" ng-condition=\"getAnswer('AM66').value == '2'\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AM68').value | nullToZero)/(getAnswer('AM69').value | nullToZero)\" question-code=\"AM70\" ng-condition=\"getAnswer('AM66').value == '2'\"></mm-awac-question><mm-awac-question question-code=\"AM71\"></mm-awac-question><mm-awac-sub-sub-title ng-show=\"getAnswer('AM71').value == true\" question-code=\"AM72\"></mm-awac-sub-sub-title><mm-awac-question ng-optional=\"true\" question-code=\"AM73\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM74\" ng-condition=\"getAnswer('AM73').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM75\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM76\" ng-condition=\"getAnswer('AM75').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM77\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM78\" ng-condition=\"getAnswer('AM77').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM79\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM80\" ng-condition=\"getAnswer('AM79').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM81\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM82\" ng-condition=\"getAnswer('AM81').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM83\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM84\" ng-condition=\"getAnswer('AM83').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM85\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM86\" ng-condition=\"getAnswer('AM85').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM87\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM88\" ng-condition=\"getAnswer('AM87').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM89\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM90\" ng-condition=\"getAnswer('AM89').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM91\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM92\" ng-condition=\"getAnswer('AM91').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM93\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM94\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM95\" ng-condition=\"getAnswer('AM94').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM96\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM97\" ng-condition=\"getAnswer('AM96').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM98\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM99\" ng-condition=\"getAnswer('AM98').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM100\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM101\" ng-condition=\"getAnswer('AM100').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM102\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM103\" ng-condition=\"getAnswer('AM102').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM104\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM105\" ng-condition=\"getAnswer('AM104').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM106\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM107\" ng-condition=\"getAnswer('AM106').value &gt; 0\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM108\" ng-condition=\"getAnswer('AM71').value == true\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"AM109\" ng-condition=\"getAnswer('AM108').value &gt; 0\"></mm-awac-question></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/household/help_form_fr.html', "<h1>Calculateur CO2 ménages de l'AWAC: aide générale</h1><h2>Introduction</h2><p>Le but du présent calculateur est de vous permettre de réaliser le bilan GES de votre organisation de la manière la plus simple possible tout en respectant le prescrit du GHG protocol. C'est pourquoi l'AWAC a sélectionné pour vous les bonnes questions à se poser, et pour lesquelles il vous \"suffit\" de fournir les données vous concernant afin d'obtenir un bilan GES valide. La présente page d'aide générale vous détaille d'abord l'ensemble des boutons disponibles dans les menus du haut de l'application, avant de détailler la manière de fournir les données nécessaires au calcul du bilan.</p><h2>Interface générale</h2><p>L'image ci-dessous vous présente le menu principal de l'application:</p><img src=\"/assets/images/help/entreprise_menu.png\"><p>Les annotations numérotées  correspondent aux explications ci-dessous:</p><p>1. Chaque utilisateur se connecte à l'application via son identifiant et son mot de passe. Il peut à tout moment modifier ses coordonnées personnelles via le menu \"Mon profil\".</p><p>2. Chaque utilisateur peut demander à avoir les interfaces et les questions liées à la collecte de données dans la langue de son choix parmi les langues disponibles dans l'application.</p><p>3. Le calculateur entreprise permettant de consolider le bilan de l'organisation comme la somme de bilans sur différents sites, il faut que l'utilisateur choisisse un site parmis ceux auxquels il a accès. La gestion des sites (pour les créer et y donner accès) est effectuée par les administrateurs (le créateur d'un compte est en administrateur par défaut) via le bouton \"Gérer les sites\".</p><p>4. Tout administrateur peut également \"Gérer les utilisateurs\" pour les rendre administrateurs, inviter de nouveaux utilisateurs ou en désactiver certains.</p><p>5. Et les administrateurs peuvent également modifier certains paramètres d'organisation via le bouton \"Gérer l'organisation\".</p><p>6. L'utilisateur choisit alors pour quelle année (parmis celles demandées, cf. \"Gérer les sites\") il souhaite fournir des données</p><p>7. A tout moment, il peut enregistrer les données fournies. Pour chaque formulaire thématique de données (voir plus bas), l'application indique le moment du dernier enregitrement effectué.</p><p>8. L'utilisateur a aussi la possibilité de comparer les données avec celle d'une autre année, ce qui peut facilité le recopiage des données restées similaires (voir plus bas).</p><p>9. Les administrateurs peuvent également demander une vérification externe des données (voir plus bas).</p><h2>Fournir des données</h2><p>Les données à fournir sont divisées en plusieurs parties distinctes. Vous accédez à chaque partie en cliquant sur son titre.</p><img src=\"/assets/images/help/entreprise_forms.png\"><p></p><p>La partie sélectionnée est présentée sur fond vert. Pour chaque partie, une barre de progression vous indique quelle fraction des données escomptées vous avez déjà remplie.</p><p>Tout à droite, la partie \"résultats\" vous donne accès à la présentation du bilan CO2 en fonction des données que vous avez remplies, et la partie \"actions de réduction\" vous permet de définir de telles actions pour améliorer votre bilan d'année en année.</p><h3>Types de données</h3><p>Chaque utilisateur impliqué est invité à fournir les données qu'il maîtrise. Pour ce faire, il lui suffit de remplir les cases faites pour. Plusieurs types de données différentes sont indiquées avec des icones différentes:</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-share-alt\"></span> indique des questions dont les réponses sont nécessaires au calcul du résultat.</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-paperclip\"></span> indique à l'inverse des questions qui ne servent pas au résultat mais uniquement à vous aider à mémoriser le contexte.</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-file\"></span> indique des questions documentaires qui vous permettent de stocker vos documents relatifs à certains jeux de données, afin de pouvoir les re-consulter à tout moment.</p><p><span class=\"glyphicon glyphicon-record\"></span> indique des questions regroupées en un bloc qu'il vous appartient de répliquer autant de fois que nécessaire (voir ci-dessous, création de blocs de données)</p><p></p><p>Une icone est encore présente derrière la donnée: <button class=\"button edit_comment_icon glyphicon glyphicon-pencil\"></button> Il s'agit d'un champs de commentaires libres auquel vous accéder en cliquant dessus: cela vous permet de noter des aspects compélmentaires à la donnée fournie. Lorsqu'un commentaire est effectivement présent, cette icône devient verte: <button class=\"button edit_comment_icon glyphicon glyphicon-pencil edit_comment_icon_selected\"></button></p><p>Finalement, certaines questions sont également suivies par un symbole:</p><img src=\"/assets/images/info.png\"><p>Il s'agit d'informations complémentaires sur la question qui apparaissent en infobulle lorsqu'on passe la souris sur cette icône.</p><h3>Remplissage des données</h3><p>Pour toutes les questions attendues par le système pour offrir un résultat le plus exhaustif possible, une icône sous forme de main vient donner une indication complémentaire:</p><img src=\"/assets/images/status/pending.png\"><p>indique une question attendue mais pas encore répondue;</p><img src=\"/assets/images/status/answer_temp.png\"><p>indique une question répondue mais pas encore sauvée;</p><img src=\"/assets/images/status/answer.png\"><p>indique une question répondue et dont la réponse est bien enregistrée;</p><img src=\"/assets/images/status/pending_temp.png\"><p>indique une question dont on a effacé la réponse, mais pas encore sauvegardé ce changement.</p><p>Pour remplir la donnée, il suffit d'encoder celle-ci dans la case blanche. Une fois la donnée enregistrée, une case avec des initiales indique quel utilisateur a fourni cette donnée. Si on laisse la souris dessus, le nom complet de la personne apparaît.</p><img src=\"/assets/images/help/question_repondue.png\"><h3>Création de blocs de données</h3><p>Comme énoncé plus haut, le symbole <span class=\"glyphicon glyphicon-record\"></span> indique des questions regroupées en un bloc qu'il vous appartient de répliquer autant de fois que nécessaire.</p><img src=\"/assets/images/help/bloc_donnees.png\"><p>Dans de tels cas, le bouton \"Ajouter...\" vous permet de créer autant de blocs de données identiques que nécessaire dans votre cas. A tout moment, le bouton \"Supprimer\" vous permet d'effacer un de ces blocs.</p><h3>Comparaison des données d'une autre année et recopiage d'une année à l'autre</h3><p>Dès que vous choisissez de comparer les données avec celle d'une autre année (cf. point 8 de l'introduction du menu principal), les données passent en deux colonnes pour vous permettre de comparer, donnée par donnée, les deux années.</p><img src=\"/assets/images/help/comparaison_donnees.png\"><p>Le bouton <button class=\"button\" title=\"Copier la valeur\" >&lt;&lt;</button> vous permet de recopier d'un seul clic la valeur de l'année de référence dans la case de l'année en cours.</p><p>Dans le cas de blocs de données (cf. ci-dessus), il vous faut d'abord créer le bloc pour l'année en cours, et à ce moment-là les données de l'année de référence viendront se placer à côté et seront recopiables.</p><h3>Données alternatives</h3><p>A plusieurs endroits, des méthodes différentes existent pour collecter les données nécessaires. Elles vous sont chaque fois présentées de la meilleure, en vert clair, à la plus approximative (vert foncé, puis jaune, puis orange). L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Pour le calcul du résultat, le système utilisera les données de la meilleure méthode entièrement complétée, ce qui est indiqué par le symbole <i class=\"glyphicon glyphicon-bell\"></i>.</p><img src=\"/assets/images/help/alternative.png\"><p>Dans l'exemple ci-dessus, les approches \"calcul par euros dépensés\" et \"calcul par les kilomètres\" ont été entièrement remplies, tandis que l'approche \"calcul par les consommations\" n'est pas complet. Le système indique donc qu'il utilisera les données de l'approche \"calcul par les kilomètres\". S'il n'y a pas de symbole <i class=\"glyphicon glyphicon-bell\"></i>, cela signifie qu'aucune des alternatives n'est complète et que le système ne saura pas prendre cette partie en compte dans le résultat.</p><h2>Travailler à plusieurs</h2><p>Afin de  faciliter la collecte des données nécessaires à un bilan complet, il est souvent plus efficace de pouvoir travailler à plusieurs collègues. C'est pourquoi plusieurs utilisateurs peuvent être invités sur le compte de l'organsation par le ou les administrateur(s). Il est toutefois important qu'un utilisateur n'annule pas le travail des autres. Divers mécanismes sont prévus pour cela.</p><p>Tout d'abord, il est impossible pour deux utilisateurs d'accéder en même temps à la même partie des données. Si un autre utilisateur est déjà en train de travailler sur une partie, vous recevrez un message vous y refusant l'accès en vous annonçant qui y travaille déjà.</p><p>Ensuite, deux mécanismes complémentaires permettent de gérer la collaboration.</p><h3>Se réserver des données</h3><p>Le cadenas qui figure en dessous de chaque rubrique de données permet à un utilisateur de bloquer l'ensemble des données de la rubrique pour son seul usage.</p><img src=\"/assets/images/help/localisation_cadenas.png\"><p>Seul cet utilisateur aura encore la possibilité de travailler sur ces données, tandis que les autres utilisateurs verront un cadenas fermé (le nom de l'utilisateur l'ayant fermé apparaît lorsque la souris est passée sur le cadenas).</p><p>L'utilisateur qui a verrouillé peut rouvrir le cadenas à tout moment en cliquant dessus.</p><h3>Valider des données</h3><p>En dessous du cadenas figure une autre icône dont le principe de fonctionnement est similaire.</p><img src=\"/assets/images/status/validate_not.png\"><p>Cette icône permet à un utilisateur de valider l'ensemble des données de la rubrique. C'est-à-dire d'indiquer que ces données ont été relues, sont correctes, et ne peuvent dès lors plus être modifiées. Dès lors, plus personne, cet utilisateur inclus, ne peut modifier les données concernées.</p><p><em>Note</em>: un administrateur peut toujours, à n'importe quel moment, annuler le cadenas ou la validation de n'importe quel utilisateur.</p><h2>Faire vérifier les données de manière externe</h2><p>Encore en dessous des icônes de validation, figure une icône de vérification:</p><img src=\"/assets/images/status/verified_unverified.png\"><p>Il s'agit d'indique le statut lorsque les données ont été vérifiées par une personne externe, typiquement un auditeur.</p><p>Pour pouvoir demander une vérification externe, il faut d'abord que toutes les données ait étées validées en interne (cf. ci-dessus).</p><img src=\"/assets/images/help/bouton_verification.png\"><p>Le bouton de demande de vérification devient alors vert pour indiquer qu'il est actif. Seuls les administrateurs le verront apparaître dans leur interface, et pour toute action qu'ils souhaiteront prendre eu égard à la vérification externe, ils devront chaque fois la confirmer avec leur mot de passe.</p><img src=\"/assets/images/help/demander_verification.png\"><p>Cliquer sur le bouton ouvre une fenêtre qui permet de demander la vérification à une personne externe en fournissant l'adresse de courrier électronique de la dite personne. Il appartiendra ensuite à cette personne de réagir et d'accepter le travail qui lui est demandé. Le suivi du processus se fait de manière très simple: à chaque fois qu'une action est nécessaire de la part de votre organisation, le ou les administrateur(s) du compte reçoivent un courriel leur expliquant le statut et la marche à suivre. En effet, ce bouton qui indique intialement \"Demander une vérification\" va changer de texte au fur et à mesure que le processus avance.</p><p>Tout à la fin du processus, si tout s'est bien passé, le rapport de vérification deviendra alors disponible dans la partie \"résultats\".</p>");$templateCache.put('$/angular/views/household/TAB_M1.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AM3\"><mm-awac-question question-code=\"AM4\"></mm-awac-question><mm-awac-question question-code=\"AM5\"></mm-awac-question><mm-awac-question question-code=\"AM6\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AM7\"><mm-awac-question question-code=\"AM8\"></mm-awac-question><mm-awac-question question-code=\"AM9\"></mm-awac-question><mm-awac-question question-code=\"AM10\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AM8').value | nullToZero)+(getAnswer('AM9').value | nullToZero)+(getAnswer('AM10').value | nullToZero)\" question-code=\"AM11\"></mm-awac-question><mm-awac-question question-code=\"AM12\"></mm-awac-question></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/household/TAB_M3.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AM110\"><mm-awac-repetition-name question-code=\"AM111\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AM111\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AM111')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM112\" ng-map-index=\"{'AM111':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM113\" ng-map-index=\"{'AM111':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM114\" ng-map-index=\"{'AM111':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM115\" ng-map-index=\"{'AM111':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AM111\"></mm-awac-repetition-add-button><mm-awac-question question-code=\"AM116\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AM117\"><mm-awac-sub-title question-code=\"AM118\"></mm-awac-sub-title><mm-awac-question question-code=\"AM119\"></mm-awac-question><mm-awac-question question-code=\"AM120\"></mm-awac-question><mm-awac-question question-code=\"AM121\"></mm-awac-question><mm-awac-sub-title question-code=\"AM122\"></mm-awac-sub-title><mm-awac-question question-code=\"AM123\"></mm-awac-question><mm-awac-question question-code=\"AM124\"></mm-awac-question><mm-awac-question question-code=\"AM125\"></mm-awac-question><mm-awac-sub-title question-code=\"AM126\"></mm-awac-sub-title><mm-awac-question question-code=\"AM127\"></mm-awac-question><mm-awac-question question-code=\"AM128\"></mm-awac-question><mm-awac-question question-code=\"AM129\"></mm-awac-question><mm-awac-sub-title question-code=\"AM130\"></mm-awac-sub-title><mm-awac-question question-code=\"AM131\"></mm-awac-question><mm-awac-question question-code=\"AM132\"></mm-awac-question><mm-awac-question question-code=\"AM133\"></mm-awac-question><mm-awac-sub-title question-code=\"AM140\"></mm-awac-sub-title><mm-awac-question question-code=\"AM141\"></mm-awac-question><mm-awac-question question-code=\"AM142\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AM500\"><mm-awac-repetition-name question-code=\"AM134\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AM134\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AM134')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM135\" ng-map-index=\"{'AM134':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM136\" ng-map-index=\"{'AM134':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM137\" ng-map-index=\"{'AM134':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM138\" ng-map-index=\"{'AM134':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM139\" ng-map-index=\"{'AM134':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AM134\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AM600\"><mm-awac-repetition-name question-code=\"AM143\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AM143\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AM143')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM144\" ng-map-index=\"{'AM143':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM145\" ng-map-index=\"{'AM143':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AM146\" ng-map-index=\"{'AM143':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AM143\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AM147\"><mm-awac-question question-code=\"AM148\"></mm-awac-question><mm-awac-question question-code=\"AM149\"></mm-awac-question></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/household/agreement_fr.html', "<h1>Mentions légales du calculateur CO2 ménages de l'AWAC</h1><h2> Information juridique - Conditions d'utilisation du site</h2><p>L'utilisation du présent site est soumise au respect des conditions générales décrites ci-après. En accédant à ce site, vous déclarez avoir pris connaissance et avoir accepté, sans la moindre réserve, ces conditions générales d'utilisation.</p><h2>Qualité de l'information et du service – Clause de non-responsabilité</h2><p>Nous apportons le plus grand soin à la gestion de ce site. Nous ne garantissons toutefois pas l'exactitude des informations qui y sont proposées. Celles-ci sont par ailleurs susceptibles d'être modifiées sans avis préalable. Il en résulte que nous déclinons toute responsabilité quant à l'utilisation qui pourrait être faite du contenu de ce site. Les liens hypertextes présents sur le site et aiguillant les utilisateurs vers d'autres sites Internet n'engagent pas notre responsabilité quant au contenu de ces sites.</p><h2>Protection des données à caractère personnel</h2><p>Il vous est loisible de visiter notre site internet sans nous fournir de données à caractère personnel (1). Vous pouvez accéder à la base de calcul et de résultats en ne saisissant que des données générales à caractère non personnel.</p><p>Si vous nous fournissez des informations personnelles (2), elles seront traitées conformément aux dispositions de la loi du 8 décembre 1992. Elles seront traitées, moyennant votre accord,  anonymement à des fins statistiques et pour vous adresser des messages électroniques généraux; elles ne seront pas communiquées à des tiers, à l’exception du sous-traitant avec lequel l’AWAC a conclu une convention relative à la confidentialité des données, ni utilisées à des fins commerciales.  L’AWAC s'engage à prendre les meilleures mesures de sécurité afin d'éviter que des tiers n'abusent des données à caractère personnel qui lui ont été communiquées.</p><p>Les données saisies en ligne et les résultats associés sont traités collectivement et anonymement. Ces données et résultats pourront être exploités, en totalité ou en partie, en vue de réalisations statistiques, de réalisations de rapports ou d’études relatives à la consommation d’énergie et d’émission de gaz à effet de serre.</p><h2>Droits de propriété intellectuelle</h2><p>Tous droits sur le contenu et l'architecture de notre site, et notamment les éléments suivants : textes, créations graphiques, logos, éléments sonores, publications, mises en page, bases de données, algorithmes de calculs... sont protégés par le droit national et international de la propriété intellectuelle.</p><p>A ce titre, vous ne pouvez procéder à une quelconque reproduction, représentation, adaptation, traduction et/ou transformation partielle ou intégrale, ou un transfert sur un autre site web de tout élément composant le site.</p><p>Cependant si vous souhaitez utiliser les éléments disponibles sur notre site, nous vous invitons à adresser votre demande d’autorisation à l’AWAC en précisant les éléments à reproduire et l’utilisation souhaitée.</p><h2>Création d'hyperliens vers le site</h2><p>Nous autorisons la création sans demande préalable de liens en surface (surface linking) qui renvoient à la page d'accueil du site ou à toute autre page dans sa globalité. Par contre, le recours à toutes techniques visant à inclure tout ou partie du site dans un site Internet en masquant ne serait-ce que partiellement l'origine exacte de l'information ou pouvant prêter à confusion sur l'origine de l'information, telle que le framing ou l'inlining, requiert une autorisation écrite.</p><h2>Modification des mentions légales</h2><p>L’AWAC se réserve le droit de modifier les présentes mentions à tout moment. L’utilisateur est lié par les conditions en vigueur lors de sa visite.</p><h2>Droit applicable en cas de litige</h2><p>Le droit applicable est le droit belge.</p><p>Tout litige relèvera de la compétence exclusive des tribunaux de Namur.</p><h3>Personne de Contact</h3><p>Cécile Batungwnanayo</p><p>Agence wallone de l'air et du Climat (AWAC)</p><p>7, Avenue Prince de Liège</p><p>B-5100 Jambes</p><P>Belgium</P><h2>Notes</h2><p>(1) La loi du 8 décembre 1992 relative au traitement de données à caractère personnel entend par « données à caractère personnel », toute information concernant une personne physique identifiée ou identifiable […] ; est réputée identifiable une personne qui peut être identifiée, directement ou indirectement, notamment par référence à un numéro d’identification ou à un plusieurs éléments spécifiques, propres à son identité physique, physiologique, psychique, économique, culturelle ou sociale. »</p><p>(2) Pour les personnes physiques, via la création d’un espace personnel sur le site avec votre e-mail et un mot de passe.  Si votre adresse e-mail est constituée de votre nom de famille, elle peut permettre votre identification.</p>");$templateCache.put('$/angular/views/organization_manager.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1 ng-bind-html=\"'ORGANIZATION_MANAGER_TITLE' | translateText\" ng-hide=\"$root.instanceName =='household'\"></h1><h1 ng-show=\"$root.instanceName =='household'\" ng-bind-html=\"'HOUSEHOLD_MANAGER_TITLE' | translateText\"></h1><div class=\"site_manager\"><br><h4 ng-bind-html=\"'ORGANIZATION_MANAGER_PROPERTIES_TITLE' | translateText\" ng-hide=\"$root.instanceName =='household'\">)</h4><h4 ng-show=\"$root.instanceName =='household'\" ng-bind-html=\"'HOUSEHOLD_MANAGER_PROPERTIES_TITLE' | translateText\">)</h4><div class=\"field_form\" style=\"width: 600px;\"><mm-awac-modal-field-text ng-info=\"nameInfo\"></mm-awac-modal-field-text><div class=\"field_row\" ng-hide=\"$root.instanceName == 'verification'\"><div class=\"field_cell\" ng-bind-html=\"'ORGANIZATION_STATISTICS_ALLOWED' | translateText\"></div><div class=\"field_cell\" ng-hide=\"$root.instanceName == 'verification' || $root.instanceName == 'admin'\"><input ng-model=\"statisticsAllowed\" style=\"width: 18px;height: 18px;margin-top: 2px;\" type=\"checkbox\"></div></div></div><div ng-hide=\"isLoading\"><button ng-click=\"saveOrganization()\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" class=\"btn btn-primary\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"><div ng-hide=\"$root.instanceName == 'verification' || $root.instanceName == 'admin' || $root.instanceName == 'littleemitter' || $root.instanceName == 'event' || $root.instanceName == 'household' \"><br><br><h4 ng-bind-html=\"'SITE_MANAGER_EVENT_TITLE' | translateText\"></h4><div class=\"desc\" ng-bind-html=\"'SITE_MANAGER_EVENT_DESC' | translateText\"></div><br><div><span class=\"select_period\" ng-bind-html=\"'SITE_MANAGER_SELECT_PERIOD' | translateText\"></span><select ng-model=\"selectedPeriodForEvent\" ng-options=\"p.key as p.label for p in $root.periods\"></select></div><table class=\"site_table\"><tr class=\"site_table_header\"><td ng-bind-html=\"'SITE_MANAGER_EVENT_NAME' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_EVENT_DESCRIPTION' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_EVENT_PERIOD' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_EDIT_EVENT_BUTTON' | translateText\"></td></tr><tr ng-show=\"event.period.key == selectedPeriodForEvent\" ng-repeat=\"event in events\"><td>{{event.name}}</td><td>{{event.description}}</td><td>{{event.period.label}}</td><td><button ng-click=\"editOrCreateEvent(event)\" type=\"button\" title=\"{{'SITE_MANAGER_EDIT_EVENT_BUTTON' | translateText}}\" class=\"edit_icon glyphicon glyphicon-pencil\"></button></td></tr></table><button class=\"button add\" ng-click=\"editOrCreateEvent()\" ng-bind-html=\"'SITE_MANAGER_ADD_EVENT_BUTTON' | translateText\" type=\"button\"></button></div></div></div>");$templateCache.put('$/angular/views/user_data.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1 ng-bind-html=\"'USER_DATA_BUTTON' | translate\"></h1><div class=\"user_data\"><div class=\"desc\" ng-bind-html=\"'ANONYMOUS_PROFILE_DESC' | translateText\" ng-if=\"isAnonymousUser()\"></div><div class=\"field_form\" ng-if=\"isAnonymousUser()\"><mm-awac-modal-field-text ng-info=\"anonymous.loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"anonymous.passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"anonymous.lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"anonymous.firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"anonymous.emailInfo\"></mm-awac-modal-field-text><br><div style=\"display:table-row\"><div style=\"display:table-cell\"></div><div style=\"display:table-cell\"></div><div style=\"display:table-cell\"><div style=\"text-align: right\" ng-hide=\"isLoading\"><button ng-click=\"sendAnonymous()()\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" class=\"btn btn-primary\" ng-disabled=\"!anonymousFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div><div style=\"display:table\" ng-if=\"!isAnonymousUser()\" class=\"field_form\"></div></div><div class=\"field_form\" ng-if=\"!isAnonymousUser()\"><mm-awac-modal-field-text ng-info=\"identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"><button class=\"edit_icon\" ng-click=\"changePassword()\" type=\"button\" title=\"{{'UPDATE_PASSWORD_BUTTON' | translateText}}\"><span class=\"glyphicon glyphicon-pencil\"></span></button></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"emailInfo\"><button class=\"edit_icon\" ng-click=\"changeEmail()\" type=\"button\" title=\"{{'UPDATE_EMAIL_BUTTON' | translateText}}\"><span class=\"glyphicon glyphicon-pencil\"></span></button></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\"></div><div class=\"field_cell field_wide\"><div style=\"text-align: right\" ng-hide=\"isLoading\"><button ng-click=\"send()\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" class=\"btn btn-primary\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div></div>");$templateCache.put('$/angular/views/product_manager.html', "<div><div class=\"menu_close\" ng-click=\"toForm()\"></div><h1 ng-bind-html=\"'EVENT_MANAGER_BUTTON' | translateText\"></h1><div class=\"site_manager\"><h4 ng-bind-html=\"'EVENT_MANAGER_EVENT_LIST_TITLE' | translateText\"></h4><div class=\"desc\" ng-bind-html=\"'EVENT_MANAGER_EVENT_LIST_DESC' | translateText\"></div><table class=\"site_table\"><tr class=\"site_table_header\"><td ng-bind-html=\"'EDIT_EVENT_EDIT_TITLE' | translateText\"></td><td ng-bind-html=\"'NAME' | translateText\"></td><td ng-bind-html=\"'DESCRIPTION' | translateText\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_USERS_BUTTON' | translateText\"></td><td><select ng-model=\"assignPeriod\" ng-options=\"p.key as p.label for p in $root.periods\"></select></td></tr><tr ng-repeat=\"product in getProductList()\"><td><button ng-click=\"editOrCreateProduct(product)\" type=\"button\" title=\"{{'SITE_MANAGER_EDIT_SITE_BUTTON' | translateText}}\" class=\"edit_icon glyphicon glyphicon-pencil\"></button></td><td>{{product.name}}</td><td>{{product.description}}</td><td><button ng-click=\"addUsers(product)\" type=\"button\" title=\"{{'SITE_MANAGER_ADD_USERS_BUTTON' | translateText}}\" class=\"edit_icon glyphicon glyphicon-pencil\"></button></td><td><input ng-model=\"isPeriodChecked[product.id]\" ng-click=\"assignPeriodToProduct(product)\" type=\"checkbox\" ng-hide=\"isLoading[product.id]=== true\"><img ng-show=\"isLoading[product.id]=== true\" src=\"/assets/images/modal-loading.gif\"></td></tr></table><button class=\"button add\" ng-click=\"editOrCreateProduct()\" ng-bind-html=\"'EVENT_MANAGER_CREATE_EVENT' | translateText\" type=\"button\"></button></div></div>");$templateCache.put('$/angular/views/admin/translation_linked_lists.html', "<div class=\"admin-translation\"><mm-admin-translations-navigation-bar></mm-admin-translations-navigation-bar><div class=\"translation-view\"><div class=\"translation-title\">Listes liées</div><div class=\"waiting-data\" ng-show=\"waitingData\"><img src=\"/assets/images/modal-loading.gif\"><span>Veuillez patienter...</span></div><table class=\"translations-table\" ng-hide=\"waitingData\"><thead><tr><th>Détails</th><th>CodeList</th><th>Calculateur(s)</th></tr></thead><tbody><tr ng-repeat-start=\"linkedList in linkedLists\"><td class=\"details\"><mm-plus-minus-toggle-button ng-model=\"linkedList.$opened\"></mm-plus-minus-toggle-button></td><td class=\"codelist\"><span class=\"text\">{{ linkedList.codeList }}</span></td><td class=\"calculators\"><span class=\"text\">{{ linkedList.calculators }}</span></td></tr><tr ng-show=\"linkedList.$opened\" ng-repeat-end class=\"details\"><td colspan=\"3\"><div class=\"linkedlist-header\"><div class=\"order-column\">#</div><div class=\"key-column\">Clé</div><div class=\"labels-columns\"><div class=\"label-column\">Libellé EN</div><div class=\"label-column\">Libellé FR</div><div class=\"label-column\">Libellé NL</div><div class=\"link-column\">ActivitySource</div><div class=\"link-column\">ActivityType</div></div></div><div class=\"linkedlist-content\"><div class=\"linkedlist-items\" ui-sortable=\"sortableOptions\" ng-model=\"linkedList.items\"><div class=\"linkedlist-item\" ng-repeat=\"item in linkedList.items\"><div class=\"order-column\">{{ $index + 1 }}</div><div class=\"key-column\">{{ item.key }}</div><div class=\"labels-columns\"><div class=\"label-column\"><textarea ng-model=\"item.labelEn\"></textarea></div><div class=\"label-column\"><textarea ng-model=\"item.labelFr\"></textarea></div><div class=\"label-column\"><textarea ng-model=\"item.labelNl\"></textarea></div><div class=\"link-column\"><textarea readonly>{{ getActivitySourceLabel(item.activitySourceKey) }}</textarea></div><div class=\"link-column\"><textarea readonly>{{ getActivityTypeLabel(item.activityTypeKey) }}</textarea></div></div></div></div><div class=\"linkedlist-add-item\">Ajouter un élément:<div class=\"linkedlist-item\"><div class=\"order-column\"><button class=\"button\" ng-click=\"addItem(linkedList)\" style=\"margin:0; min-height:22px; padding:0px 5px 2px 5px; border-radius:2px;\" type=\"button\"><span class=\"fa fa-plus-circle\" style=\"vertical-align:middle;\"></span></button></div><div class=\"key-column\"><input ng-model=\"itemToAdd.key\"></div><div class=\"labels-columns\"><div class=\"label-column\"><textarea ng-model=\"itemToAdd.labelEn\"></textarea></div><div class=\"label-column\"><textarea ng-model=\"itemToAdd.labelFr\"></textarea></div><div class=\"label-column\"><textarea ng-model=\"itemToAdd.labelNl\"></textarea></div><div class=\"link-column\"><select ng-model=\"itemToAdd.activitySourceKey\" ng-options=\"cl.key as cl.key + ' - ' + cl.label for cl in activitySourcesLabels\"></select></div><div class=\"link-column\"><select ng-model=\"itemToAdd.activityTypeKey\" ng-options=\"cl.key as cl.key + ' - ' + cl.label for cl in activityTypesLabels\"></select></div></div></div></div></div></td></tr></tbody></table><div class=\"translation-actions\"><button class=\"button\" ng-click=\"save()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Enregistrer les modifications</button><button class=\"button\" ng-click=\"loadLinkedLists()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Annuler</button></div></div></div>");$templateCache.put('$/angular/views/admin/translation_single_list.html', "<div class=\"admin-translation\"><mm-admin-translations-navigation-bar></mm-admin-translations-navigation-bar><div class=\"translation-view\"><div class=\"translation-title\">{{ baseListLabel }}</div><table class=\"translations-table\" ng-hide=\"waitingData\"><tr><td class=\"details\"><table class=\"codelabels-table\"><thead><tr><th>Clé</th><th>Libellé EN</th><th>Libellé FR</th><th>Libellé NL</th></tr></thead><tbody><tr ng-repeat=\"codeLabel in codeLabels\"><td class=\"td-key\">{{ codeLabel.key }}</td><td class=\"td-label\"><textarea ng-model=\"codeLabel.labelEn\"></textarea></td><td class=\"td-label\"><textarea ng-model=\"codeLabel.labelFr\"></textarea></td><td class=\"td-label\"><textarea ng-model=\"codeLabel.labelNl\"></textarea></td></tr></tbody></table></td></tr></table><div class=\"translation-actions\"><button class=\"button\" ng-click=\"save()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Enregistrer les modifications</button><button class=\"button\" ng-click=\"undo()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Annuler</button></div></div></div>");$templateCache.put('$/angular/views/admin/driver.html', "<div class=\"verification_manage\"><span><h1>Drivers</h1></span><table class=\"actions-table\"><tr><th>Nom du driver</th><th>Caculateur(s)</th><th>Valeurs</th></tr><tr ng-repeat=\"driver in drivers\"><td ng-bind-html=\"driver.name | translate\"></td><td><div ng-repeat=\"calculator in driver.calculatorNames\">{{calculator}}</div></td><td><div ng-repeat=\"value in driver.driverValues\"><div style=\"width:50%;display:inline-block\">A partir de<select ng-model=\"value.fromPeriodKey\" ng-options=\"p.key as p.label for p in getPeriod(driver,value)\" ng-disabled=\"value.fromPeriodKey == '2000'\"></select></div><div style=\"display:inline-block\">Valeur<input ng-attr-numbers-only=\"{{driver.expectedValueType}}\" ng-model=\"value.defaultValue\" ng-click=\"wasEdited()\"></div><div style=\"display:inline-block\"><button class=\"button\" ng-click=\"remove(driver,value.tempId)\" type=\"button\" ng-hide=\"value.fromPeriodKey == '2000'\">supprimer</button></div></div><button class=\"button\" ng-click=\"addValue(driver)\" type=\"button\">Ajouter une valeur</button></td></tr></table><button class=\"button\" ng-click=\"save()\" type=\"button\">Sauver les modifications</button><button class=\"button\" ng-click=\"orderDriverValues()\" type=\"button\">Trier les valeurs par date</button></div>");$templateCache.put('$/angular/views/admin/translation_surveys.html', "<div class=\"admin-translation\"><mm-admin-translations-navigation-bar></mm-admin-translations-navigation-bar><div class=\"translation-view\"><div class=\"translation-title\">Données des formulaires</div><div class=\"waiting-data\" ng-show=\"waitingData\"><img src=\"/assets/images/modal-loading.gif\"><span>Veuillez patienter...</span></div><div class=\"translation-section\" ng-repeat=\"(calculatorCodeKey, calculatorForms) in formLabelsByCalculator\" ng-hide=\"waitingData\"><div class=\"section-title\">{{ calculatorCodeKey }}</div><div class=\"section-content\"><table class=\"translations-table\"><thead><tr><th>Détails</th><th>Formulaire</th></tr></thead><tbody><tr ng-repeat-start=\"form in calculatorForms\"><td class=\"details\"><mm-plus-minus-toggle-button ng-model=\"form.$opened\"></mm-plus-minus-toggle-button></td><td class=\"codelist\"><span class=\"text\">{{ form.codeKey }}</span></td></tr><tr ng-repeat-end ng-if=\"form.$opened\" class=\"details\"><td colspan=\"2\"><div class=\"form-labels\"><div class=\"form-labels-header\"><div class=\"label-key\">Clé</div><div class=\"question-labels\"><div class=\"question-label\">Libellé En</div><div class=\"question-label\">Libellé Fr</div><div class=\"question-label\">Libellé Nl</div></div></div><div class=\"form-labels-content\" ng-repeat=\"questionSet in form.questionSets\"><mm-awac-question-set-tree ng-model=\"questionSet\"></mm-awac-question-set-tree></div></div></td></tr></tbody></table></div></div><div class=\"translation-actions\"><button class=\"button\" ng-click=\"save()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Enregistrer les modifications</button><button class=\"button\" ng-click=\"undo()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Annuler</button></div></div></div>");$templateCache.put('$/angular/views/admin/translation_interface.html', "<div class=\"admin-translation\"><mm-admin-translations-navigation-bar></mm-admin-translations-navigation-bar><div class=\"translation-view\"><div class=\"translation-title\">Interface</div><div class=\"waiting-data\" ng-show=\"waitingData\"><img src=\"/assets/images/modal-loading.gif\"><span>Veuillez patienter...</span></div><table class=\"translations-table\" ng-hide=\"waitingData\"><thead><tr><th>Détails</th><th>Catégorie</th></tr></thead><tbody><tr ng-repeat-start=\"group in codeLabelsGroups\"><td class=\"details\"><mm-plus-minus-toggle-button ng-model=\"group.$opened\"></mm-plus-minus-toggle-button></td><td class=\"topic\"><span class=\"text\">{{ group.topic }}</span></td></tr><tr ng-show=\"group.$opened\" ng-repeat-end class=\"details\"><td>&nbsp;</td><td class=\"topic-content\"><table class=\"codelabels-table\"><thead><tr><th>Clé</th><th>Libellé EN</th><th>Libellé FR</th><th>Libellé NL</th></tr></thead><tbody><tr ng-repeat=\"codeLabel in group.codeLabels\"><td class=\"td-key\">{{ codeLabel.key }}</td><td class=\"td-label\"><textarea ng-model=\"codeLabel.labelEn\"></textarea></td><td class=\"td-label\"><textarea ng-model=\"codeLabel.labelFr\"></textarea></td><td class=\"td-label\"><textarea ng-model=\"codeLabel.labelNl\"></textarea></td></tr></tbody></table></td></tr></tbody></table><div class=\"translation-actions\"><button class=\"button\" ng-click=\"save()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Enregistrer les modifications</button><button class=\"button\" ng-click=\"undo()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Annuler</button></div></div></div>");$templateCache.put('$/angular/views/admin/translation_helps.html', "<div class=\"admin-translation\"><mm-admin-translations-navigation-bar></mm-admin-translations-navigation-bar><div class=\"translation-view\"><div class=\"admin-translations-helps\"><div class=\"row\"><div class=\"col-md-3\"><div class=\"panel panel-default\"><div class=\"panel-heading\" ng-repeat-start=\"c in categories\"><a ng-click=\"c.$displayed=!c.$displayed\" onclick=\"return false\" href=\"#\"><span class=\"fa fa-folder\" ng-hide=\"c.$displayed==true\"></span><span class=\"fa fa-folder-open\" ng-show=\"c.$displayed==true\"></span><span>&nbsp;</span><span>{{ c.label }}</span></a></div><div class=\"list-group\" ng-show=\"c.$displayed==true\" ng-repeat-end><div class=\"list-group-item\" ng-class=\"{active: isSelected(file)}\" ng-click=\"select(file)\" ng-repeat=\"file in (files | filter:{category: c.key} | orderBy:'name')\"><span class=\"badge alert-warning\" ng-show=\"isModified(file)\"><span class=\"fa fa-edit\"></span><span>&nbsp;</span><span>modifié</span></span><span class=\"fa fa-file-o\"></span><span>&nbsp;</span><span>{{ file.name }}</span></div></div></div></div><div class=\"col-md-9\"><div class=\"row\" ng-if=\"selectedFile\"><div class=\"col-md-9\"><h4>{{ selectedFile.name }}</h4></div><div class=\"col-md-3\"><button class=\"button save pull-right\" ng-click=\"save(selectedFile)\" type=\"button\" ng-disabled=\"!isModified(selectedFile)\"><span class=\"fa fa-save\"></span><span>&nbsp;</span><span>enregistrer</span></button></div></div><div class=\"editor\" ng-if=\"selectedFile\"><textarea ng-model=\"selectedFile.content\" ckeditor=\"editorOptions\"></textarea></div></div></div></div></div></div>");$templateCache.put('$/angular/views/admin/manage_advices.html', "<div class=\"actions advices-mngt\"><span><h1>{{'REDUCTION_ACTION_ADVICES_MANAGEMENT' | translateText }}</h1></span><br><table class=\"actions-table\"><thead><tr><th>{{ \"REDUCTION_ACTION_ADVICE_TABLE_DETAILS\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_ADVICE_TABLE_CALCULATOR\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_ADVICE_TABLE_TITLE\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_ADVICE_TABLE_TYPE\" | translateText }}</th></tr></thead><tbody><tr ng-repeat-start=\"a in actionAdvices\"><td class=\"details\"><mm-plus-minus-toggle-button ng-model=\"a.opened\"></mm-plus-minus-toggle-button></td><td class=\"calculator\"><span class=\"text\">{{ getInterfaceTypeLabel(a.interfaceTypeKey) }}</span></td><td class=\"title\"><span class=\"text\">{{ a.title }}</span></td><td class=\"type\"><span class=\"icon action_type_icon\" ng-class=\"{reducing: a.typeKey == '1', better_measure: a.typeKey == '2'}\"></span><span>&nbsp;&nbsp;</span><span class=\"text\">{{ getTypeLabel(a.typeKey) }}</span></td></tr><tr ng-show=\"a.opened\" ng-repeat-end class=\"details\"><td colspan=\"4\"><table style=\"width:100%\"><tr><td><table style=\"width:800px;\"><tr><td>{{ \"REDUCTION_ACTION_ADVICE_PHYSICAL_MEASURE_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-bind-html=\"a.physicalMeasure || '&amp;nbsp;'\"></div></td></tr><tr><td>{{ \"REDUCTION_ACTION_ADVICE_WEBSITE_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"!! a.webSite\"><a href=\"{{a.webSite}}\" target=\"blank\">{{ a.webSite }}</a></div><div class=\"box wide\" ng-hide=\"!! a.webSite\">&nbsp;</div></td></tr><tr class=\"with-separator\"><td>{{ \"REDUCTION_ACTION_ADVICE_BASE_INDICATORS_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"a.baseIndicatorAssociations.length &gt; 0\"><div ng-repeat=\"bia in a.baseIndicatorAssociations\"><span ng-bind-html=\"getBaseIndicatorLabel(bia.baseIndicatorKey) + ': ' + (bia.percent | numberToI18N) + ' % - ' + (bia.percentMax | numberToI18N) + ' %'\"></span></div></div><div class=\"box wide\" ng-hide=\"a.baseIndicatorAssociations.length &gt; 0\">&nbsp;</div></td></tr><tr class=\"with-separator\"><td>{{ \"REDUCTION_ACTION_ADVICE_COMMENT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-bind-html=\"a.comment || '&amp;nbsp;'\"></div></td></tr><tr><td>{{ \"REDUCTION_ACTION_ADVICE_RESPONSIBLE_PERSON_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\">{{ a.responsiblePerson || \"&nbsp;\" }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_ADVICE_ATTACHMENTS_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"a.files.length &gt; 0\"><div ng-repeat=\"f in a.files\"><a ng-href=\"/awac/file/download/{{ f.id }}\">{{ f.name }}</a></div></div><div class=\"box wide\" ng-hide=\"a.files.length &gt; 0\">&nbsp;</div></td></tr></table></td><td style=\"vertical-align:bottom; text-align:right;\"><button class=\"button\" ng-click=\"updateActionAdvice(a)\" type=\"button\"><span class=\"fa fa-pencil-square-o\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCTION_ACTION_ADVICE_BUTTON_EDIT' | translate\"></span></button><button class=\"button\" ng-click=\"confirmDelete(a)\" type=\"button\"><span class=\"fa fa-times\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCTION_ACTION_ADVICE_BUTTON_DELETE' | translate\"></span></button></td></tr></table></td></tr></tbody></table><br><button class=\"button\" ng-click=\"createActionAdvice()\" type=\"button\"><span class=\"fa fa-plus-circle\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCTION_ACTION_ADVICE_BUTTON_CREATE' | translate\"></span></button><!--button.button(type=\"button\", ng-click=\"exportActionAdvicesToXls()\")<span class=\"fa fa-file-excel-o\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCTION_ACTION_ADVICE_BUTTON_EXPORT_TO_XLS' | translate\"></span>--></div>");$templateCache.put('$/angular/views/admin/translation_sub_lists.html', "<div class=\"admin-translation\"><mm-admin-translations-navigation-bar></mm-admin-translations-navigation-bar><div class=\"translation-view\"><div class=\"translation-title\">Sous-listes</div><div class=\"waiting-data\" ng-show=\"waitingData\"><img src=\"/assets/images/modal-loading.gif\"><span>Veuillez patienter...</span></div><table class=\"translations-table\" ng-hide=\"waitingData\"><thead><tr><th>Détails</th><th>CodeList</th><th>CodeList référencée</th><th>Calculateur(s)</th></tr></thead><tbody><tr ng-repeat-start=\"subList in subLists\"><td class=\"details\"><mm-plus-minus-toggle-button ng-model=\"subList.$opened\"></mm-plus-minus-toggle-button></td><td class=\"codelist\"><span class=\"text\">{{ subList.codeList }}</span></td><td class=\"referencedCodeList\"><span class=\"text\">{{ subList.referencedCodeList }}</span></td><td class=\"calculators\"><span class=\"text\">{{ subList.calculators }}</span></td></tr><tr ng-show=\"subList.$opened\" ng-repeat-end class=\"details\"><td colspan=\"4\"><div class=\"sublist-header\"><div class=\"order-column\">#</div><div class=\"list-column\">{{ subList.referencedCodeList }}</div></div><div class=\"sublist-content\"><div class=\"sublist-items\" ui-sortable=\"sortableOptions\" ng-model=\"subList.items\"><div class=\"sublist-item\" ng-repeat=\"item in subList.items\"><div class=\"order-column\">{{ $index + 1 }}</div><div class=\"list-column\">{{ item.key + \" - \" + getLabelByKey(subList.referencedCodeList, item.key) }}</div></div></div><div class=\"sublist-add-item\">Ajouter un élément:<div class=\"sublist-item\"><div class=\"order-column\"><button class=\"button\" ng-click=\"addItem(subList)\" style=\"margin:0; min-height:22px; padding:0px 5px 2px 5px; border-radius:2px;\" type=\"button\"><span class=\"fa fa-plus-circle\" style=\"vertical-align:middle;\"></span></button></div><div class=\"list-column\"><select ng-model=\"itemToAdd.key\" ng-options=\"cl.key as cl.key + ' - ' + cl.label for cl in codeLabels[subList.referencedCodeList]\"></select></div></div></div></div></td></tr></tbody></table><div class=\"translation-actions\"><button class=\"button\" ng-click=\"save()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Enregistrer les modifications</button><button class=\"button\" ng-click=\"loadSubLists()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Annuler</button></div></div></div>");$templateCache.put('$/angular/views/admin/questionSet.html', "<div>{{params.label.key}}<ul><li ng-repeat=\"child in params.children\"><div ng-include=\"'$/angular/views/admin/questionSet.html'\" ng-init=\"params = questionSet\"></div></li></ul></div>");$templateCache.put('$/angular/views/admin/translation_base_lists.html', "<div class=\"admin-translation\"><mm-admin-translations-navigation-bar></mm-admin-translations-navigation-bar><div class=\"translation-view\"><div class=\"translation-title\">Listes de base</div><div class=\"waiting-data\" ng-show=\"waitingData\"><img src=\"/assets/images/modal-loading.gif\"><span>Veuillez patienter...</span></div><div class=\"translation-section\" ng-repeat=\"baseLists in allBaseLists\" ng-hide=\"waitingData\"><div class=\"section-title\">{{ groupsLabels[$index] }}</div><div class=\"section-content\"><table class=\"translations-table\" ng-hide=\"waitingData\"><thead><tr><th>Détails</th><th>CodeList</th><th ng-if=\"$index == 2\">Calculateur(s)</th></tr></thead><tbody><tr ng-repeat-start=\"baseList in baseLists\"><td class=\"details\"><mm-plus-minus-toggle-button ng-model=\"baseList.$opened\"></mm-plus-minus-toggle-button></td><td class=\"codelist\"><span class=\"text\">{{ baseList.codeList }}</span></td><td class=\"calculator\" ng-if=\"$parent.$index == 2\"><span class=\"text\">{{ baseList.calculators }}</span></td></tr><tr ng-show=\"baseList.$opened\" ng-repeat-end class=\"details\"><td colspan=\"{{($parent.$index == 2) ? 3 : 2}}\"><div class=\"baselist-header\"><div class=\"order-column\">#</div><div class=\"key-column\">Clé</div><div class=\"calculator_column\" ng-if=\"(baseList.codeList == 'INDICATOR') || (baseList.codeList == 'BASE_INDICATOR')\">Calculateur</div><div class=\"labels-columns\"><div class=\"label-column\">Libellé EN</div><div class=\"label-column\">Libellé FR</div><div class=\"label-column\">Libellé NL</div></div></div><div class=\"baselist-content\"><div class=\"baselist-items\" ui-sortable=\"sortableOptions\" ng-model=\"baseList.codeLabels\"><div class=\"baselist-item\" ng-repeat=\"codeLabel in baseList.codeLabels\"><div class=\"order-column\">{{ $index + 1 }}</div><div class=\"key-column\">{{ codeLabel.key }}</div><div class=\"calculator_column\" ng-if=\"(baseList.codeList == 'INDICATOR') || (baseList.codeList == 'BASE_INDICATOR')\">{{ getCalculatorByKeyPrefix(codeLabel.key) }}</div><div class=\"labels-columns\" ng-class=\"{'with-calculator-info': (baseList.codeList == 'INDICATOR') || (baseList.codeList == 'BASE_INDICATOR')}\"><div class=\"label-column\"><textarea ng-model=\"codeLabel.labelEn\"></textarea></div><div class=\"label-column\"><textarea ng-model=\"codeLabel.labelFr\"></textarea></div><div class=\"label-column\"><textarea ng-model=\"codeLabel.labelNl\"></textarea></div></div></div></div><div class=\"baselist-add-item\" ng-if=\"(baseList.codeList != 'ActivityCategory') &amp;&amp;  (baseList.codeList != 'ActivitySubCategory') &amp;&amp; (baseList.codeList != 'ActivitySource') &amp;&amp; (baseList.codeList != 'ActivityType')\">Ajouter un élément:<div class=\"baselist-item\"><div class=\"order-column\"><button class=\"button\" ng-click=\"addCodeLabel(baseList)\" style=\"margin:0; min-height:22px; padding:0px 5px 2px 5px; border-radius:2px;\" type=\"button\"><span class=\"fa fa-plus-circle\" style=\"vertical-align:middle;\"></span></button></div><div class=\"key-column\"><input ng-model=\"codeLabelToAdd.key\"></div><div class=\"labels-columns\"><div class=\"label-column\"><textarea ng-model=\"codeLabelToAdd.labelEn\"></textarea></div><div class=\"label-column\"><textarea ng-model=\"codeLabelToAdd.labelFr\"></textarea></div><div class=\"label-column\"><textarea ng-model=\"codeLabelToAdd.labelNl\"></textarea></div></div></div></div></div></td></tr></tbody></table></div></div><div class=\"translation-actions\"><button class=\"button\" ng-click=\"save()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Enregistrer les modifications</button><button class=\"button\" ng-click=\"undo()\" type=\"button\" ng-hide=\"waitingData\" ng-disabled=\"isLoading\">Annuler</button></div></div></div>");$templateCache.put('$/angular/views/admin/average.html', "<div class=\"verification_manage\"><span><h1>Statistiques</h1></span><div style=\"width:700px\"><div class=\"field_form\"><div class=\"field_row\"><div class=\"field_cell\">Periode</div><div class=\"field_cell\"><select ng-model=\"results.period\" ng-options=\"p.key as p.label for p in $root.periods\"></select></div></div><div class=\"field_row\"><div class=\"field_cell\">Calculateur</div><div class=\"field_cell\"><select ng-model=\"results.interface\" ng-options=\"interfaceName for interfaceName in interfaceNames\"></select></div></div><div class=\"field_row\" ng-show=\"results.interface == 'enterprise'\"><div class=\"field_cell\">NACE code</div><div class=\"field_cell\"><select ng-model=\"naceCode\" ng-options=\"p.key as p.label for p in naceCodes\"></select></div></div><div class=\"field_row\" ng-show=\"results.interface == 'enterprise' || results.interface == 'municipality'\"><div class=\"field_cell\">Uniquement les bilans cloturés</div><div class=\"field_cell\"><input ng-model=\"onlyVerifiedForm\" type=\"checkbox\"></div></div><div class=\"field_row\"><div class=\"field_cell\"><div ng-hide=\"isLoading\"><button class=\"button\" ng-click=\"askAverage()\" type=\"button\" ng-disabled=\"!allFieldValid()\">Calculer et envoyer les moyennnes par e-mail</button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div></div>");$templateCache.put('$/angular/views/admin/factors.html', "<div class=\"factors\"><br><button class=\"button btn save\" ng-click=\"save()\" type=\"button\"><span class=\"fa fa-save\"></span><span>&nbsp;</span><span ng-bind-html=\"'SAVE_BUTTON' | translate\"></span></button><button class=\"button\" ng-click=\"exportXls()\" type=\"button\"><span class=\"fa fa-file-excel-o\"></span><span>&nbsp;</span><span ng-bind-html=\"'FACTORS_BUTTON_EXPORT_TO_XLS' | translate\"></span></button><br><br><table class=\"factors-table\" show-filter=\"true\" ng-table=\"parameters\"><tr ng-class=\"{ 'row-modified': isModified(row) }\" ng-repeat-start=\"row in $data\"><td class=\"details\" data-title=\"\"><mm-plus-minus-toggle-button ng-model=\"a.opened\"></mm-plus-minus-toggle-button></td><td class=\"name\" filter=\"{ 'key': 'text' }\" data-title=\"'FACTORS_TABLE_KEY' | translateText\">{{ row.key }}</td><td class=\"type\" filter=\"{ 'typeString': 'text' }\" data-title=\"'FACTORS_TABLE_TYPE' | translateText\" sortable=\"'typeString'\">{{ row.typeString }}</td><td class=\"unit-category-in\" filter=\"{ 'unitCategoryIn': 'select' }\" filter-data=\"names('unitCategoryIn')\" data-title=\"'FACTORS_TABLE_UNIT_CATEGORY_IN' | translateText\">{{ row.unitCategoryIn }}</td><td class=\"origin\" filter=\"{ 'origin': 'text' }\" data-title=\"'FACTORS_TABLE_ORIGIN' | translateText\" sortable=\"'origin'\"><input ng-model=\"row.origin\" type=\"text\"></td></tr><tr ng-show=\"a.opened\" ng-repeat-end class=\"details\"><td colspan=\"5\"><table><tr ng-repeat=\"fv in row.factorValues | orderBy:'dateIn'\"><td><span ng-bind-html=\"'FACTORS_TABLE_DETAILS_FROM' | translate\"></span></td><td><select class=\"years\" ng-model=\"fv.dateIn\" ng-options=\"p.key as p.label for p in getAvailablePeriods(row, fv) | orderBy:'label'\" style=\"width: 100px; text-align: right\" ng-disabled=\"fv.dateIn == 2000\"></select></td><td><span ng-bind-html=\"'FACTORS_TABLE_DETAILS_VALUE' | translate\"></span></td><td><input class=\"align-right\" ng-model=\"fv.value\" type=\"text\" mm-force-numbers></td><td><span>tCO2e</span><span>&nbsp;/&nbsp;</span><span>{{ row.unitIn }}</span></td><td><button class=\"button btn btn-danger\" ng-click=\"removeFactorValue(row, fv)\" type=\"button\" ng-hide=\"fv.dateIn == 2000\"><i class=\"fa fa-times\"></i></button></td></tr></table><button class=\"button\" ng-click=\"addValue(row)\" type=\"button\"><span class=\"fa fa-plus-circle\"></span><span>&nbsp;</span><span ng-bind-html=\"'FACTORS_TABLE_DETAILS_BUTTON_CREATE_FACTOR_VALUE' | translate\"></span></button></td></tr></table><br><h3><span ng-bind-html=\"'FACTORS_CREATE_TITLE' | translate\"></span></h3><br><div ng-form name=\"form\"><table class=\"new-factor\"><tr><td><span ng-bind-html=\"'FACTORS_CREATE_TYPE' | translate\"></span></td><td><select ng-model=\"newFactor.indicatorCategory\" ng-options=\"p as (p | translateText) for p in newFactor.indicatorCategories\" required></select><span>&nbsp;/&nbsp;</span><select ng-model=\"newFactor.activityType\" ng-options=\"p as (p | translateText) for p in newFactor.activityTypes\" required></select><span>&nbsp;/&nbsp;</span><select ng-model=\"newFactor.activitySource\" ng-options=\"p as (p | translateText) for p in newFactor.activitySources\" required></select></td></tr><tr><td><span ng-bind-html=\"'FACTORS_CREATE_UNIT_CATEGORY_TYPE' | translate\"></span></td><td><select ng-model=\"newFactor.unitCategory\" ng-options=\"p as (p | translateText) for p in newFactor.unitCategories\" required></select></td></tr><tr><td><span ng-bind-html=\"'FACTORS_CREATE_VALUE_SINCE_2000' | translate\"></span></td><td><input class=\"valueSince2000\" ng-model=\"newFactor.valueSince2000\" type=\"number\" required></td></tr><tr><td><span ng-bind-html=\"'FACTORS_CREATE_ORIGIN' | translate\"></span></td><td><input class=\"origin\" ng-model=\"newFactor.origin\" type=\"text\"></td></tr></table><button class=\"button\" ng-click=\"createFactor()\" type=\"button\" ng-disabled=\"form.$invalid\"><span class=\"fa fa-plus-circle\"></span><span>&nbsp;</span><span ng-bind-html=\"'FACTORS_BUTTON_CREATE' | translate\"></span></button></div></div>");$templateCache.put('$/angular/views/admin/organization_data.html', "<div class=\"verification_manage\"><span><h1>Information sur les calculateurs</h1></span><table class=\"table\"><tr><th></th><th ng-repeat=\"(key,info) in data\" style=\"width:15%;text-align:center\">{{key}}</th></tr><tr><th>Français</th><td ng-repeat=\"(key,info) in data\" style=\"text-align:center\"><span ng-show=\"info.frEnabled\" style=\"color: darkgreen\">Activé</span><span style=\"color: darkred\" ng-hide=\"info.frEnabled\">Désactivé</span></td></tr><tr><th>Néerlandais</th><td ng-repeat=\"(key,info) in data\" style=\"text-align:center\"><span ng-show=\"info.nlEnabled\" style=\"color: darkgreen\">Activé</span><span style=\"color: darkred\" ng-hide=\"info.nlEnabled\">Désactivé</span><button class=\"button\" ng-click=\"toggleLanguageEnabled(key,'nl')\" type=\"button\"><i class=\"fa fa-exchange\"></i></button></td></tr><tr><th>Anglais</th><td ng-repeat=\"(key,info) in data\" style=\"text-align:center\"><span ng-show=\"info.enEnabled\" style=\"color: darkgreen\">Activé</span><span style=\"color: darkred\" ng-hide=\"info.enEnabled\">Désactivé</span><button class=\"button\" ng-click=\"toggleLanguageEnabled(key,'en')\" type=\"button\"><i class=\"fa fa-exchange\"></i></button></td></tr><tr><th>Nombre d'organisations enregistré</th><td ng-repeat=\"(key,info) in data\" style=\"text-align:center\">{{info.total}}</td></tr><tr><th>Nombre d'organisations partageant leurs données</th><td ng-repeat=\"(key,info) in data\" style=\"text-align:center\">{{info.shareData}}</td></tr><tr><th>Nombre de sites</th><td ng-repeat=\"(key,info) in data\" style=\"text-align:center\"><div ng-show=\"info.sites &gt; 0 \">{{info.sites}}</div></td></tr><tr><th>Nombre d'évènements</th><td ng-repeat=\"(key,info) in data\" style=\"text-align:center\"><div ng-show=\"info.products &gt; 0 \">{{info.products}}</div></td></tr><tr><th>Nombre de bilans cloturés</th><td ng-repeat=\"(key,info) in data\"><table class=\"actions-table\"><tr ng-repeat=\"(key,value) in info.closedForm\"><td>{{key}}</td><td>{{value}}</td></tr></table></td></tr><tr><th>Envoye d'emails</th><td ng-repeat=\"(key,info) in data\" style=\"text-align:center\"><button class=\"button\" ng-show=\"info.total &gt; 0\" ng-click=\"sendEmail(key,false)\" type=\"button\">Envoyer à toutes les organisations</button><br><br><button class=\"button\" ng-show=\"info.shareData &gt; 0\" ng-click=\"sendEmail(key,true)\" type=\"button\">Envoyer uniquement aux organisation partageant leur données</button></td></tr></table></div>");$templateCache.put('$/angular/views/municipality/TAB_C5.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><!--achat bien et service--><mm-awac-section title-code=\"AC130\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC130' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"AC131\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC132\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC132\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC132')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC133\" ng-map-index=\"{'AC132':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC134\" ng-map-index=\"{'AC132':$index}\" ng-condition=\"(getAnswer('AC133',itLevel1).value | stringToFloat) &lt; 18   || getAnswer('AC133',itLevel1).value == '23'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC135\" ng-map-index=\"{'AC132':$index}\" ng-condition=\"getAnswer('AC133',itLevel1).value == '20' || getAnswer('AC133',itLevel1).value == '21'|| getAnswer('AC133',itLevel1).value == '22'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC136\" ng-map-index=\"{'AC132':$index}\" ng-condition=\"getAnswer('AC133',itLevel1).value == '18' || getAnswer('AC133',itLevel1).value == '19'\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC132\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><!--investissement AC137--><mm-awac-section title-code=\"AC137\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC137' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"AC138\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC139\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC139\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC139')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC140\" ng-map-index=\"{'AC139':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC141\" ng-map-index=\"{'AC139':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC142\" ng-map-index=\"{'AC139':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC143\" ng-map-index=\"{'AC139':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC139\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/municipality/TAB_C2.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AC9\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC9' | translate\"></div></div><!--main loop--><mm-awac-repetition-name question-code=\"AC10\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC10\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC10')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC11\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC12\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"AC13\" ng-map-index=\"{'AC10':$index}\" ng-condition=\"getAnswer('AC12',itLevel1).value == '8'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"AC2002\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"AC2003\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"AC14\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"AC15\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"AC16\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"AC17\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"AC18\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC19\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC20\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC21\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC22\" ng-map-index=\"{'AC10':$index}\" ng-condition=\"getAnswer('AC19',itLevel1).value == '1' || getAnswer('AC19',itLevel1).value == '3'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"AC23\" ng-map-index=\"{'AC10':$index}\"></mm-awac-question><!--AC24--><mm-awac-sub-title question-code=\"AC24\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AC25\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"AC25\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC25', itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC26\" ng-map-index=\"{'AC25':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC27\" ng-map-index=\"{'AC25':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC25\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"AC900\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"AC900\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC900', itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC901\" ng-map-index=\"{'AC900':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC902\" ng-map-index=\"{'AC900':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC900\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"AC903\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"AC903\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC903', itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC904\" ng-map-index=\"{'AC903':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC905\" ng-map-index=\"{'AC903':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC903\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><!--AC28--><mm-awac-sub-title question-code=\"AC28\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"AC29\"></mm-awac-sub-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC30\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC31\"></mm-awac-question><!--AC32--><mm-awac-block ng-condition=\"getAnswer('AC19',itLevel1).value == '1' || getAnswer('AC19',itLevel1).value == '3'\"><mm-awac-sub-title question-code=\"AC32\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AC33\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"AC33\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC33', itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC34\" ng-map-index=\"{'AC33':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC35\" ng-map-index=\"{'AC33':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC36\" ng-map-index=\"{'AC33':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC33\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button></mm-awac-block><!--AC37--><mm-awac-sub-title ng-repetition-map=\"itLevel1\" question-code=\"AC37\"></mm-awac-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"AC38\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC39\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"AC39\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC39', itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC40\" ng-map-index=\"{'AC39':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC41\" ng-map-index=\"{'AC39':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC39\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><!--AC42--><mm-awac-block ng-condition=\"getAnswer('AC19',itLevel1).value == '1' || getAnswer('AC19',itLevel1).value == '3'\"><mm-awac-sub-title ng-repetition-map=\"itLevel1\" question-code=\"AC42\"></mm-awac-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"AC43\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC5000\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"AC5000\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('AC5000', itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC5001\" ng-map-index=\"{'AC5000':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"AC5002\" ng-map-index=\"{'AC5000':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC5000\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button></mm-awac-block></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC10\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><!--éclairage public AS52--><mm-awac-section title-code=\"AC52\"><mm-awac-question ng-optional=\"true\" question-code=\"AC53\"></mm-awac-question><mm-awac-sub-title question-code=\"AC2000\"></mm-awac-sub-title><mm-awac-question question-code=\"AC54\"></mm-awac-question><mm-awac-question question-code=\"AC55\"></mm-awac-question><mm-awac-sub-title question-code=\"AC2001\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AC56\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC56\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC56')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC57\" ng-map-index=\"{'AC56':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC58\" ng-map-index=\"{'AC56':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC59\" ng-map-index=\"{'AC56':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC56\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/municipality/TAB_C3.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><!--mobilite AC60--><mm-awac-section title-code=\"AC60\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC60' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"AC61\"></mm-awac-question><!--transport routier AC62--><mm-awac-sub-title question-code=\"AC62\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"AC400\"></mm-awac-sub-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><div class=\"status {{getStatusClassForTab(1,1)}}\"></div><span ng-bind-html=\"'AC402' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"AC403\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"AC404\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"AC405\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><div class=\"status {{getStatusClassForTab(1,2)}}\"></div><span ng-bind-html=\"'AC406' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC407\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC407\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC407')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"AC408\" ng-map-index=\"{'AC407':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"AC409\" ng-map-index=\"{'AC407':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"AC410\" ng-map-index=\"{'AC407':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"AC411\" ng-map-index=\"{'AC407':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC407\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(1,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,3)\"></i><div class=\"status {{getStatusClassForTab(1,3)}}\"></div><span ng-bind-html=\"'AC412' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC413\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC413\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC413')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"3\" question-code=\"AC414\" ng-map-index=\"{'AC413':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"3\" question-code=\"AC415\" ng-map-index=\"{'AC413':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"3\" question-code=\"AC416\" ng-map-index=\"{'AC413':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"3\" question-code=\"AC417\" ng-map-index=\"{'AC413':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC413\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div><mm-awac-sub-sub-title question-code=\"AC500\"></mm-awac-sub-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(2,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,1)\"></i><div class=\"status {{getStatusClassForTab(2,1)}}\"></div><span ng-bind-html=\"'AC502' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"2\" ng-tab=\"1\" question-code=\"AC503\"></mm-awac-question><mm-awac-question ng-tab-set=\"2\" ng-tab=\"1\" question-code=\"AC504\"></mm-awac-question><mm-awac-question ng-tab-set=\"2\" ng-tab=\"1\" question-code=\"AC505\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(2,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,2)\"></i><div class=\"status {{getStatusClassForTab(2,2)}}\"></div><span ng-bind-html=\"'AC506' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC507\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC507\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC507')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"2\" question-code=\"AC508\" ng-map-index=\"{'AC507':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"2\" question-code=\"AC509\" ng-map-index=\"{'AC507':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"2\" question-code=\"AC510\" ng-map-index=\"{'AC507':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"2\" question-code=\"AC511\" ng-map-index=\"{'AC507':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC507\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(2,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,3)\"></i><div class=\"status {{getStatusClassForTab(2,3)}}\"></div><span ng-bind-html=\"'AC512' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC513\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC513\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC513')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"3\" question-code=\"AC514\" ng-map-index=\"{'AC513':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"3\" question-code=\"AC515\" ng-map-index=\"{'AC513':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"3\" question-code=\"AC516\" ng-map-index=\"{'AC513':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"3\" question-code=\"AC517\" ng-map-index=\"{'AC513':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC513\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div><mm-awac-sub-sub-title question-code=\"AC600\"></mm-awac-sub-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(3,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,1)\"></i><div class=\"status {{getStatusClassForTab(3,1)}}\"></div><span ng-bind-html=\"'AC602' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"3\" ng-tab=\"1\" question-code=\"AC603\"></mm-awac-question><mm-awac-question ng-tab-set=\"3\" ng-tab=\"1\" question-code=\"AC604\"></mm-awac-question><mm-awac-question ng-tab-set=\"3\" ng-tab=\"1\" question-code=\"AC605\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(3,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,2)\"></i><div class=\"status {{getStatusClassForTab(3,2)}}\"></div><span ng-bind-html=\"'AC606' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC607\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC607\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC607')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"2\" question-code=\"AC608\" ng-map-index=\"{'AC607':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"2\" question-code=\"AC609\" ng-map-index=\"{'AC607':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"2\" question-code=\"AC610\" ng-map-index=\"{'AC607':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"2\" question-code=\"AC611\" ng-map-index=\"{'AC607':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC607\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(3,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,3)\"></i><div class=\"status {{getStatusClassForTab(3,3)}}\"></div><span ng-bind-html=\"'AC612' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"AC613\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC613\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC613')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"3\" question-code=\"AC614\" ng-map-index=\"{'AC613':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"3\" question-code=\"AC615\" ng-map-index=\"{'AC613':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"3\" question-code=\"AC616\" ng-map-index=\"{'AC613':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"3\" question-code=\"AC617\" ng-map-index=\"{'AC613':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC613\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div><!--transport public AC92--><mm-awac-sub-title question-code=\"AC92\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"AC93\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"AC94\"></mm-awac-question><mm-awac-question question-code=\"AC95\"></mm-awac-question><mm-awac-question question-code=\"AC96\"></mm-awac-question><mm-awac-question question-code=\"AC97\"></mm-awac-question><!--déplacements professionels AC98--><mm-awac-sub-title question-code=\"AC98\"></mm-awac-sub-title><mm-awac-question question-code=\"AC99\"></mm-awac-question><mm-awac-question question-code=\"AC100\"></mm-awac-question><mm-awac-question question-code=\"AC101\"></mm-awac-question><mm-awac-question question-code=\"AC102\"></mm-awac-question><mm-awac-question question-code=\"AC103\"></mm-awac-question><mm-awac-question question-code=\"AC104\"></mm-awac-question><mm-awac-question question-code=\"AC105\"></mm-awac-question><!--déplacement avion AC106--><mm-awac-sub-title question-code=\"AC106\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AC107\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC107\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC107')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC108\" ng-map-index=\"{'AC107':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC109\" ng-map-index=\"{'AC107':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC110\" ng-map-index=\"{'AC107':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC111\" ng-map-index=\"{'AC107':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC112\" ng-map-index=\"{'AC107':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC113\" ng-map-index=\"{'AC107':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC107\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/municipality/help_user_manager_fr.html', "<h1>Calculateur CO2 commune de l'AWAC: aide sur la gestion des utilisateurs</h1><h2>Introduction</h2><p>Accessible aux seuls utilisateurs avec un statut <b>administrateur</b>, cette interface permet de gérer l'ensemble des utilisateurs de l'organisation, en déterminant qui est administrateur et en invitant de nouveaux utilisateurs ou en en désactivant certains.</p><h2>Octroyer le statut administrateur</h2><p>Pour octroyer le statut d'administrateur, il suffit de cocher la case à cet effet à côté de son nom.</p><p><em>Note</em>: un administrateur ne peut jamais se défaire lui-même de son statut: un autre administrateur doit le faire pour lui, ce qui permet de s'assurer qu'il reste toujours un administrateur associé à l'organisation.</p><h2>Inviter un nouvel utilisateur</h2><img src=\"/assets/images/help/inviter_utilisateur.png\"><p>Un clic sur ce bouton ouvre une fenêtre dans laquelle il suffit de mentionner l'adresse courriel de l'utilisateur à inviter: il reçoit alors un courriel avec un lien pour s'enregistrer.</p><h2>Désactiver un utilisateur</h2><p>Pour désactiver un utilisateur, il suffit de décocher la case \"Actif\" à côté de son nom. Dès ce moment, l'utilisateur ne saura plus se connecter à l'application avec son identifiant. Aucune des données fournies par cet utilisateur n'est perdue. Elles restent accessibles aux autres utilisateurs, avec mention claire de ce qu'elles ont été fournies par leur ancien collègue.</p><img src=\"/assets/images/help/utilisateurs_actif.png\"><p><em>Note</em>: un administrateur ne peut jamais se désactiver lui-même: un autre administrateur doit le faire pour lui, ce qui permet de s'assurer qu'il reste toujours un administrateur associé à l'organisation.</p><p><em>Note 2</em>: Un utilisateur inactif peut être re-rendu actif à tout moment grâce au bouton vert qui est apparu à côté de son nom.</p>");$templateCache.put('$/angular/views/municipality/confidentiality_fr.html', "<h1>Calculateur CO2 commune de l'AWAC: gestion de la confidentialité de vos données</h1><h2>Introduction</h2><p>L'AWAC ne se considère a aucun moment être le propriétaire des données fournies via l'outil. Tant vous que les autres utilisateurs restez chaque fois les propriétaires des données que vous fournissez. L'AWAC en le gardien, et n'en fera aucun usage hormis ceux décrits ci-dessous.</p><h2>Protection de la vie privée</h2><p>Si vous nous fournissez des informations personnelles, elles seront traitées conformément aux dispositions de la loi du 8 décembre 1992. Les autres données seront traitées, moyennant votre accord (voir plus bas),  anonymement à des fins statistiques et pour vous adresser des messages électroniques généraux; elles ne seront pas communiquées à des tiers, à l’exception du sous-traitant avec lequel l’AWAC a conclu une convention relative à la confidentialité des données, ni utilisées à des fins commerciales. L’AWAC s'engage à prendre les meilleures mesures de sécurité afin d'éviter que des tiers n'abusent des données à caractère personnel qui lui ont été communiquées.</p><h2>Confidentialité de vos données</h2><p>Afin de garantir cette confidentialité, divers mécanismes techniques sont utilisés: la connexion au service s’établit par protocole https, avec certificat TSL/SSL1, à l’instar de ce qui se fait pour les transactions financières électroniques, et non pas simple protocole http. De plus, aucun mot de passe utilisateur ne circule en clair: ils sont systématiquement tous encryptés et seules les versions cryptées sont mémorisées.</p><h2>Usage statistique de vos données</h2><p>Un tel usage prend place uniquement si vous avez explicitement marqué votre accord, soit lors de la création de votre compte, soit à tout moment via l'interface de \"gérer l'organisation\" en activant le choix \"Permettre à l’AWAC d’inclure mes données dans son analyse statistique\".</p><p>Les données saisies en ligne et les résultats associés sont traités collectivement et anonymement. Ces données et résultats pourront alors être exploités, en totalité ou en partie, en vue de réalisations statistiques, de réalisations de rapports ou d’études relatives à la consommation d’énergie et d’émission de gaz à effet de serre.</p>");$templateCache.put('$/angular/views/municipality/help_results_fr.html', "<h1>Calculateur CO2 commune de l'AWAC: aide sur la présentation des résultats</h1><h2>Introduction</h2><p>La partie Résultats vous présente le bilan GES de votre commune, selon vos critères et présenté de différentes manières. Petit tour d'horizon des options qui s'ouvrent à vous.</p><h2>Résultat annuel ou comparaison</h2><p>Il vous permet de choisir si souhaitez voir le résultat pour une année seulement, ou au contraire comparer le résultat de deux années différentes, comme illustré ci-dessous.</p><img src=\"/assets/images/help/resultats_commune_comparaison.png\"><h2>Export du résultat</h2><p>Deux boutons, disponibles juste au-dessus des différentes manières de visualiser le résultat, vous permettent de télécharger celui-ci au format Excel ou PDF sur votre ordinateur. Dans chaque cas, après avoir cliqué sur le bouton, vous devrez patienter une bonne dizaine de secondes, le temps que le serveur assemble le document pour vous. Ensuite, votre navigateur sauvegardera le ficher demandé selon son mécanisme habituel (endroit habituel, avec ou sans pop-up, selon la configuration de votre navigateur).</p><p>Le ficher Excel contient juste le résultat brut et les données saisies, triées dans des cases différentes pour vous permettre de les re-travailler à votre guise et/ou de les mettre en page différement.</p><p>Le ficher PDF contient les données saisies, mais également tous les graphiques de présentation du résultat.</p><h2>Différentes vues</h2><p>Le résultat est présenté de différentes manières, chacune accesssible via les onglets sur le bord droit du navigateur. Voici les explications de l'objectif de chaque mode de présentation.</p><h3>Valeurs par rubrique</h3><p>Cette vue présente l'ensemble des rubriques du résultat sous forme de \"bar-chart\" (diagramme par bâtons), afin de mettre en évidence les impacts dominants de votre bilan GES. En mode de comparaison de deux années, les rubriques de chacune des deux années sont présentées côte à côte dans deux couleurs différentes.</p><h3>Répartition des impacts</h3><p>Cette vue présente les résultats scindés selon les 3 périmètres (scopes) de l'ISO 14064, dans des \"pie-charts\" (présentation en fromage). Cela permet de mettre en évidence les rubriques principales qui contribuent à chacun de ces périmètres.</p><h3>Diagramme araignée</h3><p>Cette vue est la même que celle des \"valeurs par rubrique\", mais sous forme de diagramme araignée, où chaque rubrique est représentée sur un axe différent. En cas de comparaison de deux années, cela permet de mieux visualiser les divergences entre les années.</p><h3>Chiffres</h3><p>Cette vue présente les résultats sous forme d'un tableau de chiffres.</p><h3>Comparaison à facteurs constants</h3><p>Cette vue présente à nouveau un \"bar-chart\" (diagramme par bâtons) comme les \"valeurs par rubrique\". Actif seulement en cas de comparaison de deux années, les données de l'année de comparaison ont cette fois étés multipliées par les facteurs d'émission de l'année de référence (et non pas par ceux de leur année de données, comme dans les autres vues). Cela permet de visualiser le changement dû seulement aux consommations engendrées par les données, indépendamment de tout changement de facteur d'émission.</p><h3>Explication du calcul</h3><p>Cette dernière rubrique, qui est également exportée tant en Excel qu'en PDF, vous permet de comprendre le raisonnement qui a mené au calcul du résultat qui vous est présenté. Elle vous présente chaque fois la valeur résultant du jeu de données fournies avec le facteur d'émission qui y a été appliqué, et la contribution au résultat qui en découle. Si certaines phrases apparaissent en rouge, cela signifie qu'un faceteur d'émission n'a pas été trouvé, et nous vous invitons alors à le signaler à l'AWAC.</p><h3>Convention des maires</h3><p>Cette vue vous présente les indicateurs nécessaires à remplir votre suivi annuel des engagements communaux liés à la convention des maires. Les résultats sont présentés en différents indicateurs scindés afin que vous puissiez reprendre les valeurs correspondant à votre approche (avec ou sans calcul LCA).</p><h2>Résultats vérifiés de manière externe</h2><p>Si une vérification a été effectuée (cf. pages principales), le rapport de vérification, s'il existe pour l'année sélectionnée, devient également disponible via la présente interface.</p>");$templateCache.put('$/angular/views/municipality/help_user_data_fr.html', "<h1>Calculateur CO2 commune de l'AWAC: aide sur le profil individuel</h1><p>Cette interface de gestion permet à chaque utilisateur, individuellement, de gérer son profil.</p><p>Il peut ainsi y modifier ses données personnelles (prénom, nonm et adresse courriel) ainsi que le mot de passe utilisés pour se connecter à l'application. L'identifiant n'est pas modifiable.</p>");$templateCache.put('$/angular/views/municipality/TAB_C1.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AC1\"><mm-awac-sub-title question-code=\"AC2\"></mm-awac-sub-title><mm-awac-question question-code=\"AC3\"></mm-awac-question><mm-awac-question question-code=\"AC6\"></mm-awac-question><mm-awac-question question-code=\"AC7\"></mm-awac-question><mm-awac-question question-code=\"AC8\"></mm-awac-question></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/municipality/help_actions_fr.html', "<h1>Calculateur CO2 commune de l'AWAC: aide sur les actions de réduction</h1><h2>Introduction</h2><p>En vous aidant à établir votre bilan de gaz à effet de serre, l'objectif du calculateur proposé par l'AWAC est de vous aider à définir des actions visant à réduire votre impact climatique. Comment?</p><p>Sur base du bilan établi, vous pouvez déterminer les parties les plus impactantes de vos activités, et ainsi choisir de vous y prendre autrement afin de réduire votre impact. C'est ce que l'on appelle une \"action de réduction\": un objectif qui vous est propre et que vous vous fixez.</p><p>Parfois, avant que de déterminer une action de réduction, il y a lieu de bien s'assurer de son impact, et pour ce faire d'affiner les données utilisées dans le bilan. Cela s'appelle une action de \"meilleure mesure\" des données.</p><p>Afin de vous aider dans ce travail, l'AWAC a également établi certains conseils, qui sont des actions relativement génériques et partiellement pré-remplies, que vous pouvez utiliser comme base pour vous définir une action. Lorsque c'est possible, le potentiel de gain CO2 associé au conseil a été calculé automatiquement par le système sur base de vos données.</p><h2>Créer une action</h2><img src=\"/assets/images/help/bouton_ajouter_action.png\"><p>Cliquez sur ce bouton pour créer une action que vous souhaitez mettre en oeuvre.</p><p>Cela ouvre une fenêtre qui vous permet de décrire l'action que vous souhaitez mettre en oeuvre: objectif, implications financières, responsable... Afin de mieux en tracer les détails opérationnels, vous avez également la possibilité d'associer un ou plusieurs fichiers à l'action.</p><h2>Exporter les actions</h2><img src=\"/assets/images/help/bouton_export_XLS.png\"><p>A tout moment, ce bouton vous permet d'exporter en format .xls l'ensemble de toutes vos actions afin de pouvoir l'utiliser hors outil de la manière qui vous convient le mieux pour suivre et implémenter ces actions.</p><h2>Consulter et modifier une action</h2><img src=\"/assets/images/help/details_actions.png\"><p>Pour relire l'ensemble des détails liés à une action, un simple clic sur le symbole + dans la colonne \"Détails\" fournit tous les détails de l'action choisie. En bas de ces détails, trois boutons vous permettent de re-travailler vos actions.</p><h3>Editer une action</h3><p>Ce bouton vous permet de re-modifier à l'envi tous les champs descriptifs de l'action</p><h3> Indiquer une action comme réalisée</h3><p>Le bouton \"Marquer une action comme réalisée\" permet de changer instantanément son statut en \"réalisé\" lors de l'année actuelle, sans devoir passer par le mode édition.</p><h3>Supprimer une action</h3><p>Contrairement au fait de passer une action en statut \"annulé\" pour ce souvenir de ce que l'action avait été identifiée mais s'est avérée infaisable, il est possible de purement et simplement supprimer une action, qui n'apparaîtra dès lors plus nulle part.</p><h2>Transformer un conseil en action</h2><p>Cliquez simplement sur le bouton \"En faire une action\", et une action basée sur le conseil sera créée pour vous. Libre ensuite à vous de la modifier comme n'importe quelle autre action que vous gérez.</p>");$templateCache.put('$/angular/views/municipality/TAB_C4.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><!--achat bien et service--><mm-awac-section title-code=\"AC114\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_AC114' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"AC115\"></mm-awac-question><mm-awac-repetition-name question-code=\"AC116\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AC116\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AC116')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC117\" ng-map-index=\"{'AC116':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC118\" ng-map-index=\"{'AC116':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC119\" ng-map-index=\"{'AC116':$index}\" ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_61'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC120\" ng-map-index=\"{'AC116':$index}\" ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_62'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC121\" ng-map-index=\"{'AC116':$index}\" ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_63'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC122\" ng-map-index=\"{'AC116':$index}\" ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_64'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC123\" ng-map-index=\"{'AC116':$index}\" ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_65'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC124\" ng-map-index=\"{'AC116':$index}\" ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_66'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC125\" ng-map-index=\"{'AC116':$index}\" ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_67'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC126\" ng-map-index=\"{'AC116':$index}\" ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_68'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC127\" ng-map-index=\"{'AC116':$index}\" ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_61' || getAnswer('AC118',itLevel1).value == 'AT_62' || getAnswer('AC118',itLevel1).value == 'AT_63' || getAnswer('AC118',itLevel1).value == 'AT_64'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC128\" ng-map-index=\"{'AC116':$index}\" ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_61' || getAnswer('AC118',itLevel1).value == 'AT_62' || getAnswer('AC118',itLevel1).value == 'AT_63' || getAnswer('AC118',itLevel1).value == 'AT_64' || getAnswer('AC118',itLevel1).value == 'AT_65' || getAnswer('AC118',itLevel1).value == 'AT_66' || getAnswer('AC118',itLevel1).value == 'AT_67'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AC129\" ng-map-index=\"{'AC116':$index}\" ng-condition=\"getAnswer('AC118',itLevel1).value == 'AT_68'\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AC116\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/municipality/agreement_en.html', "<h1>Je suis l'agreement commune EN</h1>");$templateCache.put('$/angular/views/municipality/help_organization_manager_fr.html', "<h1>Calculateur CO2 commune de l'AWAC: aide sur la gestion de l'organisation</h1><h2>Introduction</h2><p>Accessible aux seuls utilisateurs avec un statut <b>administrateur</b>, cette interface permet de gérer trois aspects au niveau général de votre organisation: son nom, le partage des données avec l'AWAC pour un usage statitstique (cf. confidetnailité) et les facteurs infulants le bilan GES.</p><h2>Nom de l'organisation</h2><p>Vous pouvez changer ici le nom global de votre organisation tel qu'il apparaît tout en haut du calculateur.</p><h2>Partage des données avec l'AWAC</h2><p>En accord avec les principes édictés dans la confidentialité de l'outil, c'est ici que vous pouvez décider de partager ou non vos données avec l'AWAC pour alimenter la représentativitié de ses statistiques.</p><h2>Facteurs influants sur la performance GES</h2><p>Comme indiqué par le texte explicatif présent dans l'interface, vous pouvez ici décrire les changements principaux survenus telle ou telle année, et qui expliquent des variations significatives de votre bilan d'une année sur l'autre.</p><p>Cet aspect est pour votre bonne compréhension interne, mais peut également s'avérer particulièrement utile lorsque vous demandez une vérification externe de vos données: le vérificateur aura en effet accès à ces facteurs afin de mieux comprendre la nature de votre bilan.</p>");$templateCache.put('$/angular/views/municipality/help_form_fr.html', "<h1>Calculateur CO2 commune de l'AWAC: aide générale</h1><h2>Introduction</h2><p>Le but du présent calculateur est de vous permettre de réaliser le bilan GES de votre organisation de la manière la plus simple possible tout en respectant le prescrit du GHG protocol. C'est pourquoi l'AWAC a sélectionné pour vous les bonnes questions à se poser, et pour lesquelles il vous \"suffit\" de fournir les données vous concernant afin d'obtenir un bilan GES valide. La présente page d'aide générale vous détaille d'abord l'ensemble des boutons disponibles dans les menus du haut de l'application, avant de détailler la manière de fournir les données nécessaires au calcul du bilan.</p><h2>Interface générale</h2><p>L'image ci-dessous vous présente le menu principal de l'application:</p><img src=\"/assets/images/help/commune_menu.png\"><p>Les annotations numérotées  correspondent aux explications ci-dessous:</p><p>1. Chaque utilisateur se connecte à l'application via son identifiant et son mot de passe. Il peut à tout moment modifier ses coordonnées personnelles via le menu \"Mon profil\".</p><p>2. Chaque utilisateur peut demander à avoir les interfaces et les questions liées à la collecte de données dans la langue de son choix parmi les langues disponibles dans l'application.</p><p>3. Tout administrateur peut également \"Gérer les utilisateurs\" pour les rendre administrateurs, inviter de nouveaux utilisateurs ou en désactiver certains.</p><p>4. Et les administrateurs peuvent également modifier certains paramètres d'organisation via le bouton \"Gérer l'organisation\".</p><p>5. L'utilisateur choisit alors pour quelle année il souhaite fournir des données.</p><p>6. A tout moment, il peut enregistrer les données fournies. Pour chaque formulaire thématique de données (voir plus bas), l'application indique le moment du dernier enregitrement effectué.</p><p>7. L'utilisateur a aussi la possibilité de comparer les données avec celle d'une autre année, ce qui peut facilité le recopiage des données restées similaires (voir plus bas).</p><p>8. Les administrateurs peuvent également demander une vérification externe des données (voir plus bas).</p><h2>Fournir des données</h2><p>Les données à fournir sont divisées en plusieurs parties distinctes. Vous accédez à chaque partie en cliquant sur son titre.</p><img src=\"/assets/images/help/commune_forms.png\"><p></p><p>La partie sélectionnée est présentée sur fond vert. Pour chaque partie, une barre de progression vous indique quelle fraction des données escomptées vous avez déjà remplie.</p><p>Tout à droite, la partie \"résultats\" vous donne accès à la présentation du bilan CO2 en fonction des données que vous avez remplies, et la partie \"actions de réduction\" vous permet de définir de telles actions pour améliorer votre bilan d'année en année.</p><h3>Types de données</h3><p>Chaque utilisateur impliqué est invité à fournir les données qu'il maîtrise. Pour ce faire, il lui suffit de remplir les cases faites pour. Plusieurs types de données différentes sont indiquées avec des icones différentes:</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-share-alt\"></span> indique des questions dont les réponses sont nécessaires au calcul du résultat.</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-paperclip\"></span> indique à l'inverse des questions qui ne servent pas au résultat mais uniquement à vous aider à mémoriser le contexte.</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-file\"></span> indique des questions documentaires qui vous permettent de stocker vos documents relatifs à certains jeux de données, afin de pouvoir les re-consulter à tout moment.</p><p><span class=\"glyphicon glyphicon-record\"></span> indique des questions regroupées en un bloc qu'il vous appartient de répliquer autant de fois que nécessaire (voir ci-dessous, création de blocs de données)</p><p></p><p>Une icone est encore présente derrière la donnée: <button class=\"button edit_comment_icon glyphicon glyphicon-pencil\"></button> Il s'agit d'un champs de commentaires libres auquel vous accéder en cliquant dessus: cela vous permet de noter des aspects compélmentaires à la donnée fournie. Lorsqu'un commentaire est effectivement présent, cette icône devient verte: <button class=\"button edit_comment_icon glyphicon glyphicon-pencil edit_comment_icon_selected\"></button></p><p>Finalement, certaines questions sont également suivies par un symbole:</p><img src=\"/assets/images/info.png\"><p>Il s'agit d'informations complémentaires sur la question qui apparaissent en infobulle lorsqu'on passe la souris sur cette icône.</p><h3>Remplissage des données</h3><p>Pour toutes les questions attendues par le système pour offrir un résultat le plus exhaustif possible, une icône sous forme de main vient donner une indication complémentaire:</p><img src=\"/assets/images/status/pending.png\"><p>indique une question attendue mais pas encore répondue;</p><img src=\"/assets/images/status/answer_temp.png\"><p>indique une question répondue mais pas encore sauvée;</p><img src=\"/assets/images/status/answer.png\"><p>indique une question répondue et dont la réponse est bien enregistrée;</p><img src=\"/assets/images/status/pending_temp.png\"><p>indique une question dont on a effacé la réponse, mais pas encore sauvegardé ce changement.</p><p>Pour remplir la donnée, il suffit d'encoder celle-ci dans la case blanche. Une fois la donnée enregistrée, une case avec des initiales indique quel utilisateur a fourni cette donnée. Si on laisse la souris dessus, le nom complet de la personne apparaît.</p><img src=\"/assets/images/help/question_repondue.png\"><h3>Création de blocs de données</h3><p>Comme énoncé plus haut, le symbole <span class=\"glyphicon glyphicon-record\"></span> indique des questions regroupées en un bloc qu'il vous appartient de répliquer autant de fois que nécessaire.</p><img src=\"/assets/images/help/bloc_donnees.png\"><p>Dans de tels cas, le bouton \"Ajouter...\" vous permet de créer autant de blocs de données identiques que nécessaire dans votre cas. A tout moment, le bouton \"Supprimer\" vous permet d'effacer un de ces blocs.</p><h3>Comparaison des données d'une autre année et recopiage d'une année à l'autre</h3><p>Dès que vous choisissez de comparer les données avec celle d'une autre année (cf. point 7 de l'introduction du menu principal), les données passent en deux colonnes pour vous permettre de comparer, donnée par donnée, les deux années.</p><img src=\"/assets/images/help/comparaison_donnees.png\"><p>Le bouton <button class=\"button\" title=\"Copier la valeur\" >&lt;&lt;</button> vous permet de recopier d'un seul clic la valeur de l'année de référence dans la case de l'année en cours.</p><p>Dans le cas de blocs de données (cf. ci-dessus), il vous faut d'abord créer le bloc pour l'année en cours, et à ce moment-là les données de l'année de référence viendront se placer à côté et seront recopiables.</p><h3>Données alternatives</h3><p>A plusieurs endroits, des méthodes différentes existent pour collecter les données nécessaires. Elles vous sont chaque fois présentées de la meilleure, en vert clair, à la plus approximative (vert foncé, puis jaune, puis orange). L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Pour le calcul du résultat, le système utilisera les données de la meilleure méthode entièrement complétée, ce qui est indiqué par le symbole <i class=\"glyphicon glyphicon-bell\"></i>.</p><img src=\"/assets/images/help/alternative.png\"><p>Dans l'exemple ci-dessus, les approches \"calcul par euros dépensés\" et \"calcul par les kilomètres\" ont été entièrement remplies, tandis que l'approche \"calcul par les consommations\" n'est pas complet. Le système indique donc qu'il utilisera les données de l'approche \"calcul par les kilomètres\". S'il n'y a pas de symbole <i class=\"glyphicon glyphicon-bell\"></i>, cela signifie qu'aucune des alternatives n'est complète et que le système ne saura pas prendre cette partie en compte dans le résultat.</p><h2>Travailler à plusieurs</h2><p>Afin de  faciliter la collecte des données nécessaires à un bilan complet, il est souvent plus efficace de pouvoir travailler à plusieurs collègues. C'est pourquoi plusieurs utilisateurs peuvent être invités sur le compte de l'organsation par le ou les administrateur(s). Il est toutefois important qu'un utilisateur n'annule pas le travail des autres. Divers mécanismes sont prévus pour cela.</p><p>Tout d'abord, il est impossible pour deux utilisateurs d'accéder en même temps à la même partie des données. Si un autre utilisateur est déjà en train de travailler sur une partie, vous recevrez un message vous y refusant l'accès en vous annonçant qui y travaille déjà.</p><p>Ensuite, deux mécanismes complémentaires permettent de gérer la collaboration.</p><h3>Se réserver des données</h3><p>Le cadenas qui figure en dessous de chaque rubrique de données permet à un utilisateur de bloquer l'ensemble des données de la rubrique pour son seul usage.</p><img src=\"/assets/images/help/localisation_cadenas.png\"><p>Seul cet utilisateur aura encore la possibilité de travailler sur ces données, tandis que les autres utilisateurs verront un cadenas fermé (le nom de l'utilisateur l'ayant fermé apparaît lorsque la souris est passée sur le cadenas).</p><p>L'utilisateur qui a verrouillé peut rouvrir le cadenas à tout moment en cliquant dessus.</p><h3>Valider des données</h3><p>En dessous du cadenas figure une autre icône dont le principe de fonctionnement est similaire.</p><img src=\"/assets/images/status/validate_not.png\"><p>Cette icône permet à un utilisateur de valider l'ensemble des données de la rubrique. C'est-à-dire d'indiquer que ces données ont été relues, sont correctes, et ne peuvent dès lors plus être modifiées. Dès lors, plus personne, cet utilisateur inclus, ne peut modifier les données concernées.</p><p><em>Note</em>: un administrateur peut toujours, à n'importe quel moment, annuler le cadenas ou la validation de n'importe quel utilisateur.</p><h2>Faire vérifier les données de manière externe</h2><p>Encore en dessous des icônes de validation, figure une icône de vérification:</p><img src=\"/assets/images/status/verified_unverified.png\"><p>Il s'agit d'indique le statut lorsque les données ont été vérifiées par une personne externe, typiquement un auditeur.</p><p>Pour pouvoir demander une vérification externe, il faut d'abord que toutes les données ait étées validées en interne (cf. ci-dessus).</p><img src=\"/assets/images/help/bouton_verification.png\"><p>Le bouton de demande de vérification devient alors vert pour indiquer qu'il est actif. Seuls les administrateurs le verront apparaître dans leur interface, et pour toute action qu'ils souhaiteront prendre eu égard à la vérification externe, ils devront chaque fois la confirmer avec leur mot de passe.</p><img src=\"/assets/images/help/demander_verification.png\"><p>Cliquer sur le bouton ouvre une fenêtre qui permet de demander la vérification à une personne externe en fournissant l'adresse de courrier électronique de la dite personne. Il appartiendra ensuite à cette personne de réagir et d'accepter le travail qui lui est demandé. Le suivi du processus se fait de manière très simple: à chaque fois qu'une action est nécessaire de la part de votre organisation, le ou les administrateur(s) du compte reçoivent un courriel leur expliquant le statut et la marche à suivre. En effet, ce bouton qui indique intialement \"Demander une vérification\" va changer de texte au fur et à mesure que le processus avance.</p><p>Tout à la fin du processus, si tout s'est bien passé, le rapport de vérification deviendra alors disponible dans la partie \"résultats\".</p>");$templateCache.put('$/angular/views/municipality/help_site_manager_fr.html', "<h1>Calculateur CO2 commune de l'AWAC: aide sur la gestion des sites</h1><p>NE DEVRAIT PAS EXISTER !!!</p>");$templateCache.put('$/angular/views/municipality/agreement_fr.html', "<h1>Mentions légales du calculateur CO2 commune de l'AWAC</h1><h2> Information juridique - Conditions d'utilisation du site</h2><p>L'utilisation du présent site est soumise au respect des conditions générales décrites ci-après. En accédant à ce site, vous déclarez avoir pris connaissance et avoir accepté, sans la moindre réserve, ces conditions générales d'utilisation.</p><h2>Qualité de l'information et du service – Clause de non-responsabilité</h2><p>Nous apportons le plus grand soin à la gestion de ce site. Nous ne garantissons toutefois pas l'exactitude des informations qui y sont proposées. Celles-ci sont par ailleurs susceptibles d'être modifiées sans avis préalable. Il en résulte que nous déclinons toute responsabilité quant à l'utilisation qui pourrait être faite du contenu de ce site. Les liens hypertextes présents sur le site et aiguillant les utilisateurs vers d'autres sites Internet n'engagent pas notre responsabilité quant au contenu de ces sites.</p><h2>Protection des données à caractère personnel</h2><p>Il vous est loisible de visiter notre site internet sans nous fournir de données à caractère personnel (1). Vous pouvez accéder à la base de calcul et de résultats en ne saisissant que des données générales à caractère non personnel.</p><p>Si vous nous fournissez des informations personnelles (2), elles seront traitées conformément aux dispositions de la loi du 8 décembre 1992. Elles seront traitées, moyennant votre accord,  anonymement à des fins statistiques et pour vous adresser des messages électroniques généraux; elles ne seront pas communiquées à des tiers, à l’exception du sous-traitant avec lequel l’AWAC a conclu une convention relative à la confidentialité des données, ni utilisées à des fins commerciales.  L’AWAC s'engage à prendre les meilleures mesures de sécurité afin d'éviter que des tiers n'abusent des données à caractère personnel qui lui ont été communiquées.</p><p>Les données saisies en ligne et les résultats associés sont traités collectivement et anonymement. Ces données et résultats pourront être exploités, en totalité ou en partie, en vue de réalisations statistiques, de réalisations de rapports ou d’études relatives à la consommation d’énergie et d’émission de gaz à effet de serre.</p><h2>Droits de propriété intellectuelle</h2><p>Tous droits sur le contenu et l'architecture de notre site, et notamment les éléments suivants : textes, créations graphiques, logos, éléments sonores, publications, mises en page, bases de données, algorithmes de calculs... sont protégés par le droit national et international de la propriété intellectuelle.</p><p>A ce titre, vous ne pouvez procéder à une quelconque reproduction, représentation, adaptation, traduction et/ou transformation partielle ou intégrale, ou un transfert sur un autre site web de tout élément composant le site.</p><p>Cependant si vous souhaitez utiliser les éléments disponibles sur notre site, nous vous invitons à adresser votre demande d’autorisation à l’AWAC en précisant les éléments à reproduire et l’utilisation souhaitée.</p><h2>Création d'hyperliens vers le site</h2><p>Nous autorisons la création sans demande préalable de liens en surface (surface linking) qui renvoient à la page d'accueil du site ou à toute autre page dans sa globalité. Par contre, le recours à toutes techniques visant à inclure tout ou partie du site dans un site Internet en masquant ne serait-ce que partiellement l'origine exacte de l'information ou pouvant prêter à confusion sur l'origine de l'information, telle que le framing ou l'inlining, requiert une autorisation écrite.</p><h2>Modification des mentions légales</h2><p>L’AWAC se réserve le droit de modifier les présentes mentions à tout moment. L’utilisateur est lié par les conditions en vigueur lors de sa visite.</p><h2>Droit applicable en cas de litige</h2><p>Le droit applicable est le droit belge.</p><p>Tout litige relèvera de la compétence exclusive des tribunaux de Namur.</p><h3>Personne de Contact</h3><p>Cécile Batungwnanayo</p><p>Agence wallone de l'air et du Climat (AWAC)</p><p>7, Avenue Prince de Liège</p><p>B-5100 Jambes</p><P>Belgium</P><h2>Notes</h2><p>(1) La loi du 8 décembre 1992 relative au traitement de données à caractère personnel entend par « données à caractère personnel », toute information concernant une personne physique identifiée ou identifiable […] ; est réputée identifiable une personne qui peut être identifiée, directement ou indirectement, notamment par référence à un numéro d’identification ou à un plusieurs éléments spécifiques, propres à son identité physique, physiologique, psychique, économique, culturelle ou sociale. »</p><p>(2) Pour les personnes physiques, via la création d’un espace personnel sur le site avec votre e-mail et un mot de passe.  Si votre adresse e-mail est constituée de votre nom de famille, elle peut permettre votre identification.</p>");$templateCache.put('$/angular/views/actions.html', "<div class=\"actions advices\"><h1>{{ \"REDUCTION_ACTION_ADVICES_TITLE\" | translateText }}</h1><br><div class=\"desc\" ng-bind-html=\"'REDUCTION_ACTION_ADVICES_DESC_HOUSEHOLD_LE' | translate\" ng-if=\"$root.instanceName == 'littleemitter' || $root.instanceName == 'household'\"></div><div class=\"desc\" ng-bind-html=\"'REDUCTION_ACTION_ADVICES_DESC_EVENT' | translate\" ng-if=\"$root.instanceName == 'event'\"></div><div class=\"waiting-data\" ng-show=\"waitingAdvices\"><img src=\"/assets/images/modal-loading.gif\"><span>Recherche en cours...</span></div><table class=\"actions-table\" ng-hide=\"waitingAdvices\"><thead><tr><th>&nbsp;</th><th ng-bind-html=\"'REDUCTION_ACTION_ADVICE_TABLE_TITLE' | translate\"></th><th ng-bind-html=\"'REDUCTION_ACTION_ADVICE_TABLE_TYPE' | translate\"></th><th ng-bind-html=\"'REDUCTION_ACTION_ADVICE_TABLE_COMPUTED_GHG_BENEFIT' | translate\"></th></tr></thead><tbody><tr ng-repeat=\"advice in actionAdvices\"><td class=\"details\"><button class=\"button\" ng-click=\"createActionFromAdvice(advice)\" type=\"button\"><span class=\"text\" ng-bind-html=\"'REDUCTION_ACTION_ADVICE_BUTTON_CREATE_ACTION_FROM_ADVICE' | translate\"></span></button></td><td class=\"title\"><span class=\"text\" ng-bind-html=\"advice.title\"></span><div class=\"field_info\" ng-if=\"!!advice.webSite || !!advice.physicalMeasure\"><div class=\"field_info_popup\"><div ng-bind-html=\"advice.physicalMeasure\" ng-if=\"!!advice.physicalMeasure\"></div><div ng-if=\"!!advice.webSite\"><span ng-bind-html=\"'REDUCTION_ACTION_ADVICE_MORE_INFO' | translate\"></span><span>&nbsp;</span><b><a href=\"{{advice.webSite}}\" title=\"{{advice.webSite}}\" target=\"blank\">{{ \"REDUCTION_ACTION_ADVICE_MORE_INFO_HERE\" | translateText }}</a></b></div></div></div></td><td class=\"type\"><span class=\"icon action_type_icon\" ng-class=\"{reducing: advice.typeKey == '1', better_measure: advice.typeKey == '2'}\"></span><span>&nbsp;&nbsp;</span><span class=\"text\">{{ getTypeLabel(advice.typeKey) }}</span></td><td class=\"ghgBenefit\"><span ng-show=\"advice.typeKey == '1'\">{{ (advice.computedGhgBenefit | numberToI18N) + \"&nbsp;\" + getGwpUnitSymbol(advice.computedGhgBenefitUnitKey) + \"&nbsp;&#8210;&nbsp;\" + (advice.computedGhgBenefitMax | numberToI18N) + \"&nbsp;\" + getGwpUnitSymbol(advice.computedGhgBenefitMaxUnitKey) }}</span><span ng-hide=\"advice.typeKey == '2'\">&nbsp;</span><!--.field_info<div class=\"field_info_popup\"><div ng-if=\"!!advice.physicalMeasure\">{{ advice.physicalMeasure }}</div><div ng-if=\"!!advice.webSite\"><span ng-bind-html=\" 'REDUCTION_ACTION_ADVICE_MORE_INFO' | translate\"></span><span>:&nbsp</span><a href=\"{{advice.webSite}}\" target=\"blank\">{{ advice.webSite }}</a></div></div>--></td></tr></tbody></table></div><div class=\"actions\"><span><h1>{{'REDUCTION_ACTIONS' | translateText }}</h1></span><br><table class=\"actions-table\"><thead><tr><th>{{ \"REDUCTION_ACTION_TABLE_DETAILS\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_ACTIONS\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_TYPE\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_SCOPE\" | translateText }}</th><th>{{ \"REDUCTION_ACTION_TABLE_COMPLETION\" | translateText }}</th></tr></thead><tbody><tr ng-repeat-start=\"a in actions\"><td class=\"details\"><mm-plus-minus-toggle-button ng-model=\"a.opened\"></mm-plus-minus-toggle-button></td><td class=\"title\"><span class=\"text\" ng-bind-html=\"a.title\"></span></td><td class=\"type\"><span class=\"icon action_type_icon\" ng-class=\"{reducing: a.typeKey == '1', better_measure: a.typeKey == '2'}\"></span><span>&nbsp;&nbsp;</span><span class=\"text\">{{ getTypeLabel(a.typeKey) }}</span></td><td class=\"scope\"><span class=\"icon scope_type_icon\" ng-class=\"{org: a.scopeTypeKey == '1', site: a.scopeTypeKey == '2'}\"></span><span>&nbsp;&nbsp;</span><span class=\"text\">{{ getScopeName(a.scopeTypeKey, a.scopeId) }}</span></td><td class=\"realization\"><span class=\"icon action_done_icon\" ng-show=\"a.statusKey == '2'\"></span><span>&nbsp;&nbsp;</span><span class=\"text\">{{ (a.statusKey == \"2\") ? (a.completionDate | date : \"yyyy\") : getStatusLabel(a.statusKey) }}</span></td></tr><tr ng-show=\"a.opened\" ng-repeat-end class=\"details\"><td colspan=\"5\"><table style=\"width:100%\"><tr><td><table style=\"width:800px;\"><tr><td>{{ \"REDUCTION_ACTION_WEBSITE_FIELD_TITLE\" | translateText }}:</td><td><a href=\"{{a.webSite}}\" ng-if=\"a.webSite != null\" target=\"blank\">{{ a.webSite }}</a></td></tr><tr><td>{{ \"REDUCTION_ACTION_PHYSICAL_MEASURE_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-bind-html=\"a.physicalMeasure || '&amp;nbsp;'\"></div></td></tr><tr ng-if=\"a.typeKey == '1'\"><td>{{ \"REDUCTION_ACTION_GHG_BENEFIT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"!!a.ghgBenefit\">{{ (a.ghgBenefit | numberToI18N) + \"&nbsp;\" + getGwpUnitSymbol(a.ghgBenefitUnitKey) }}</div><div class=\"box wide\" ng-hide=\"!!a.ghgBenefit\">&nbsp;</div></td></tr><tr ng-if=\"a.typeKey == '1'\"><td>{{ \"REDUCTION_ACTION_GHG_BENEFIT_MAX_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"!!a.ghgBenefitMax\">{{ (a.ghgBenefitMax | numberToI18N) + \"&nbsp;\" + getGwpUnitSymbol(a.ghgBenefitMaxUnitKey) }}</div><div class=\"box wide\" ng-hide=\"!!a.ghgBenefitMax\">&nbsp;</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_FINANCIAL_BENEFIT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"!!a.financialBenefit\">{{ (a.financialBenefit | numberToI18N) + \"&nbsp;EUR\" }}</div><div class=\"box wide\" ng-hide=\"!!a.financialBenefit\">&nbsp;</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_INVESTMENT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"!!a.investmentCost\">{{ (a.investmentCost | numberToI18N) + \"&nbsp;EUR\" }}</div><div class=\"box wide\" ng-hide=\"!!a.investmentCost\">&nbsp;</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_EXPECTED_PAYBACK_TIME_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\">{{ a.expectedPaybackTime || \"&nbsp;\" }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_DUE_DATE_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\">{{ a.dueDate | date: 'dd/MM/yyyy' }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_RESPONSIBLE_PERSON_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\">{{ a.responsiblePerson || \"&nbsp;\" }}</div></td></tr><tr><td>{{ \"REDUCTION_ACTION_COMMENT_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-bind-html=\"a.comment || '&amp;nbsp;'\"></div></td></tr><tr><td>{{ \"REDUCTION_ACTION_ATTACHMENTS_FIELD_TITLE\" | translateText }}:</td><td><div class=\"box wide\" ng-show=\"a.files.length &gt; 0\"><div ng-repeat=\"f in a.files\"><a ng-href=\"/awac/file/download/{{ f.id }}\">{{ f.name }}</a></div></div><div class=\"box wide\" ng-hide=\"a.files.length &gt; 0\">&nbsp;</div></td></tr></table></td><td style=\"vertical-align:bottom; text-align:right;\"><!--td(style=\"vertical-align: bottom; text-align: right\", colspan=\"4\")--><button class=\"button\" ng-click=\"updateAction(a)\" type=\"button\"><span class=\"fa fa-pencil-square-o\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_EDIT' | translate\"></span></button><button class=\"button\" ng-click=\"confirmDelete(a)\" type=\"button\"><span class=\"fa fa-times\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_DELETE' | translate\"></span></button><button class=\"button\" ng-show=\"a.statusKey != '2'\" ng-click=\"markActionAsDone(a)\" type=\"button\"><span class=\"fa fa-check-square-o\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_MARK_AS_DONE' | translate\"></span></button></td></tr></table></td></tr></tbody></table><br><button class=\"button\" ng-click=\"createAction()\" type=\"button\"><span class=\"fa fa-plus-circle\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_CREATE' | translate\"></span></button><button class=\"button\" ng-click=\"exportActionsToXls()\" type=\"button\"><span class=\"fa fa-file-excel-o\"></span><span>&nbsp;</span><span ng-bind-html=\"'REDUCING_ACTION_BUTTON_EXPORT_TO_XLS' | translate\"></span></button></div>");$templateCache.put('$/angular/views/verification_registration.html', "<div class=\"loginBackground\"><div class=\"router-bar\"><div class=\"awac_logo\"></div></div><div class=\"registrationFrame\" ng-enter=\"enterEvent()\"><select ng-model=\"$root.language\" ng-options=\"l.value as l.label for l in $root.languages\" style=\"float:right\"></select><tabset><tab class=\"tab-color-lightgreen\" active=\"tabActive[0]\"><tab-heading><span ng-bind-html=\"'VERIFICATION_REGISTRATION' | translate\"></span></tab-heading><div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.emailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordConfirmInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.organizationNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\" ng-bind-html=\"'REGISTRATION_AGREEMENT_VALIDATION' | translateText\"></div><div class=\"field_cell\"><div><input ng-model=\"fields.agreementValidation.isValid\" style=\"width: 18px;height: 18px;\" type=\"checkbox\"><button class=\"button btn btn-primary\" ng-click=\"displayAgreement()\" ng-bind-html=\"'REGISTRATION_AGREEMENT_CONSULTATION' | translate\" type=\"button\"></button></div></div><div class=\"field_cell\"><img ng-show=\"fields.agreementValidation.isValid == true\" src=\"/assets/images/field_valid.png\"><div class=\"error_message\" ng-hide=\"fields.agreementValidation.isValid == true\"><img src=\"/assets/images/field_invalid.png\"><div ng-bind-html=\"'ACCEPT_AGREEMENT' | translateText\"></div></div></div></div><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"registration()\" ng-bind-html=\"'REGISTRATION_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></tab></tabset></div></div>");$templateCache.put('$/angular/views/admin.html', "<div><h1>Admin</h1><tabset style=\"margin-top:20px\"><tab class=\"tab-color-lightgreen\"><tab-heading style=\"margin-left:25px\"><span ng-bind-html=\"'BAD Importer'\"></span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><mm-awac-admin-bad-importer></mm-awac-admin-bad-importer></div></div></tab><tab class=\"tab-color-lightgreen\"><tab-heading><span ng-bind-html=\"'Other .... '\"></span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><div>something</div></div></div></tab></tabset></div>");$templateCache.put('$/angular/views/results.html', "<div class=\"results pdf-able\"><h1><span ng-bind-html=\"'RESULTS' | translate\"></span></h1><table class=\"wide\"><tr><td class=\"top-aligned\"><div class=\"sites-panel\" ng-hide=\"$root.instanceName == 'municipality' || $root.instanceName == 'household' || $root.instanceName == 'littleemitter'\"><div class=\"sites-panel-title\"><span ng-bind-html=\"($root.instanceName == 'event' ? 'EVENTS_LIST' : 'SITES_LIST') | translate\"></span></div><!--<div class=\"sites-panel-all-items\"><table><tr><td><span ng-bind-html=\"'ALL_SITES_SELECTED' | translate\"></span></td><td><input type=\"checkbox\"></td></tr></table></div>--><div class=\"sites-panel-items\"><div class=\"sites-panel-item\"><table><tr ng-repeat=\"site in mySites\"><td>{{ site.name }}</td><td><input ng-model=\"site.selected\" type=\"checkbox\"></td></tr></table></div></div></div><div class=\"sites-panel\" ng-hide=\"$root.instanceName == 'household' || $root.instanceName == 'event' || $root.instanceName == 'littleemitter'\"><div class=\"sites-panel-title\"><span ng-bind-html=\"'SURVEY_INTERFACE_VERIFICATION' | translate\"></span></div><div class=\"sites-panel-item\" ng-show=\"verificationRequests.length&gt; 0 \"><table><tr ng-repeat=\"verificationRequest in verificationRequests\"><td>{{ verificationRequest.scope.name }}</td><td><button class=\"button\" ng-show=\"getVerificationRequestStatus() === 'VERIFICATION_STATUS_VERIFIED'\" ng-click=\"downloadVerificationReport(verificationRequest)\" type=\"button\"><span ng-bind-html=\"'DOWNLOAD_VERIFICATION_REPORT' | translate\"></span></button></td></tr></table></div></div></td><td class=\"top-aligned horizontally-padded wide\"><div ng-show=\"o != null &amp;&amp; o != undefined\"><div ng-show=\"o.reportDTOs.R_1.rightPeriod == null\"><span ng-show=\"$root.instanceName == 'enterprise'\" ng-bind-html=\"'ACCOMPANIMENT_WORD_ENTERPRISE' | translateWithVars:[(leftTotalEmissions | numberToI18N)]\"></span><span ng-show=\"$root.instanceName == 'municipality'\" ng-bind-html=\"'ACCOMPANIMENT_WORD_MUNICIPALITY' | translateWithVars:[(leftTotalEmissions | numberToI18N)]\"></span></div><div ng-hide=\"o.reportDTOs.R_1.rightPeriod == null\"><span ng-show=\"$root.instanceName == 'enterprise'\" ng-bind-html=\"'ACCOMPANIMENT_COMPARISION_WORD_ENTERPRISE' | translateWithVars:[(leftTotalEmissions | numberToI18N),o.reportDTOs.R_1.leftPeriod,(rightTotalEmissions | numberToI18N),o.reportDTOs.R_1.rightPeriod]\"></span><span ng-show=\"$root.instanceName == 'municipality'\" ng-bind-html=\"'ACCOMPANIMENT_COMPARISION_WORD_MUNICIPALITY' | translateWithVars:[(leftTotalEmissions | numberToI18N),o.reportDTOs.R_1.leftPeriod,(rightTotalEmissions | numberToI18N),o.reportDTOs.R_1.rightPeriod]\"></span></div><br><br><div ng-show=\"siteSelectionIsEmpty()\"><div ng-bind-html=\"'SELECT_AT_LEAST_ONE_SITE' | translate\"></div></div><div ng-hide=\"siteSelectionIsEmpty()\"><div ng-show=\"current_tab == 1\"><h2><span ng-bind-html=\"'VALUES_BY_CATEGORY' | translate\"></span></h2><br><center><div class=\"diagram\" style=\"height: 10cm\" ng-bind-html=\"o.svgHistograms.R_1 | trustAsHtml\"></div></center><br><center><div><mm-awac-result-legend mode=\"numbers\" ng-model=\"o.reportDTOs.R_1\"></mm-awac-result-legend></div></center></div><div ng-show=\"current_tab == 2\"><h2><span ng-bind-html=\"'IMPACTS_PARTITION' | translate\"></span></h2><br><center><table ng-show=\"$root.instanceName == 'household' || $root.instanceName == 'event' || $root.instanceName == 'littleemitter' \"><tr><td><div class=\"diagram\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.leftSvgDonuts.R_1 | trustAsHtml\"></div><span>&nbsp;</span><div class=\"diagram\" ng-show=\"rightTotalEmissions&gt;0\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.rightSvgDonuts.R_1 | trustAsHtml\"></div></td><td style=\"width: 2em\"></td><td><mm-awac-result-legend ng-model=\"o.reportDTOs.R_1\"></mm-awac-result-legend></td></tr></table></center><table ng-hide=\"$root.instanceName == 'household' || $root.instanceName == 'event' || $root.instanceName == 'littleemitter'\"><tr><td colspan=\"3\"><h3><span ng-bind-html=\"'SCOPE_1' | translate\"></span><span> : {{ leftTotalScope1 | numberToI18N }} tCO2e</span><span>&nbsp;/&nbsp;</span><span> {{ rightTotalScope1 | numberToI18N }} tCO2e</span></h3></td></tr><tr><td><div class=\"diagram\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.leftSvgDonuts.R_2 | trustAsHtml\"></div><span>&nbsp;</span><div class=\"diagram\" ng-show=\"rightTotalEmissions&gt;0\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.rightSvgDonuts.R_2 | trustAsHtml\"></div></td><td style=\"width: 2em\"></td><td><mm-awac-result-legend ng-model=\"o.reportDTOs.R_2\"></mm-awac-result-legend></td></tr><tr><td colspan=\"3\"><h3><span ng-bind-html=\"'SCOPE_2' | translate\"></span><span> : {{ leftTotalScope2 | numberToI18N }} tCO2e</span><span>&nbsp;/&nbsp;</span><span> {{ rightTotalScope2 | numberToI18N }} tCO2e</span></h3></td></tr><tr><td><div class=\"diagram\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.leftSvgDonuts.R_3 | trustAsHtml\"></div><span>&nbsp;</span><div class=\"diagram\" ng-show=\"rightTotalEmissions&gt;0\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.rightSvgDonuts.R_3 | trustAsHtml\"></div></td><td style=\"width: 2em\"></td><td><mm-awac-result-legend ng-model=\"o.reportDTOs.R_3\"></mm-awac-result-legend></td></tr><tr><td colspan=\"3\"><h3><span ng-bind-html=\"'SCOPE_3' | translate\"></span><span> : {{ leftTotalScope3 | numberToI18N }} tCO2e</span><span>&nbsp;/&nbsp;</span><span> {{ rightTotalScope3 | numberToI18N }} tCO2e</span></h3></td></tr><tr><td><div class=\"diagram\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.leftSvgDonuts.R_4 | trustAsHtml\"></div><span>&nbsp;</span><div class=\"diagram\" ng-show=\"rightTotalEmissions&gt;0\" style=\"display:inline-block; max-width: 5cm; max-height: 5cm;\" ng-bind-html=\"o.rightSvgDonuts.R_4 | trustAsHtml\"></div></td><td style=\"width: 2em\"></td><td><mm-awac-result-legend ng-model=\"o.reportDTOs.R_4\"></mm-awac-result-legend></td></tr></table><br><br></div><div ng-show=\"current_tab == 3\"><h2><span ng-bind-html=\"'KIVIAT_DIAGRAM' | translate\"></span></h2><br><center><div class=\"diagram\" style=\"display:inline-block; max-width: 15cm; max-height: 15cm;\" ng-bind-html=\"o.svgWebs.R_1 | trustAsHtml\"></div></center><br><center><mm-awac-result-legend mode=\"numbers\" ng-model=\"o.reportDTOs.R_1\" ng-hide=\"$root.instanceName == 'household' || $root.instanceName == 'event' || $root.instanceName == 'littleemitter'\"></mm-awac-result-legend><mm-awac-result-legend-simple ng-show=\"$root.instanceName == 'household' || $root.instanceName == 'event' || $root.instanceName == 'littleemitter'\" ng-ideal=\"o.idealMap\" ng-model=\"o.reportDTOs.R_1\" ng-type-color=\"o.typeColor\" ng-ideal-color=\"o.idealColor\" ng-type=\"o.typeMap\"></mm-awac-result-legend-simple></center></div><div ng-show=\"(current_tab == 4) &amp;&amp; ($root.instanceName == 'enterprise' || $root.instanceName == 'municipality')\"><h2><span ng-bind-html=\"'NUMBERS' | translate\"></span></h2><mm-awac-result-table ng-model=\"o.reportDTOs.R_1\"></mm-awac-result-table></div><div ng-show=\"(current_tab == 4) &amp;&amp; ($root.instanceName == 'household' || $root.instanceName == 'event' || $root.instanceName == 'littleemitter')\"><h2><span ng-bind-html=\"'NUMBERS' | translate\"></span></h2><mm-awac-result-table-simple ng-model=\"o.reportDTOs.R_1\" ng-type-color=\"o.typeColor\" ng-ideal-color=\"o.idealColor\" ng-type-map=\"o.typeMap\" ng-ideal-map=\"o.idealMap\"></mm-awac-result-table-simple></div><div ng-show=\"current_tab == 5\"><h2><span ng-bind-html=\"'COMPARISION_WITH_CONSTANT_EMISSION_FACTORS' | translate\"></span><span>&nbsp;(</span><span ng-bind-html=\"'USED_EMISSION_FACTORS' | translate\"></span><span>:&nbsp;</span><span>{{ o.reportDTOs.R_1.rightPeriod }}</span><span>)</span></h2><br><center><div class=\"diagram\" style=\"height: 10cm\" ng-bind-html=\"o.svgHistogramsCEF.R_1 | trustAsHtml\"></div></center><br><center><div><mm-awac-result-legend mode=\"numbers\" ng-model=\"o.reportCEFDTOs.R_1\"></mm-awac-result-legend></div></center></div><div ng-show=\"current_tab == 6\"><h2><span ng-bind-html=\"'CALCULUS_EXPLANATION' | translate\"></span></h2><br><p ng-show=\"o.reportDTOs.R_1.rightPeriod != null\"><span ng-bind-html=\"'RESULTS_EXPLANATION_ONLY_AVAILABLE_FOR_SINGLE_PERIOD' | translate\"></span></p><p ng-repeat=\"e in o.logEntries\" ng-hide=\"o.reportDTOs.R_1.rightPeriod != null\"><span ng-show=\"e.__type == 'eu.factorx.awac.dto.awac.get.ReportLogContributionEntryDTO'\"><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART1' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.biActivityCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.biActivitySubCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART2' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.adValue | numberToI18NRoundedOrFullIfLessThanOne\"></span><span>&#32;</span><span ng-bind-html=\"e.adUnit\"></span><span>&#32;</span><br><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART3' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.biIndicatorCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART4' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.fValue | numberToI18NRoundedOrFullIfLessThanOne\"></span><span>&#32;</span><span ng-bind-html=\"e.fUnitOut\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART5' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.fUnitIn\"></span><br><span>&nbsp;&nbsp;&nbsp;&nbsp;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_CONTRIB_PART6' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.value | numberToI18N\"></span><span>&#32;</span><span ng-bind-html=\"e.biUnit\"></span></span><span ng-show=\"e.__type == 'eu.factorx.awac.dto.awac.get.ReportLogNoSuitableFactorEntryDTO'\" style=\"color: #a33\"><span ng-bind-html=\"'RESULTS_EXPLANATION_NOFACTOR_PART1' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.biActivityCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.biActivitySubCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_NOFACTOR_PART2' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.adValue | numberToI18NRoundedOrFullIfLessThanOne\"></span><span>&#32;</span><span ng-bind-html=\"e.adUnit\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_NOFACTOR_PART3' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.biIndicatorCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.adActivitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_NOFACTOR_PART4' | translate\"></span></span><span ng-show=\"e.__type == 'eu.factorx.awac.dto.awac.get.LowerRankInGroupDTO'\" style=\"color: #33a\"><span ng-bind-html=\"'RESULTS_EXPLANATION_LOWER_RANK_PART1' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.activityCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activitySubCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_LOWER_RANK_PART2' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.value | numberToI18NRoundedOrFullIfLessThanOne\"></span><span>&#32;</span><span ng-bind-html=\"e.unit\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_LOWER_RANK_PART3' | translate\"></span></span><span ng-show=\"e.__type == 'eu.factorx.awac.dto.awac.get.NoSuitableIndicatorDTO'\" style=\"color: #a33\"><span ng-bind-html=\"'RESULTS_EXPLANATION_NOINDICATOR_PART1' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.activityCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activitySubCategory | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activityType | translate\"></span><span>&#32;/&#32;</span><span ng-bind-html=\"e.activitySource | translate\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_NOINDICATOR_PART2' | translate\"></span><span>&#32;</span><span ng-bind-html=\"e.value | numberToI18NRoundedOrFullIfLessThanOne\"></span><span>&#32;</span><span ng-bind-html=\"e.unit\"></span><span>&#32;</span><span ng-bind-html=\"'RESULTS_EXPLANATION_NOINDICATOR_PART3' | translate\"></span></span></p></div><div ng-show=\"current_tab == 7\"><h2><span ng-bind-html=\"'USED_EMISSION_FACTORS' | translate\"></span></h2></div><div ng-show=\"current_tab == 8\"><h2><span ng-bind-html=\"'CONVENTION_OF_MAYORS' | translate\"></span></h2><br><center><div class=\"diagram\" style=\"height: 10cm\" ng-bind-html=\"o.svgHistograms.R_5 | trustAsHtml\"></div></center><br><center><div><mm-awac-result-legend mode=\"numbers\" ng-model=\"o.reportDTOs.R_5\"></mm-awac-result-legend></div></center></div><br><div class=\"results_disclaimer\"><span class=\"results_disclaimer_text\" ng-bind-html=\"'RESULTS_DISCLAIMER' | translate\" ng-hide=\"$root.instanceName == 'household' || $root.instanceName == 'event' || $root.instanceName == 'littleemitter'\"></span><span class=\"results_disclaimer_text\" ng-show=\"$root.instanceName == 'household' || $root.instanceName == 'event' || $root.instanceName == 'littleemitter'\" ng-bind-html=\"'RESULTS_DISCLAIMER_SIMPLE' | translate\"></span></div><br><br><br></div></div></td><td class=\"top-aligned\"><div class=\"align-right\"><button class=\"button\" ng-click=\"exportXls()\" type=\"button\" ng-disabled=\"xlsLoading\"><img ng-show=\"xlsLoading\" src=\"/assets/images/loading_small.gif\"><span ng-bind-html=\"'XLS_EXPORT' | translate\" ng-hide=\"xlsLoading\"></span></button><button class=\"button\" ng-click=\"exportPdf()\" type=\"button\" ng-disabled=\"pdfLoading\"><img ng-show=\"pdfLoading\" src=\"/assets/images/loading_small.gif\"><span ng-bind-html=\"'PDF_EXPORT' | translate\" ng-hide=\"pdfLoading\"></span></button></div><br><div class=\"charts-panel-tabset\"><div class=\"charts-panel-tab\" ng-click=\"current_tab = 1\" ng-class=\"{ active: current_tab == 1 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_bars\" ng-bind-html=\"'VALUES_BY_CATEGORY' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 2\" ng-class=\"{ active: current_tab == 2 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_donut\" ng-bind-html=\"'IMPACTS_PARTITION' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 3\" ng-class=\"{ active: current_tab == 3 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_web\" ng-bind-html=\"'KIVIAT_DIAGRAM' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 4\" ng-class=\"{ active: current_tab == 4 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_numbers\" ng-bind-html=\"'NUMBERS' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 5\" ng-class=\"{ active: current_tab == 5 }\" ng-hide=\"$root.instanceName == 'household' || $root.instanceName == 'event' || $root.instanceName == 'littleemitter'\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_constant_factors\" ng-bind-html=\"'COMPARISION_WITH_CONSTANT_EMISSION_FACTORS' | translate\"></div></div><div class=\"charts-panel-tab\" ng-click=\"current_tab = 6\" ng-class=\"{ active: current_tab == 6 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_calculus\" ng-bind-html=\"'CALCULUS_EXPLANATION' | translate\"></div></div><div class=\"charts-panel-tab\" ng-show=\"$root.instanceName == 'municipality'\" ng-click=\"current_tab = 8\" ng-class=\"{ active: current_tab == 8 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_convention\" ng-bind-html=\"'CONVENTION_OF_MAYORS' | translate\"></div></div><!--<div class=\"charts-panel-tab\" ng-click=\"current_tab = 7\" ng-class=\"{ active: current_tab == 7 }\"><div class=\"charts-panel-tab-arrow\"></div><div class=\"charts-panel-tab-title tab_fe\" ng-bind-html=\"'USED_EMISSION_FACTORS' | translate\"></div></div>--></div></td></tr></table></div>");$templateCache.put('$/angular/views/enterprise/TAB7.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"A243\"><mm-awac-repetition-name question-code=\"A244\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A244')\" ng-question-set-code=\"'A244'\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-map-index=\"{'A244':$index}\" ng-question-code=\"'A245'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-map-index=\"{'A244':$index}\" ng-question-code=\"'A246'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-map-index=\"{'A244':$index}\" ng-question-code=\"'A247'\"></mm-awac-question><mm-awac-block><!--Transport--><mm-awac-sub-title question-code=\"A250\"></mm-awac-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"A251\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A252\"></mm-awac-sub-sub-title><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1,itLevel1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1,itLevel1)\"></i><div class=\"status {{getStatusClassForTab(1,1,itLevel1)}}\"></div><span ng-bind-html=\"'A253' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A254\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A255\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A256\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A257\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A258\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A259\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A260\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A261\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A262\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A263\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A264\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" ng-aggregation=\"(getAnswer('A256',itLevel1).value | nullToZero)+( getAnswer('A257',itLevel1).value | nullToZero)+(getAnswer('A258',itLevel1).value | nullToZero)+(getAnswer('A259',itLevel1).value| nullToZero)+(getAnswer('A260',itLevel1).value| nullToZero)+(getAnswer('A261',itLevel1).value| nullToZero)+(getAnswer('A262',itLevel1).value| nullToZero)+(getAnswer('A263',itLevel1).value| nullToZero)+(getAnswer('A264',itLevel1).value| nullToZero)\" question-code=\"A265\" ng-map-index=\"{'A244':$index}\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2,itLevel1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2,itLevel1)\"></i><div class=\"status {{getStatusClassForTab(1,2,itLevel1)}}\"></div><span ng-bind-html=\"'A266' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A267\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A268\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" ng-aggregation=\"5000\" question-code=\"A269\" ng-map-index=\"{'A244':$index}\" ng-condition=\"getAnswer('A268',itLevel1).value == '3'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" ng-aggregation=\"2500\" question-code=\"A270\" ng-map-index=\"{'A244':$index}\" ng-condition=\"getAnswer('A268',itLevel1).value == '2'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" ng-aggregation=\"200\" question-code=\"A271\" ng-map-index=\"{'A244':$index}\" ng-condition=\"getAnswer('A268',itLevel1).value == '1'\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-block><mm-awac-block><!--Distribution--><mm-awac-sub-sub-title question-code=\"A272\"></mm-awac-sub-sub-title><mm-awac-repetition-name question-code=\"A273\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A273',itLevel1)\" ng-question-set-code=\"'A273'\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A274\" ng-map-index=\"{'A244':$parent.$parent.$index,'A273':$index}\"></mm-awac-question><mm-awac-repetition-name question-code=\"A275\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel2\" question-set-code=\"A275\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A275',itLevel2)\" ng-iteration=\"itLevel3\"><mm-awac-question ng-repetition-map=\"itLevel3\" question-code=\"A276\" ng-map-index=\"{'A244':$parent.$parent.$parent.$parent.$index,'A273':$parent.$parent.$index,'A275':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel3\" question-code=\"A277\" ng-map-index=\"{'A244':$parent.$parent.$parent.$parent.$index,'A273':$parent.$parent.$index,'A275':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A275\" ng-iteration=\"itLevel2\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1024\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel2\" question-set-code=\"A1024\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A1024',itLevel2)\" ng-iteration=\"itLevel3\"><mm-awac-question ng-repetition-map=\"itLevel3\" question-code=\"A1025\" ng-map-index=\"{'A244':$parent.$parent.$parent.$parent.$index,'A273':$parent.$parent.$index,'A1024':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel3\" question-code=\"A1026\" ng-map-index=\"{'A244':$parent.$parent.$parent.$parent.$index,'A273':$parent.$parent.$index,'A1024':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1024\" ng-iteration=\"itLevel2\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1027\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel2\" question-set-code=\"A1027\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A1027',itLevel2)\" ng-iteration=\"itLevel3\"><mm-awac-question ng-repetition-map=\"itLevel3\" question-code=\"A1028\" ng-map-index=\"{'A244':$parent.$parent.$parent.$parent.$index,'A273':$parent.$parent.$index,'A1027':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel3\" question-code=\"A1029\" ng-map-index=\"{'A244':$parent.$parent.$parent.$parent.$index,'A273':$parent.$parent.$index,'A1027':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1027\" ng-iteration=\"itLevel2\"></mm-awac-repetition-add-button><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A278\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A273':$index}\"></mm-awac-question><mm-awac-repetition-name question-code=\"A279\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel2\" question-set-code=\"A279\" ng-repeat=\"itLevel3 in getRepetitionMapByQuestionSet('A279',itLevel2)\" ng-iteration=\"itLevel3\"><mm-awac-question ng-repetition-map=\"itLevel3\" question-code=\"A280\" ng-map-index=\"{'A244':$parent.$parent.$parent.$parent.$index,'A273':$parent.$parent.$index,'A279':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel3\" question-code=\"A281\" ng-map-index=\"{'A244':$parent.$parent.$parent.$parent.$index,'A273':$parent.$parent.$index,'A279':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A279\" ng-iteration=\"itLevel2\"></mm-awac-repetition-add-button></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A273\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button></mm-awac-block><mm-awac-block><!--sous-question final ou intermediaire--><mm-awac-sub-title question-code=\"A8000\"></mm-awac-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" ng-question-code=\"'A248'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-condition=\"getAnswer('A248',itLevel1).value == '2'\" ng-question-code=\"'A249'\"></mm-awac-question></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A249',itLevel1).value == '1'\"><!--Traitement--><mm-awac-sub-sub-title question-code=\"A282\"></mm-awac-sub-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"A283\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-repetition-name question-code=\"A284\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A284\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A284',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A285\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A284':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A286\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A284':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A284\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1030\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A1030\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1030',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1031\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A1030':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1032\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A1030':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1030\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1033\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A1033\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1033',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1034\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A1033':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1035\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A1033':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1033\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A287\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-repetition-name question-code=\"A288\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A288\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A288',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A289\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A288':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A290\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A288':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A288\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Utilisation--><mm-awac-sub-sub-title question-code=\"A291\"></mm-awac-sub-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"A292\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A293\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A294\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A295\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A296\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-repetition-name question-code=\"A297\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A297\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A297',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A298\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A297':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A299\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A297':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A297\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button></mm-awac-block><mm-awac-block ng-condition=\"getAnswer('A248',itLevel1).value == '1' || getAnswer('A249',itLevel1).value == '1'\"><!--Fin de vie--><mm-awac-sub-sub-title question-code=\"A300\"></mm-awac-sub-sub-title><mm-awac-question ng-repetition-map=\"itLevel1\" ng-optional=\"true\" question-code=\"A301\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A302\" ng-map-index=\"{'A244':$index}\"></mm-awac-question><mm-awac-repetition-name question-code=\"A5010\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A5010\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A5010',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A5011\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A5010':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A5012\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A5010':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A5013\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A5010':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A5014\" ng-map-index=\"{'A244':$parent.$parent.$index, 'A5010':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A5010\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button></mm-awac-block></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A244\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/enterprise/help_user_manager_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur la gestion des utilisateurs</h1><h2>Introduction</h2><p>Accessible aux seuls utilisateurs avec un statut <b>administrateur</b>, cette interface permet de gérer l'ensemble des utilisateurs de l'organisation, en déterminant qui est administrateur et en invitant de nouveaux utilisateurs ou en en désactivant certains.</p><h2>Octroyer le statut administrateur</h2><p>Pour octroyer le statut d'administrateur, il suffit de cocher la case à cet effet à côté de son nom.</p><p><em>Note</em>: un administrateur ne peut jamais se défaire lui-même de son statut: un autre administrateur doit le faire pour lui, ce qui permet de s'assurer qu'il reste toujours un administrateur associé à l'organisation.</p><h2>Inviter un nouvel utilisateur</h2><img src=\"/assets/images/help/inviter_utilisateur.png\"><p>Un clic sur ce bouton ouvre une fenêtre dans laquelle il suffit de mentionner l'adresse courriel de l'utilisateur à inviter: il reçoit alors un courriel avec un lien pour s'enregistrer.</p><p><em>Note</em>: dès que l'utilisateur se sera enregistré, il ne faudra pas oublier de l'associer à des sites dans l'interface de gestion des sites.</p><h2>Désactiver un utilisateur</h2><p>Pour désactiver un utilisateur, il suffit de décocher la case \"Actif\" à côté de son nom. Dès ce moment, l'utilisateur ne saura plus se connecter à l'application avec son identifiant. Aucune des données fournies par cet utilisateur n'est perdue. Elles restent accessibles aux autres utilisateurs, avec mention claire de ce qu'elles ont été fournies par leur ancien collègue.</p><img src=\"/assets/images/help/utilisateurs_actif.png\"><p><em>Note</em>: un administrateur ne peut jamais se désactiver lui-même: un autre administrateur doit le faire pour lui, ce qui permet de s'assurer qu'il reste toujours un administrateur associé à l'organisation.</p><p><em>Note 2</em>: Un utilisateur inactif peut être re-rendu actif à tout moment grâce au bouton vert qui est apparu à côté de son nom.</p>");$templateCache.put('$/angular/views/enterprise/confidentiality_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: gestion de la confidentialité de vos données</h1><h2>Introduction</h2><p>L'AWAC ne se considère a aucun moment être le propriétaire des données fournies via l'outil. Tant vous que les autres utilisateurs restez chaque fois les propriétaires des données que vous fournissez. L'AWAC en le gardien, et n'en fera aucun usage hormis ceux décrits ci-dessous.</p><h2>Protection de la vie privée</h2><p>Si vous nous fournissez des informations personnelles, elles seront traitées conformément aux dispositions de la loi du 8 décembre 1992. Les autres données seront traitées, moyennant votre accord (voir plus bas),  anonymement à des fins statistiques et pour vous adresser des messages électroniques généraux; elles ne seront pas communiquées à des tiers, à l’exception du sous-traitant avec lequel l’AWAC a conclu une convention relative à la confidentialité des données, ni utilisées à des fins commerciales. L’AWAC s'engage à prendre les meilleures mesures de sécurité afin d'éviter que des tiers n'abusent des données à caractère personnel qui lui ont été communiquées.</p><h2>Confidentialité de vos données</h2><p>Afin de garantir cette confidentialité, divers mécanismes techniques sont utilisés: la connexion au service s’établit par protocole https, avec certificat TSL/SSL1, à l’instar de ce qui se fait pour les transactions financières électroniques, et non pas simple protocole http. De plus, aucun mot de passe utilisateur ne circule en clair: ils sont systématiquement tous encryptés et seules les versions cryptées sont mémorisées.</p><h2>Usage statistique de vos données</h2><p>Un tel usage prend place uniquement si vous avez explicitement marqué votre accord, soit lors de la création de votre compte, soit à tout moment via l'interface de \"gérer l'organisation\" en activant le choix \"Permettre à l’AWAC d’inclure mes données dans son analyse statistique\".</p><p>Les données saisies en ligne et les résultats associés sont traités collectivement et anonymement. Ces données et résultats pourront alors être exploités, en totalité ou en partie, en vue de réalisations statistiques, de réalisations de rapports ou d’études relatives à la consommation d’énergie et d’émission de gaz à effet de serre.</p>");$templateCache.put('$/angular/views/enterprise/help_results_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur la présentation des résultats</h1><h2>Introduction</h2><p>La partie Résultats vous présente le bilan GES de votre organisation, selon vos critères et présenté de différentes manières. Petit tour d'horizon des options qui s'ouvrent à vous.</p><h2>Choisir les sites</h2><p>Une première option est que vous pouvez choisir l'ensemble des sites dont les données seront consolidées ensemble (au prorata des poucentages à prendre en compte définis dans la partie gestion des sites) et sommées pour réaliser le résultat final.</p><img src=\"/assets/images/help/resultats_entreprise_main.png\"><h2>Résultat annuel ou comparaison</h2><p>Une seconde option vous permet de choisir si souhaitez voir le résultat pour une année seulement, ou au contraire comparer le résultat de deux années différentes, comme illustré ci-dessous.</p><img src=\"/assets/images/help/resultats_entreprise_comparaison.png\"><h2>Export du résultat</h2><p>Deux boutons, disponibles juste au-dessus des différentes manières de visualiser le résultat, vous permettent de télécharger celui-ci au format Excel ou PDF sur votre ordinateur. Dans chaque cas, après avoir cliqué sur le bouton, vous devrez patienter une bonne dizaine de secondes, le temps que le serveur assemble le document pour vous. Ensuite, votre navigateur sauvegardera le ficher demandé selon son mécanisme habituel (endroit habituel, avec ou sans pop-up, selon la configuration de votre navigateur).</p><p>Le ficher Excel contient juste le résultat brut et les données saisies, triées dans des cases différentes pour vous permettre de les re-travailler à votre guise et/ou de les mettre en page différement.</p><p>Le ficher PDF contient les données saisies, mais également tous les graphiques de présentation du résultat.</p><h2>Différentes vues</h2><p>Le résultat est présenté de différentes manières, chacune accesssible via les onglets sur le bord droit du navigateur. Voici les explications de l'objectif de chaque mode de présentation.</p><h3>Valeurs par rubrique</h3><p>Cette vue présente l'ensemble des rubriques du résultat sous forme de \"bar-chart\" (diagramme par bâtons), afin de mettre en évidence les impacts dominants de votre bilan GES. En mode de comparaison de deux années, les rubriques de chacune des deux années sont présentées côte à côte dans deux couleurs différentes.</p><h3>Répartition des impacts</h3><p>Cette vue présente les résultats scindés selon les 3 périmètres (scopes) de l'ISO 14064, dans des \"pie-charts\" (présentation en fromage). Cela permet de mettre en évidence les rubriques principales qui contribuent à chacun de ces périmètres.</p><h3>Diagramme araignée</h3><p>Cette vue est la même que celle des \"valeurs par rubrique\", mais sous forme de diagramme araignée, où chaque rubrique est représentée sur un axe différent. En cas de comparaison de deux années, cela permet de mieux visualiser les divergences entre les années.</p><h3>Chiffres</h3><p>Cette vue présente les résultats sous forme d'un tableau de chiffres.</p><h3>Comparaison à facteurs constants</h3><p>Cette vue présente à nouveau un \"bar-chart\" (diagramme par bâtons) comme les \"valeurs par rubrique\". Actif seulement en cas de comparaison de deux années, les données de l'année de comparaison ont cette fois étés multipliées par les facteurs d'émission de l'année de référence (et non pas par ceux de leur année de données, comme dans les autres vues). Cela permet de visualiser le changement dû seulement aux consommations engendrées par les données, indépendamment de tout changement de facteur d'émission.</p><h3>Explication du calcul</h3><p>Cette dernière rubrique, qui est également exportée tant en Excel qu'en PDF, vous permet de comprendre le raisonnement qui a mené au calcul du résultat qui vous est présenté. Elle vous présente chaque fois la valeur résultant du jeu de données fournies avec le facteur d'émission qui y a été appliqué, et la contribution au résultat qui en découle. Si certaines phrases apparaissent en rouge, cela signifie qu'un faceteur d'émission n'a pas été trouvé, et nous vous invitons alors à le signaler à l'AWAC.</p><h2>Résultats vérifiés de manière externe</h2><p>Si une vérification a été effectuée (cf. pages principales), le rapport de vérification, pour chacun des sites pour lequel un tel rapport existe pour l'année sélectionnée, devient disponible en dessous de la sélection des sites.</p>");$templateCache.put('$/angular/views/enterprise/help_user_data_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur le profil individuel</h1><p>Cette interface de gestion permet à chaque utilisateur, individuellement, de gérer son profil.</p><p>Il peut ainsi y modifier ses données personnelles (prénom, nonm et adresse courriel) ainsi que le mot de passe utilisés pour se connecter à l'application. L'identifiant n'est pas modifiable.</p>");$templateCache.put('$/angular/views/enterprise/TAB3.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"A50\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A50' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"A51\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A52\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A52' | translate\"></div></div><mm-awac-sub-title question-code=\"A400\"></mm-awac-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><div class=\"status {{getStatusClassForTab(1,1)}}\"></div><span ng-bind-html=\"'A402' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A403\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A404\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A405\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><div class=\"status {{getStatusClassForTab(1,2)}}\"></div><span ng-bind-html=\"'A406' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A407\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A407\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A407')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A408\" ng-map-index=\"{'A407':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A409\" ng-map-index=\"{'A407':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A410\" ng-map-index=\"{'A407':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A411\" ng-map-index=\"{'A407':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A407\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(1,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,3)\"></i><div class=\"status {{getStatusClassForTab(1,3)}}\"></div><span ng-bind-html=\"'A412' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A413\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A413\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A413')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"3\" question-code=\"A414\" ng-map-index=\"{'A413':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"3\" question-code=\"A415\" ng-map-index=\"{'A413':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"3\" question-code=\"A416\" ng-map-index=\"{'A413':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"3\" question-code=\"A417\" ng-map-index=\"{'A413':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A413\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div><mm-awac-sub-title question-code=\"A518\"></mm-awac-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(2,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,1)\"></i><div class=\"status {{getStatusClassForTab(2,1)}}\"></div><span ng-bind-html=\"'A502' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"2\" ng-tab=\"1\" question-code=\"A503\"></mm-awac-question><mm-awac-question ng-tab-set=\"2\" ng-tab=\"1\" question-code=\"A504\"></mm-awac-question><mm-awac-question ng-tab-set=\"2\" ng-tab=\"1\" question-code=\"A505\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(2,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,2)\"></i><div class=\"status {{getStatusClassForTab(2,2)}}\"></div><span ng-bind-html=\"'A506' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A507\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A507\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A507')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"2\" question-code=\"A508\" ng-map-index=\"{'A507':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"2\" question-code=\"A509\" ng-map-index=\"{'A507':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"2\" question-code=\"A510\" ng-map-index=\"{'A507':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"2\" question-code=\"A511\" ng-map-index=\"{'A507':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A507\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(2,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,3)\"></i><div class=\"status {{getStatusClassForTab(2,3)}}\"></div><span ng-bind-html=\"'A512' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A513\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A513\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A513')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"3\" question-code=\"A514\" ng-map-index=\"{'A513':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"3\" question-code=\"A515\" ng-map-index=\"{'A513':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"3\" question-code=\"A516\" ng-map-index=\"{'A513':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"2\" ng-tab=\"3\" question-code=\"A517\" ng-map-index=\"{'A513':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A513\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div><mm-awac-sub-title question-code=\"A600\"></mm-awac-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(3,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,1)\"></i><div class=\"status {{getStatusClassForTab(3,1)}}\"></div><span ng-bind-html=\"'A602' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"3\" ng-tab=\"1\" question-code=\"A603\"></mm-awac-question><mm-awac-question ng-tab-set=\"3\" ng-tab=\"1\" question-code=\"A604\"></mm-awac-question><mm-awac-question ng-tab-set=\"3\" ng-tab=\"1\" question-code=\"A605\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(3,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,2)\"></i><div class=\"status {{getStatusClassForTab(3,2)}}\"></div><span ng-bind-html=\"'A606' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A607\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A607\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A607')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"2\" question-code=\"A608\" ng-map-index=\"{'A607':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"2\" question-code=\"A609\" ng-map-index=\"{'A607':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"2\" question-code=\"A610\" ng-map-index=\"{'A607':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"2\" question-code=\"A611\" ng-map-index=\"{'A607':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A607\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-yellow\" active=\"getTab(3,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(3,3)\"></i><div class=\"status {{getStatusClassForTab(3,3)}}\"></div><span ng-bind-html=\"'A612' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A613\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A613\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A613')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"3\" question-code=\"A614\" ng-map-index=\"{'A613':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"3\" question-code=\"A615\" ng-map-index=\"{'A613':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"3\" question-code=\"A616\" ng-map-index=\"{'A613':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"3\" ng-tab=\"3\" question-code=\"A617\" ng-map-index=\"{'A613':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A613\"></mm-awac-repetition-add-button></div></div></tab></tabset></div></div></div></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A93\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A93' | translate\"></div></div><mm-awac-sub-title question-code=\"A93\"></mm-awac-sub-title><mm-awac-question question-code=\"A95\"></mm-awac-question><mm-awac-question question-code=\"A96\"></mm-awac-question><mm-awac-question question-code=\"A97\"></mm-awac-question><mm-awac-question question-code=\"A98\"></mm-awac-question><mm-awac-question question-code=\"A99\"></mm-awac-question><mm-awac-question question-code=\"A100\"></mm-awac-question><mm-awac-question question-code=\"A101\"></mm-awac-question><mm-awac-question question-code=\"A102\"></mm-awac-question><mm-awac-question question-code=\"A103\"></mm-awac-question><mm-awac-question question-code=\"A104\"></mm-awac-question><mm-awac-question question-code=\"A105\"></mm-awac-question><mm-awac-question question-code=\"A106\"></mm-awac-question><mm-awac-question question-code=\"A107\"></mm-awac-question><mm-awac-question question-code=\"A108\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A113\"><mm-awac-sub-title question-code=\"A113\"></mm-awac-sub-title><div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(5,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(5,1)\"></i><div class=\"status {{getStatusClassForTab(5,1)}}\"></div><span ng-bind-html=\"'A114' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A115\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A115\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A115')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"5\" ng-tab=\"1\" question-code=\"A116\" ng-map-index=\"{'A115':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"5\" ng-tab=\"1\" question-code=\"A117\" ng-map-index=\"{'A115':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"5\" ng-tab=\"1\" question-code=\"A118\" ng-map-index=\"{'A115':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"5\" ng-tab=\"1\" question-code=\"A119\" ng-map-index=\"{'A115':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"5\" ng-tab=\"1\" question-code=\"A120\" ng-map-index=\"{'A115':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A115\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(5,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(5,2)\"></i><div class=\"status {{getStatusClassForTab(5,2)}}\"></div><span ng-bind-html=\"'A121' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"5\" ng-tab=\"2\" question-code=\"A122\"></mm-awac-question><mm-awac-question ng-tab-set=\"5\" ng-tab=\"2\" question-code=\"A123\"></mm-awac-question><mm-awac-question ng-tab-set=\"5\" ng-tab=\"2\" question-code=\"A124\" ng-condition=\"getAnswer('A123',itLevel1).value == '0'\"></mm-awac-question><mm-awac-question ng-tab-set=\"5\" ng-tab=\"2\" ng-aggregation=\"2500\" question-code=\"A125\" ng-condition=\"getAnswer('A124',itLevel1).value == '1'\"></mm-awac-question><mm-awac-question ng-tab-set=\"5\" ng-tab=\"2\" ng-aggregation=\"5000\" question-code=\"A126\" ng-condition=\"getAnswer('A124',itLevel1).value == '0'\"></mm-awac-question><mm-awac-question ng-tab-set=\"5\" ng-tab=\"2\" question-code=\"A127\" ng-condition=\"getAnswer('A123',itLevel1).value == '1'\"></mm-awac-question></div></div></tab></tabset></div></div></div></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/enterprise/help_actions_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur les actions de réduction</h1><h2>Introduction</h2><p>En vous aidant à établir votre bilan de gaz à effet de serre, l'objectif du calculateur proposé par l'AWAC est de vous aider à définir des actions visant à réduire votre impact climatique. Comment?</p><p>Sur base du bilan établi, vous pouvez déterminer les parties les plus impactantes de vos activités, et ainsi choisir de vous y prendre autrement afin de réduire votre impact. C'est ce que l'on appelle une \"action de réduction\": un objectif qui vous est propre et que vous vous fixez.</p><p>Parfois, avant que de déterminer une action de réduction, il y a lieu de bien s'assurer de son impact, et pour ce faire d'affiner les données utilisées dans le bilan. Cela s'appelle une action de \"meilleure mesure\" des données.</p><p>Afin de vous aider dans ce travail, l'AWAC a également établi certains conseils, qui sont des actions relativement génériques et partiellement pré-remplies, que vous pouvez utiliser comme base pour vous définir une action. Lorsque c'est possible, le potentiel de gain CO2 associé au conseil a été calculé automatiquement par le système sur base de vos données.</p><h2>Créer une action</h2><img src=\"/assets/images/help/bouton_ajouter_action.png\"><p>Cliquez sur ce bouton pour créer une action que vous souhaitez mettre en oeuvre.</p><p>Cela ouvre une fenêtre qui vous permet de décrire l'action que vous souhaitez mettre en oeuvre: objectif, implications financières, responsable... Afin de mieux en tracer les détails opérationnels, vous avez également la possibilité d'associer un ou plusieurs fichiers à l'action.</p><h2>Exporter les actions</h2><img src=\"/assets/images/help/bouton_export_XLS.png\"><p>A tout moment, ce bouton vous permet d'exporter en format .xls l'ensemble de toutes vos actions afin de pouvoir l'utiliser hors outil de la manière qui vous convient le mieux pour suivre et implémenter ces actions.</p><h2>Consulter et modifier une action</h2><img src=\"/assets/images/help/details_actions.png\"><p>Pour relire l'ensemble des détails liés à une action, un simple clic sur le symbole + dans la colonne \"Détails\" fournit tous les détails de l'action choisie. En bas de ces détails, trois boutons vous permettent de re-travailler vos actions.</p><h3>Editer une action</h3><p>Ce bouton vous permet de re-modifier à l'envi tous les champs descriptifs de l'action</p><h3> Indiquer une action comme réalisée</h3><p>Le bouton \"Marquer une action comme réalisée\" permet de changer instantanément son statut en \"réalisé\" lors de l'année actuelle, sans devoir passer par le mode édition.</p><h3>Supprimer une action</h3><p>Contrairement au fait de passer une action en statut \"annulé\" pour ce souvenir de ce que l'action avait été identifiée mais s'est avérée infaisable, il est possible de purement et simplement supprimer une action, qui n'apparaîtra dès lors plus nulle part.</p><h2>Transformer un conseil en action</h2><p>Cliquez simplement sur le bouton \"En faire une action\", et une action basée sur le conseil sera créée pour vous. Libre ensuite à vous de la modifier comme n'importe quelle autre action que vous gérez.</p>");$templateCache.put('$/angular/views/enterprise/agreement_en.html', "<h1>Je suis l'agreement enterprise EN</h1>");$templateCache.put('$/angular/views/enterprise/help_organization_manager_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur la gestion de l'organisation</h1><h2>Introduction</h2><p>Accessible aux seuls utilisateurs avec un statut <b>administrateur</b>, cette interface permet de gérer trois aspects au niveau général de votre organisation: son nom, le partage des données avec l'AWAC pour un usage statitstique (cf. confidetnailité) et les facteurs infulants le bilan GES.</p><h2>Nom de l'organisation</h2><p>Vous pouvez changer ici le nom global de votre organisation tel qu'il apparaît tout en haut du calculateur.</p><h2>Partage des données avec l'AWAC</h2><p>En accord avec les principes édictés dans la confidentialité de l'outil, c'est ici que vous pouvez décider de partager ou non vos données avec l'AWAC pour alimenter la représentativitié de ses statistiques.</p><h2>Facteurs influants sur la performance GES</h2><p>Comme indiqué par le texte explicatif présent dans l'interface, vous pouvez ici décrire les changements principaux survenus telle ou telle année, et qui expliquent des variations significatives de votre bilan d'une année sur l'autre.</p><p>Cet aspect est pour votre bonne compréhension interne, mais peut également s'avérer particulièrement utile lorsque vous demandez une vérification externe de vos données: le vérificateur aura en effet accès à ces facteurs afin de mieux comprendre la nature de votre bilan.</p>");$templateCache.put('$/angular/views/enterprise/help_form_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide générale</h1><h2>Introduction</h2><p>Le but du présent calculateur est de vous permettre de réaliser le bilan GES de votre organisation de la manière la plus simple possible tout en respectant le prescrit du GHG protocol. C'est pourquoi l'AWAC a sélectionné pour vous les bonnes questions à se poser, et pour lesquelles il vous \"suffit\" de fournir les données vous concernant afin d'obtenir un bilan GES valide. La présente page d'aide générale vous détaille d'abord l'ensemble des boutons disponibles dans les menus du haut de l'application, avant de détailler la manière de fournir les données nécessaires au calcul du bilan.</p><h2>Interface générale</h2><p>L'image ci-dessous vous présente le menu principal de l'application:</p><img src=\"/assets/images/help/entreprise_menu.png\"><p>Les annotations numérotées  correspondent aux explications ci-dessous:</p><p>1. Chaque utilisateur se connecte à l'application via son identifiant et son mot de passe. Il peut à tout moment modifier ses coordonnées personnelles via le menu \"Mon profil\".</p><p>2. Chaque utilisateur peut demander à avoir les interfaces et les questions liées à la collecte de données dans la langue de son choix parmi les langues disponibles dans l'application.</p><p>3. Le calculateur entreprise permettant de consolider le bilan de l'organisation comme la somme de bilans sur différents sites, il faut que l'utilisateur choisisse un site parmis ceux auxquels il a accès. La gestion des sites (pour les créer et y donner accès) est effectuée par les administrateurs (le créateur d'un compte est en administrateur par défaut) via le bouton \"Gérer les sites\".</p><p>4. Tout administrateur peut également \"Gérer les utilisateurs\" pour les rendre administrateurs, inviter de nouveaux utilisateurs ou en désactiver certains.</p><p>5. Et les administrateurs peuvent également modifier certains paramètres d'organisation via le bouton \"Gérer l'organisation\".</p><p>6. L'utilisateur choisit alors pour quelle année (parmis celles demandées, cf. \"Gérer les sites\") il souhaite fournir des données</p><p>7. A tout moment, il peut enregistrer les données fournies. Pour chaque formulaire thématique de données (voir plus bas), l'application indique le moment du dernier enregitrement effectué.</p><p>8. L'utilisateur a aussi la possibilité de comparer les données avec celle d'une autre année, ce qui peut facilité le recopiage des données restées similaires (voir plus bas).</p><p>9. Les administrateurs peuvent également demander une vérification externe des données (voir plus bas).</p><h2>Fournir des données</h2><p>Les données à fournir sont divisées en plusieurs parties distinctes. Vous accédez à chaque partie en cliquant sur son titre.</p><img src=\"/assets/images/help/entreprise_forms.png\"><p></p><p>La partie sélectionnée est présentée sur fond vert. Pour chaque partie, une barre de progression vous indique quelle fraction des données escomptées vous avez déjà remplie.</p><p>Tout à droite, la partie \"résultats\" vous donne accès à la présentation du bilan CO2 en fonction des données que vous avez remplies, et la partie \"actions de réduction\" vous permet de définir de telles actions pour améliorer votre bilan d'année en année.</p><h3>Types de données</h3><p>Chaque utilisateur impliqué est invité à fournir les données qu'il maîtrise. Pour ce faire, il lui suffit de remplir les cases faites pour. Plusieurs types de données différentes sont indiquées avec des icones différentes:</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-share-alt\"></span> indique des questions dont les réponses sont nécessaires au calcul du résultat.</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-paperclip\"></span> indique à l'inverse des questions qui ne servent pas au résultat mais uniquement à vous aider à mémoriser le contexte.</p><p><span ng-class=\"getIcon()\" class=\"glyphicon glyphicon-file\"></span> indique des questions documentaires qui vous permettent de stocker vos documents relatifs à certains jeux de données, afin de pouvoir les re-consulter à tout moment.</p><p><span class=\"glyphicon glyphicon-record\"></span> indique des questions regroupées en un bloc qu'il vous appartient de répliquer autant de fois que nécessaire (voir ci-dessous, création de blocs de données)</p><p></p><p>Une icone est encore présente derrière la donnée: <button class=\"button edit_comment_icon glyphicon glyphicon-pencil\"></button> Il s'agit d'un champs de commentaires libres auquel vous accéder en cliquant dessus: cela vous permet de noter des aspects compélmentaires à la donnée fournie. Lorsqu'un commentaire est effectivement présent, cette icône devient verte: <button class=\"button edit_comment_icon glyphicon glyphicon-pencil edit_comment_icon_selected\"></button></p><p>Finalement, certaines questions sont également suivies par un symbole:</p><img src=\"/assets/images/info.png\"><p>Il s'agit d'informations complémentaires sur la question qui apparaissent en infobulle lorsqu'on passe la souris sur cette icône.</p><h3>Remplissage des données</h3><p>Pour toutes les questions attendues par le système pour offrir un résultat le plus exhaustif possible, une icône sous forme de main vient donner une indication complémentaire:</p><img src=\"/assets/images/status/pending.png\"><p>indique une question attendue mais pas encore répondue;</p><img src=\"/assets/images/status/answer_temp.png\"><p>indique une question répondue mais pas encore sauvée;</p><img src=\"/assets/images/status/answer.png\"><p>indique une question répondue et dont la réponse est bien enregistrée;</p><img src=\"/assets/images/status/pending_temp.png\"><p>indique une question dont on a effacé la réponse, mais pas encore sauvegardé ce changement.</p><p>Pour remplir la donnée, il suffit d'encoder celle-ci dans la case blanche. Une fois la donnée enregistrée, une case avec des initiales indique quel utilisateur a fourni cette donnée. Si on laisse la souris dessus, le nom complet de la personne apparaît.</p><img src=\"/assets/images/help/question_repondue.png\"><h3>Création de blocs de données</h3><p>Comme énoncé plus haut, le symbole <span class=\"glyphicon glyphicon-record\"></span> indique des questions regroupées en un bloc qu'il vous appartient de répliquer autant de fois que nécessaire.</p><img src=\"/assets/images/help/bloc_donnees.png\"><p>Dans de tels cas, le bouton \"Ajouter...\" vous permet de créer autant de blocs de données identiques que nécessaire dans votre cas. A tout moment, le bouton \"Supprimer\" vous permet d'effacer un de ces blocs.</p><h3>Comparaison des données d'une autre année et recopiage d'une année à l'autre</h3><p>Dès que vous choisissez de comparer les données avec celle d'une autre année (cf. point 8 de l'introduction du menu principal), les données passent en deux colonnes pour vous permettre de comparer, donnée par donnée, les deux années.</p><img src=\"/assets/images/help/comparaison_donnees.png\"><p>Le bouton <button class=\"button\" title=\"Copier la valeur\" >&lt;&lt;</button> vous permet de recopier d'un seul clic la valeur de l'année de référence dans la case de l'année en cours.</p><p>Dans le cas de blocs de données (cf. ci-dessus), il vous faut d'abord créer le bloc pour l'année en cours, et à ce moment-là les données de l'année de référence viendront se placer à côté et seront recopiables.</p><h3>Données alternatives</h3><p>A plusieurs endroits, des méthodes différentes existent pour collecter les données nécessaires. Elles vous sont chaque fois présentées de la meilleure, en vert clair, à la plus approximative (vert foncé, puis jaune, puis orange). L’idéal est d’utiliser la meilleure possible en fonction des données dont vous disposez. Pour le calcul du résultat, le système utilisera les données de la meilleure méthode entièrement complétée, ce qui est indiqué par le symbole <i class=\"glyphicon glyphicon-bell\"></i>.</p><img src=\"/assets/images/help/alternative.png\"><p>Dans l'exemple ci-dessus, les approches \"calcul par euros dépensés\" et \"calcul par les kilomètres\" ont été entièrement remplies, tandis que l'approche \"calcul par les consommations\" n'est pas complet. Le système indique donc qu'il utilisera les données de l'approche \"calcul par les kilomètres\". S'il n'y a pas de symbole <i class=\"glyphicon glyphicon-bell\"></i>, cela signifie qu'aucune des alternatives n'est complète et que le système ne saura pas prendre cette partie en compte dans le résultat.</p><h2>Travailler à plusieurs</h2><p>Afin de  faciliter la collecte des données nécessaires à un bilan complet, il est souvent plus efficace de pouvoir travailler à plusieurs collègues. C'est pourquoi plusieurs utilisateurs peuvent être invités sur le compte de l'organsation par le ou les administrateur(s). Il est toutefois important qu'un utilisateur n'annule pas le travail des autres. Divers mécanismes sont prévus pour cela.</p><p>Tout d'abord, il est impossible pour deux utilisateurs d'accéder en même temps à la même partie des données. Si un autre utilisateur est déjà en train de travailler sur une partie, vous recevrez un message vous y refusant l'accès en vous annonçant qui y travaille déjà.</p><p>Ensuite, deux mécanismes complémentaires permettent de gérer la collaboration.</p><h3>Se réserver des données</h3><p>Le cadenas qui figure en dessous de chaque rubrique de données permet à un utilisateur de bloquer l'ensemble des données de la rubrique pour son seul usage.</p><img src=\"/assets/images/help/localisation_cadenas.png\"><p>Seul cet utilisateur aura encore la possibilité de travailler sur ces données, tandis que les autres utilisateurs verront un cadenas fermé (le nom de l'utilisateur l'ayant fermé apparaît lorsque la souris est passée sur le cadenas).</p><p>L'utilisateur qui a verrouillé peut rouvrir le cadenas à tout moment en cliquant dessus.</p><h3>Valider des données</h3><p>En dessous du cadenas figure une autre icône dont le principe de fonctionnement est similaire.</p><img src=\"/assets/images/status/validate_not.png\"><p>Cette icône permet à un utilisateur de valider l'ensemble des données de la rubrique. C'est-à-dire d'indiquer que ces données ont été relues, sont correctes, et ne peuvent dès lors plus être modifiées. Dès lors, plus personne, cet utilisateur inclus, ne peut modifier les données concernées.</p><p><em>Note</em>: un administrateur peut toujours, à n'importe quel moment, annuler le cadenas ou la validation de n'importe quel utilisateur.</p><h2>Faire vérifier les données de manière externe</h2><p>Encore en dessous des icônes de validation, figure une icône de vérification:</p><img src=\"/assets/images/status/verified_unverified.png\"><p>Il s'agit d'indique le statut lorsque les données ont été vérifiées par une personne externe, typiquement un auditeur.</p><p>Pour pouvoir demander une vérification externe, il faut d'abord que toutes les données ait étées validées en interne (cf. ci-dessus).</p><img src=\"/assets/images/help/bouton_verification.png\"><p>Le bouton de demande de vérification devient alors vert pour indiquer qu'il est actif. Seuls les administrateurs le verront apparaître dans leur interface, et pour toute action qu'ils souhaiteront prendre eu égard à la vérification externe, ils devront chaque fois la confirmer avec leur mot de passe.</p><img src=\"/assets/images/help/demander_verification.png\"><p>Cliquer sur le bouton ouvre une fenêtre qui permet de demander la vérification à une personne externe en fournissant l'adresse de courrier électronique de la dite personne. Il appartiendra ensuite à cette personne de réagir et d'accepter le travail qui lui est demandé. Le suivi du processus se fait de manière très simple: à chaque fois qu'une action est nécessaire de la part de votre organisation, le ou les administrateur(s) du compte reçoivent un courriel leur expliquant le statut et la marche à suivre. En effet, ce bouton qui indique intialement \"Demander une vérification\" va changer de texte au fur et à mesure que le processus avance.</p><p>Tout à la fin du processus, si tout s'est bien passé, le rapport de vérification deviendra alors disponible dans la partie \"résultats\".</p>");$templateCache.put('$/angular/views/enterprise/TAB5.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><!--mm-awac-section(\"Déchets\")--><!--It lacks a proper fild code for \"D2chets alone\" -> TODO : insert into Excel file as an additional line--><mm-awac-section title-code=\"A173\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A173' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"A174\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A4999\"><mm-awac-repetition-name question-code=\"A5000\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A5000\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A5000')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A5001\" ng-map-index=\"{'A5000':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A5002\" ng-map-index=\"{'A5000':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A5003\" ng-map-index=\"{'A5000':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A5000\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A180\"><mm-awac-sub-title question-code=\"A181\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"A182\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A183\"></mm-awac-question><mm-awac-question question-code=\"A184\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A185\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A186\"></mm-awac-question><mm-awac-question question-code=\"A187\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A188\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A189\"></mm-awac-question><mm-awac-question question-code=\"A190\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A191\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A192\"></mm-awac-question><mm-awac-question question-code=\"A193\"></mm-awac-question><mm-awac-sub-title question-code=\"A194\"></mm-awac-sub-title><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><div class=\"status {{getStatusClassForTab(1,1)}}\"></div><span ng-bind-html=\"'A197' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A198\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A199\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A200\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><div class=\"status {{getStatusClassForTab(1,2)}}\"></div><span ng-bind-html=\"'A201' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A202\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A203\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A204\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/enterprise/help_site_manager_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur la gestion des sites</h1><h2>Introduction</h2><p>Accessible aux seuls utilisateurs avec un statut <b>administrateur</b>, cette interface permet de créer de nouveaux sites sur lesquels l'organisation souhaite calculer son bilan (et sommer les bilans de plusieurs sites).</p><p>Pour chaque site, cela permet également de déterminer quel utilisateur a accès à quel site, et pour quelles années des données doivent être collectées afin d'établir le bilan GES.</p><h2>Création d'un nouveau site</h2><img src=\"/assets/images/help/ajouter_site.png\"><p>Cliquez sur ce bouton pour créer un nouveau site. Cela ouvre la fenêtre de déclaration d'un site:</p><img src=\"/assets/images/help/creation_site.png\"><p>Il vous suffit de remplir les différents champs (le code NACE est optionnel, pour votre usage à vous). Le pourcentage à indiquer dans \"% des émissions GES comptabilisées par l'organisation\" dépend de la règle de consolidation que vous utilisez pour votre bilan GES, en conformité avec les recommandations du GHG Protocol. Il s'agit du % du bilan GES du site à prendre en compte dans le bilan GES de votre organisation selon le niveau de contrôle (opérationnel ou financier) que vous exercez sur ce site. Dans le cas de votre site principal, ou d'une filiale qui vous appartient exclusivement, la valeur est de 100%.</p><p>Une fois qu'un site est créé, vous pouvez à tout moment en modifier le nom, la description, et même le pourcentage à prendre en compte en cliquant sur l'icone présente dans la première colonne \"Editer le site\".</p><p>Un site n'est jamais détruit, afin que les données - même de bilans très anciens - ne soient jamais perdues. Par contre, s'il n'est plus associé à aucune année de bilan, il n'apparaîtra plus dans les interfaces de travail d'aucun utilisateur.</p><h2>Déterminer quel utilisateur a accès à quel site</h2><p>Lorsqu'un site est créé, aucun utilisateur n'y a accès par défaut. Il faut cliquer sur le bouton d'édition sous \"Editer les utilisateurs associés\" pour pouvoir alors déterminer clairement quel utilisateur aura ce site présent dans ses interfaces de travail.</p><img src=\"/assets/images/help/site_utilisateurs.png\"><p>On peut alors décider pour chaque site, qui a accès à ce site. Un utilisateur peut très bien avoir eu accès à un site pendant tout un temps et puis être dissocié du site: aucune des données qu'il a fournies ne sera perdue.</p><img src=\"/assets/images/help/associer_utilisateur.png\"><p><em>Note</em>: pour les utilisateurs avec le statut d'administrateur, il est aussi nécessaire de réaliser cette association.</p><h2>Déterminer pour quelle année le bilan d'un site est établi</h2><p>Afin de ne pas induire les utilisateurs en erreur et de les faire collecter des données inutiles à l'organisation, il faut également indiquer chaque année pour laquelle des données sont souhaitées, site par site.</p><img src=\"/assets/images/help/site_ann&eacute;es.png\"><p>Pour chaque année de bilan souhaité pour l'organisation, il faut cocher les sites qui participent à ce bilan.</p>");$templateCache.put('$/angular/views/enterprise/TAB6.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"A229\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A229' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"A230\"></mm-awac-question><mm-awac-repetition-name question-code=\"A231\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A231\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A231')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A232\" ng-map-index=\"{'A231':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A233\" ng-map-index=\"{'A231':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A234\" ng-map-index=\"{'A231':$index}\" ng-condition=\"(getAnswer('A233',itLevel1).value | stringToFloat) &lt; 18   || getAnswer('A233',itLevel1).value == '23'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A235\" ng-map-index=\"{'A231':$index}\" ng-condition=\"getAnswer('A233',itLevel1).value == '20' || getAnswer('A233',itLevel1).value == '21'|| getAnswer('A233',itLevel1).value == '22'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A236\" ng-map-index=\"{'A231':$index}\" ng-condition=\"getAnswer('A233',itLevel1).value == '18' || getAnswer('A233',itLevel1).value == '19'\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A231\"></mm-awac-repetition-add-button><mm-awac-sub-sub-title question-code=\"A237\"></mm-awac-sub-sub-title><mm-awac-repetition-name question-code=\"A238\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A238\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A238')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A239\" ng-map-index=\"{'A238':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A240\" ng-map-index=\"{'A238':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A241\" ng-map-index=\"{'A238':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A242\" ng-map-index=\"{'A238':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A238\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A309\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A309' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"A310\"></mm-awac-question><mm-awac-repetition-name question-code=\"A311\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A311\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A311')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A312\" ng-map-index=\"{'A311':$index}\"></mm-awac-question><mm-awac-repetition-name question-code=\"A313\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A313\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A313',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A314\" ng-map-index=\"{'A311':$parent.$parent.$index, 'A313':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A315\" ng-map-index=\"{'A311':$parent.$parent.$index, 'A313':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A313\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1012\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A1012\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1012',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1013\" ng-map-index=\"{'A311':$parent.$parent.$index, 'A1012':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1014\" ng-map-index=\"{'A311':$parent.$parent.$index, 'A1012':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1012\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1015\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A1015\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1015',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1016\" ng-map-index=\"{'A311':$parent.$parent.$index, 'A1015':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1017\" ng-map-index=\"{'A311':$parent.$parent.$index, 'A1015':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1015\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A316\" ng-map-index=\"{'A311':$index}\"></mm-awac-question><mm-awac-repetition-name question-code=\"A317\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A317\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A317',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A318\" ng-map-index=\"{'A311':$parent.$parent.$index, 'A317':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A319\" ng-map-index=\"{'A311':$parent.$parent.$index, 'A317':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A317\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A311\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A320\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A320' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"A321\"></mm-awac-question><mm-awac-repetition-name question-code=\"A322\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A322\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A322')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A323\" ng-map-index=\"{'A322':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A324\" ng-map-index=\"{'A322':$index}\"></mm-awac-question><mm-awac-repetition-name question-code=\"A325\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A325\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A325',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A326\" ng-map-index=\"{'A322':$parent.$parent.$index, 'A325':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A327\" ng-map-index=\"{'A322':$parent.$parent.$index, 'A325':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A325\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1018\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A1018\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1018',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1019\" ng-map-index=\"{'A322':$parent.$parent.$index, 'A1018':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1020\" ng-map-index=\"{'A322':$parent.$parent.$index, 'A1018':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1018\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1021\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A1021\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1021',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1022\" ng-map-index=\"{'A322':$parent.$parent.$index, 'A1021':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1023\" ng-map-index=\"{'A322':$parent.$parent.$index, 'A1021':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1021\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A328\" ng-map-index=\"{'A322':$index}\"></mm-awac-question><mm-awac-repetition-name question-code=\"A329\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A329\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A329',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A330\" ng-map-index=\"{'A322':$parent.$parent.$index, 'A329':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A331\" ng-map-index=\"{'A322':$parent.$parent.$index, 'A329':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A329\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A322\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A332\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A332' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"A333\"></mm-awac-question><mm-awac-repetition-name question-code=\"A334\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A334\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A334')\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A335\" ng-map-index=\"{'A334':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A336\" ng-map-index=\"{'A334':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A337\" ng-map-index=\"{'A334':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A338\" ng-map-index=\"{'A334':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A334\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/enterprise/agreement_fr.html', "<h1>Mentions légales du calculateur CO2 entreprise de l'AWAC</h1><h2> Information juridique - Conditions d'utilisation du site</h2><p>L'utilisation du présent site est soumise au respect des conditions générales décrites ci-après. En accédant à ce site, vous déclarez avoir pris connaissance et avoir accepté, sans la moindre réserve, ces conditions générales d'utilisation.</p><h2>Qualité de l'information et du service – Clause de non-responsabilité</h2><p>Nous apportons le plus grand soin à la gestion de ce site. Nous ne garantissons toutefois pas l'exactitude des informations qui y sont proposées. Celles-ci sont par ailleurs susceptibles d'être modifiées sans avis préalable. Il en résulte que nous déclinons toute responsabilité quant à l'utilisation qui pourrait être faite du contenu de ce site. Les liens hypertextes présents sur le site et aiguillant les utilisateurs vers d'autres sites Internet n'engagent pas notre responsabilité quant au contenu de ces sites.</p><h2>Protection des données à caractère personnel</h2><p>Il vous est loisible de visiter notre site internet sans nous fournir de données à caractère personnel (1). Vous pouvez accéder à la base de calcul et de résultats en ne saisissant que des données générales à caractère non personnel.</p><p>Si vous nous fournissez des informations personnelles (2), elles seront traitées conformément aux dispositions de la loi du 8 décembre 1992. Elles seront traitées, moyennant votre accord,  anonymement à des fins statistiques et pour vous adresser des messages électroniques généraux; elles ne seront pas communiquées à des tiers, à l’exception du sous-traitant avec lequel l’AWAC a conclu une convention relative à la confidentialité des données, ni utilisées à des fins commerciales.  L’AWAC s'engage à prendre les meilleures mesures de sécurité afin d'éviter que des tiers n'abusent des données à caractère personnel qui lui ont été communiquées.</p><p>Les données saisies en ligne et les résultats associés sont traités collectivement et anonymement. Ces données et résultats pourront être exploités, en totalité ou en partie, en vue de réalisations statistiques, de réalisations de rapports ou d’études relatives à la consommation d’énergie et d’émission de gaz à effet de serre.</p><h2>Droits de propriété intellectuelle</h2><p>Tous droits sur le contenu et l'architecture de notre site, et notamment les éléments suivants : textes, créations graphiques, logos, éléments sonores, publications, mises en page, bases de données, algorithmes de calculs... sont protégés par le droit national et international de la propriété intellectuelle.</p><p>A ce titre, vous ne pouvez procéder à une quelconque reproduction, représentation, adaptation, traduction et/ou transformation partielle ou intégrale, ou un transfert sur un autre site web de tout élément composant le site.</p><p>Cependant si vous souhaitez utiliser les éléments disponibles sur notre site, nous vous invitons à adresser votre demande d’autorisation à l’AWAC en précisant les éléments à reproduire et l’utilisation souhaitée.</p><h2>Création d'hyperliens vers le site</h2><p>Nous autorisons la création sans demande préalable de liens en surface (surface linking) qui renvoient à la page d'accueil du site ou à toute autre page dans sa globalité. Par contre, le recours à toutes techniques visant à inclure tout ou partie du site dans un site Internet en masquant ne serait-ce que partiellement l'origine exacte de l'information ou pouvant prêter à confusion sur l'origine de l'information, telle que le framing ou l'inlining, requiert une autorisation écrite.</p><h2>Modification des mentions légales</h2><p>L’AWAC se réserve le droit de modifier les présentes mentions à tout moment. L’utilisateur est lié par les conditions en vigueur lors de sa visite.</p><h2>Droit applicable en cas de litige</h2><p>Le droit applicable est le droit belge.</p><p>Tout litige relèvera de la compétence exclusive des tribunaux de Namur.</p><h3>Personne de Contact</h3><p>Cécile Batungwnanayo</p><p>Agence wallone de l'air et du Climat (AWAC)</p><p>7, Avenue Prince de Liège</p><p>B-5100 Jambes</p><P>Belgium</P><h2>Notes</h2><p>(1) La loi du 8 décembre 1992 relative au traitement de données à caractère personnel entend par « données à caractère personnel », toute information concernant une personne physique identifiée ou identifiable […] ; est réputée identifiable une personne qui peut être identifiée, directement ou indirectement, notamment par référence à un numéro d’identification ou à un plusieurs éléments spécifiques, propres à son identité physique, physiologique, psychique, économique, culturelle ou sociale. »</p><p>(2) Pour les personnes physiques, via la création d’un espace personnel sur le site avec votre e-mail et un mot de passe.  Si votre adresse e-mail est constituée de votre nom de famille, elle peut permettre votre identification.</p>");$templateCache.put('$/angular/views/enterprise/TAB2.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"A1\"><mm-awac-question question-code=\"A2\"></mm-awac-question><mm-awac-question question-code=\"A3\"></mm-awac-question><mm-awac-question question-code=\"A4\" ng-condition=\"getAnswer('A3').value == '1'\"></mm-awac-question><mm-awac-question question-code=\"A5\" ng-condition=\"getAnswer('A3').value == '2' || getAnswer('A3').value == '3'\"></mm-awac-question><mm-awac-question question-code=\"A6\" ng-condition=\"getAnswer('A3').value == '4'\"></mm-awac-question><mm-awac-question question-code=\"A9\"></mm-awac-question><mm-awac-question question-code=\"A10\"></mm-awac-question><mm-awac-question question-code=\"A11\" ng-condition=\"getAnswer('A3').value != '4'\"></mm-awac-question><mm-awac-question question-code=\"A12\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A13\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A13' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"A14\"></mm-awac-question><mm-awac-repetition-name question-code=\"A15\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A15\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A15')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A16\" ng-map-index=\"{'A15':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A17\" ng-map-index=\"{'A15':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A15\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1000\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A1000\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A1000')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A1001\" ng-map-index=\"{'A1000':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A1002\" ng-map-index=\"{'A1000':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1000\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1003\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A1003\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A1003')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A1004\" ng-map-index=\"{'A1003':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A1005\" ng-map-index=\"{'A1003':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1003\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A20\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A20' | translate\"></div></div><mm-awac-question ng-optional=\"true\" question-code=\"A21\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"A22\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"A23\"></mm-awac-question><mm-awac-question question-code=\"A24\"></mm-awac-question><mm-awac-repetition-name question-code=\"A25\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A25\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A25')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A26\" ng-map-index=\"{'A25':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A27\" ng-map-index=\"{'A25':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A28\" ng-map-index=\"{'A25':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A25\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A31\"><mm-awac-question question-code=\"A32\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"A33\" ng-condition=\"getAnswer('A32').value == '1'\"></mm-awac-question><mm-awac-block ng-condition=\"getAnswer('A32').value == '1'\"><mm-awac-repetition-name question-code=\"A34\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A34\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A34')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A35\" ng-map-index=\"{'A34':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A36\" ng-map-index=\"{'A34':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A34\"></mm-awac-repetition-add-button></mm-awac-block></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A37\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A37' | translate\"></div></div><mm-awac-question question-code=\"A38\"></mm-awac-question><mm-awac-question ng-optional=\"true\" question-code=\"A39\" ng-condition=\"getAnswer('A38').value == '1'\"></mm-awac-question></mm-awac-section><mm-awac-block ng-condition=\"getAnswer('A38').value == '1'\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><div class=\"status {{getStatusClassForTab(1,1)}}\"></div><span ng-bind-html=\"'A41' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A42\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A42\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A42')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A43\" ng-map-index=\"{'A42':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A44\" ng-map-index=\"{'A42':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A42\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><div class=\"status {{getStatusClassForTab(1,2)}}\"></div><span ng-bind-html=\"'A45' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A46\"></mm-awac-question></div></div></tab><tab class=\"tab-color-yellow\" ng-show=\"getAnswer('A5').value == '1' || getAnswer('A5').value == '2'\" active=\"getTab(1,3).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,3)\"></i><div class=\"status {{getStatusClassForTab(1,3)}}\"></div><span ng-bind-html=\"'A47' | translate\"></span></tab-heading><div class=\"sub_block tab-color-yellow\"><mm-awac-block class=\"element_table\" ng-condition=\"getAnswer('A5').value == '1' || getAnswer('A5').value == '2'\"><mm-awac-question ng-tab-set=\"1\" ng-tab=\"3\" question-code=\"A48\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"3\" question-code=\"A49\"></mm-awac-question></mm-awac-block></div></tab></tabset></div></div></mm-awac-block><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A6000\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A6000' | translate\"></div></div><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(2,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,1)\"></i><div class=\"status {{getStatusClassForTab(2,1)}}\"></div><span ng-bind-html=\"'A6002' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"2\" ng-tab=\"1\" question-code=\"A6003\"></mm-awac-question><mm-awac-question ng-tab-set=\"2\" ng-tab=\"1\" question-code=\"A6004\"></mm-awac-question><mm-awac-question ng-tab-set=\"2\" ng-tab=\"1\" question-code=\"A6005\"></mm-awac-question></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(2,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(2,2)\"></i><div class=\"status {{getStatusClassForTab(2,2)}}\"></div><span ng-bind-html=\"'A6006' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"2\" ng-tab=\"2\" question-code=\"A6007\"></mm-awac-question><mm-awac-question ng-tab-set=\"2\" ng-tab=\"2\" question-code=\"A6008\"></mm-awac-question><mm-awac-question ng-tab-set=\"2\" ng-tab=\"2\" question-code=\"A6009\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/enterprise/TAB4.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"A205\"><mm-awac-question ng-optional=\"true\" question-code=\"A206\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A208\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A208' | translate\"></div></div><mm-awac-repetition-name question-code=\"A209\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A209\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A209')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A210\" ng-map-index=\"{'A209':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A211\" ng-map-index=\"{'A209':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A212\" ng-map-index=\"{'A209':$index}\" ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_61'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A213\" ng-map-index=\"{'A209':$index}\" ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_62'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A214\" ng-map-index=\"{'A209':$index}\" ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_63'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A215\" ng-map-index=\"{'A209':$index}\" ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_64'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A216\" ng-map-index=\"{'A209':$index}\" ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_65'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A217\" ng-map-index=\"{'A209':$index}\" ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_66'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A218\" ng-map-index=\"{'A209':$index}\" ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_67'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A219\" ng-map-index=\"{'A209':$index}\" ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_68'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A220\" ng-map-index=\"{'A209':$index}\" ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_61' || getAnswer('A211',itLevel1).value == 'AT_62' || getAnswer('A211',itLevel1).value == 'AT_63' || getAnswer('A211',itLevel1).value == 'AT_64'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A221\" ng-map-index=\"{'A209':$index}\" ng-condition=\"getAnswer('A211',itLevel1).value != 'AT_68'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A222\" ng-map-index=\"{'A209':$index}\" ng-condition=\"getAnswer('A211',itLevel1).value == 'AT_68'\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A209\"></mm-awac-repetition-add-button><mm-awac-sub-title question-code=\"A223\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"A224\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A224\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A224')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A225\" ng-map-index=\"{'A224':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A226\" ng-map-index=\"{'A224':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A227\" ng-map-index=\"{'A224':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A228\" ng-map-index=\"{'A224':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A224\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A128\"><mm-awac-question ng-optional=\"true\" question-code=\"A129\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A130\"><mm-awac-sub-title question-code=\"A131\"></mm-awac-sub-title><mm-awac-question question-code=\"A133\"></mm-awac-question><mm-awac-question question-code=\"A134\"></mm-awac-question><mm-awac-question question-code=\"A135\"></mm-awac-question><mm-awac-question question-code=\"A136\"></mm-awac-question><mm-awac-question question-code=\"A137\" ng-condition=\"getAnswer('A136').value == '1'\"></mm-awac-question><mm-awac-question question-code=\"A138\" ng-condition=\"getAnswer('A136').value == '1'\"></mm-awac-question><mm-awac-question question-code=\"A139\" ng-condition=\"getAnswer('A138').value == '1'\"></mm-awac-question><mm-awac-question ng-aggregation=\"0.484\" question-code=\"A500\" ng-condition=\"getAnswer('A138').value == '0'\"></mm-awac-question><mm-awac-sub-title question-code=\"A140\"></mm-awac-sub-title><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'FORM_MULTI_METHOD' | translate\"></div></div><div class=\"method\"><tabset><tab class=\"tab-color-lightgreen\" active=\"getTab(1,1).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,1)\"></i><div class=\"status {{getStatusClassForTab(1,1)}}\"></div><span ng-bind-html=\"'A141' | translate\"></span></tab-heading><div class=\"sub_block tab-color-lightgreen\"><div class=\"element_text\" ng-bind-html=\"'DETAIL_UPSTREAM_KM' | translate\"></div><div class=\"element_table\"><mm-awac-repetition-name question-code=\"A142\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A142\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A142')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A143\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A145\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A146\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A147\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A148\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A149\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A150\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A151\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A152\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A153\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A154\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" question-code=\"A155\" ng-map-index=\"{'A142':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-tab-set=\"1\" ng-tab=\"1\" ng-aggregation=\"(getAnswer('A147',itLevel1).value | nullToZero)+( getAnswer('A148',itLevel1).value | nullToZero)+(getAnswer('A149',itLevel1).value | nullToZero)+(getAnswer('A150',itLevel1).value| nullToZero)+(getAnswer('A151',itLevel1).value| nullToZero)+(getAnswer('A152',itLevel1).value| nullToZero)+(getAnswer('A153',itLevel1).value| nullToZero)+(getAnswer('A154',itLevel1).value| nullToZero)+(getAnswer('A155',itLevel1).value| nullToZero)\" question-code=\"A156\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A142\"></mm-awac-repetition-add-button></div></div></tab><tab class=\"tab-color-green\" active=\"getTab(1,2).active\"><tab-heading><i class=\"glyphicon glyphicon-bell\" ng-show=\"tabIsMaster(1,2)\"></i><div class=\"status {{getStatusClassForTab(1,2)}}\"></div><span ng-bind-html=\"'A157' | translate\"></span></tab-heading><div class=\"sub_block tab-color-green\"><div class=\"element_table\"><mm-awac-question ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A158\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"2\" question-code=\"A159\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"2\" ng-aggregation=\"5000\" question-code=\"A160\" ng-condition=\"getAnswer('A159').value == '3'\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"2\" ng-aggregation=\"2500\" question-code=\"A161\" ng-condition=\"getAnswer('A159').value == '2'\"></mm-awac-question><mm-awac-question ng-tab-set=\"1\" ng-tab=\"2\" ng-aggregation=\"200\" question-code=\"A162\" ng-condition=\"getAnswer('A159').value == '1'\"></mm-awac-question></div></div></tab></tabset></div></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"A163\"><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'EXPLAIN_A163' | translate\"></div></div><div class=\"element_content\"><div class=\"element_text\" ng-bind-html=\"'INTRO_UPSTREAM_DISTRIBUTION' | translate\"></div></div><mm-awac-repetition-name question-code=\"A164\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"A164\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('A164')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A165\" ng-map-index=\"{'A164':$index}\"></mm-awac-question><mm-awac-repetition-name question-code=\"A166\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A166\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A166',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A167\" ng-map-index=\"{'A164':$parent.$parent.$index,'A166':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A168\" ng-map-index=\"{'A164':$parent.$parent.$index,'A166':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A166\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1006\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A1006\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1006',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1007\" ng-map-index=\"{'A164':$parent.$parent.$index,'A1006':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1008\" ng-map-index=\"{'A164':$parent.$parent.$index,'A1006':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1006\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-repetition-name question-code=\"A1009\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A1009\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A1009',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1010\" ng-map-index=\"{'A164':$parent.$parent.$index,'A1009':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A1011\" ng-map-index=\"{'A164':$parent.$parent.$index,'A1009':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A1009\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"A169\"></mm-awac-question><mm-awac-repetition-name question-code=\"A170\"></mm-awac-repetition-name><mm-awac-repetition-question ng-repetition-map=\"itLevel1\" question-set-code=\"A170\" ng-repeat=\"itLevel2 in getRepetitionMapByQuestionSet('A170',itLevel1)\" ng-iteration=\"itLevel2\"><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A171\" ng-map-index=\"{'A164':$parent.$parent.$index,'A170':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel2\" question-code=\"A172\" ng-map-index=\"{'A164':$parent.$parent.$index,'A170':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A170\" ng-iteration=\"itLevel1\"></mm-awac-repetition-add-button></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"A164\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/login.html', "<div class=\"loginBackground\"><div class=\"router-bar\"><div class=\"wallonie_logo\"></div><a class=\"awac_logo\" href=\"http://www.awac.be\" target=\"_blank\"></a><h1 ng-show=\"$root.instanceName == 'enterprise'\" ng-bind-html=\"'TITLE_ENTERPRISE' | translate\"></h1><h1 ng-show=\"$root.instanceName == 'municipality'\" ng-bind-html=\"'TITLE_MUNICIPALITY' | translate\"></h1><h1 ng-show=\"$root.instanceName == 'household'\" ng-bind-html=\"'TITLE_HOUSEHOLD' | translate\"></h1><h1 ng-show=\"$root.instanceName == 'event'\" ng-bind-html=\"'TITLE_EVENT' | translate\"></h1><h1 ng-show=\"$root.instanceName == 'littleemitter'\" ng-bind-html=\"'TITLE_LITTLE_EMITTER' | translate\"></h1><h1 ng-show=\"$root.instanceName == 'verification'\" ng-bind-html=\"'TITLE_VERIFICATION' | translate\"></h1><h1 ng-show=\"$root.instanceName == 'admin'\" ng-bind-html=\"'TITLE_ADMIN' | translate\"></h1></div><div class=\"loginFrame\" ng-enter=\"enterEvent()\"><select ng-model=\"$root.language\" ng-options=\"l.value as l.label for l in $root.languages\" style=\"float:right\"></select><tabset><tab class=\"tab-color-lightgreen\" active=\"tabActive[0]\"><tab-heading><span ng-bind-html=\"'LOGIN_CONNECTION' | translate\"></span></tab-heading><div><div class=\"field_form\"><form method=\"post\" action=\"\" id=\"loginForm\"><mm-awac-modal-field-text ng-info=\"loginInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></form></div><div ng-hide=\"isLoading === true\"><button class=\"button btn btn-primary\" ng-click=\"send({anonymous:false})\" ng-bind-html=\"'LOGIN_BUTTON' | translate\" type=\"button\" ng-disabled=\"!connectionFieldValid()\"></button><button class=\"button btn btn-primary\" ng-show=\"$root.instanceName == 'littleemitter' || $root.instanceName == 'household' || $root.instanceName == 'event'\" ng-click=\"send({anonymous:true})\" ng-bind-html=\"'LOGIN_ANONYMOUS_BUTTON' | translate\" type=\"button\" ng-disabled=\"connectionFieldValid()\"></button></div><img ng-show=\"isLoading === true\" src=\"/assets/images/modal-loading.gif\"></div></tab><tab class=\"tab-color-lightgreen\" ng-show=\"true\" active=\"tabActive[1]\"><tab-heading><span ng-bind-html=\"'LOGIN_FORGOT_PASSWORD' | translate\"></span></tab-heading><div><div ng-show=\"forgotEmailSuccessMessage!=null\" class=\"forgot_password_success_message\">{{forgotEmailSuccessMessage}}</div><div ng-hide=\"forgotEmailSuccessMessage!=null\"><div ng-bind-html=\"'LOGIN_FORGOT_PASSWORD_DESC' | translate\"></div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"forgotPasswordInfo\"></mm-awac-modal-field-text></div><div ng-hide=\"isLoading === true\"><button class=\"button btn btn-primary\" ng-click=\"sendForgotPassword()\" ng-bind-html=\"'SUBMIT' | translate\" type=\"button\" ng-disabled=\"!forgotPasswordFieldValid()\"></button></div><img ng-show=\"isLoading === true\" src=\"/assets/images/modal-loading.gif\"></div></div></tab><tab class=\"tab-color-lightgreen\" ng-show=\"$root.instanceName != 'verification' &amp;&amp; $root.instanceName != 'admin'\" active=\"tabActive[2]\"><tab-heading><span ng-bind-html=\"'LOGIN_REGISTRATION' | translate\"></span></tab-heading><div class=\"inject-registration-form\"></div></tab></tabset></div><div class=\"awac-footer\"><a class=\"awac-link\" href=\"http://www.awac.be/index.php/thematiques/changement-climatique/les-actions-chgmt-clim/j-agis-pour-le-climat\">Retour vers la page d'accueil des calculateurs CO2 de l'AWAC</a></div></div>");$templateCache.put('$/angular/views/change_password.html', "<div class=\"loginBackground\"><div class=\"loginFrame\" ng-enter=\"send()\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"oldPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordInfo_confirm\"></mm-awac-modal-field-text></div><p ng-show=\"errorMessage.length &gt; 0\" style=\"background-color:#ff0000;color:#ffffff;padding:15px\">{{errorMessage}}</p><button ng-click=\"send()\" ng-bind-html=\"'CHANGE_PASSWORD_BUTTON' | translate\" type=\"button\" class=\"btn btn-primary\"></button></div></div>");$templateCache.put('$/angular/views/no_scope.html', "<div><h1 ng-bind-html=\"'NO_SCOPE_TITLE' | translate\"></h1><div class=\"no-scope-message\" ng-bind-html=\"'NO_SCOPE_MESSAGE' | translate\"></div></div>");$templateCache.put('$/angular/views/littleemitter/TAB_P1.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AP2\"><mm-awac-question question-code=\"AP5\"></mm-awac-question><mm-awac-question question-code=\"AP6\" ng-condition=\"getAnswer('AP5').value == '5'\"></mm-awac-question><mm-awac-question question-code=\"AP7\"></mm-awac-question><mm-awac-question question-code=\"AP8\"></mm-awac-question><mm-awac-question question-code=\"AP9\"></mm-awac-question><mm-awac-question question-code=\"AP10\"></mm-awac-question><mm-awac-question question-code=\"AP11\"></mm-awac-question><mm-awac-question question-code=\"AP12\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AP10').value | nullToZero)\" question-code=\"AP13\" ng-condition=\"getAnswer('AP10').value &gt;= 300\"></mm-awac-question></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/littleemitter/TAB_P6.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AP187\"><mm-awac-question ng-optional=\"true\" question-code=\"AP188\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"AP189\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"AP190\"></mm-awac-question><mm-awac-question question-code=\"AP191\"></mm-awac-question><mm-awac-question question-code=\"AP192\"></mm-awac-question><mm-awac-question question-code=\"AP193\"></mm-awac-question><mm-awac-question question-code=\"AP194\"></mm-awac-question><mm-awac-question question-code=\"AP195\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AP190').value | nullToZero)+(getAnswer('AP191').value | nullToZero)+(getAnswer('AP192').value | nullToZero)+(getAnswer('AP193').value | nullToZero)+(getAnswer('AP194').value | nullToZero)+(getAnswer('AP195').value | nullToZero)\" question-code=\"AP196\"></mm-awac-question></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/littleemitter/TAB_P4.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AP140\"><mm-awac-question ng-optional=\"true\" question-code=\"AP141\"></mm-awac-question><mm-awac-sub-title question-code=\"AP142\"></mm-awac-sub-title><mm-awac-sub-sub-title question-code=\"AP143\"></mm-awac-sub-sub-title><mm-awac-block><mm-awac-repetition-name question-code=\"AP144\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AP144\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AP144')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP145\" ng-map-index=\"{'AP144':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP146\" ng-map-index=\"{'AP144':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP147\" ng-map-index=\"{'AP144':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP148\" ng-map-index=\"{'AP144':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP149\" ng-map-index=\"{'AP144':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP150\" ng-map-index=\"{'AP144':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AP144\"></mm-awac-repetition-add-button></mm-awac-block><mm-awac-sub-sub-title question-code=\"AP151\"></mm-awac-sub-sub-title><mm-awac-block><mm-awac-repetition-name question-code=\"AP152\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AP152\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AP152')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP153\" ng-map-index=\"{'AP152':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP154\" ng-map-index=\"{'AP152':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP155\" ng-map-index=\"{'AP152':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP156\" ng-map-index=\"{'AP152':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP157\" ng-map-index=\"{'AP152':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP158\" ng-map-index=\"{'AP152':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AP152\"></mm-awac-repetition-add-button></mm-awac-block></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/littleemitter/TAB_P5.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AP159\"><mm-awac-question ng-optional=\"true\" question-code=\"AP160\"></mm-awac-question><mm-awac-repetition-name question-code=\"AP161\" ng-condition=\"getAnswer('AP80').value == '1'\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AP161\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AP161')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP162\" ng-map-index=\"{'AP161':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP163\" ng-map-index=\"{'AP161':$index}\" ng-condition=\"getAnswer('AP162',itLevel1).value != '13' &amp;&amp;&nbsp;getAnswer('AP162',itLevel1).value != '14' &amp;&amp;&nbsp;getAnswer('AP162',itLevel1).value != '15' &amp;&amp;&nbsp;getAnswer('AP162',itLevel1).value != '16'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP164\" ng-map-index=\"{'AP161':$index}\" ng-condition=\"getAnswer('AP163',itLevel1).value == '1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP165\" ng-map-index=\"{'AP161':$index}\" ng-condition=\"getAnswer('AP163',itLevel1).value == '2'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP166\" ng-map-index=\"{'AP161':$index}\" ng-condition=\"getAnswer('AP162',itLevel1).value == '13' ||&nbsp;getAnswer('AP162',itLevel1).value == '14' ||&nbsp;getAnswer('AP162',itLevel1).value == '15' ||&nbsp;getAnswer('AP162',itLevel1).value == '16'\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AP161\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/littleemitter/agreement_en.html', "<h1>Je suis l'agreement enterprise EN</h1>");$templateCache.put('$/angular/views/littleemitter/help_organization_manager_fr.html', "<h1>Calculateur CO2 entreprise de l'AWAC: aide sur la gestion de l'organisation</h1><h2>Introduction</h2><p>Accessible aux seuls utilisateurs avec un statut <b>administrateur</b>, cette interface permet de gérer trois aspects au niveau général de votre organisation: son nom, le partage des données avec l'AWAC pour un usage statitstique (cf. confidetnailité) et les facteurs infulants le bilan GES.</p><h2>Nom de l'organisation</h2><p>Vous pouvez changer ici le nom global de votre organisation tel qu'il apparaît tout en haut du calculateur.</p><h2>Partage des données avec l'AWAC</h2><p>En accord avec les principes édictés dans la confidentialité de l'outil, c'est ici que vous pouvez décider de partager ou non vos données avec l'AWAC pour alimenter la représentativitié de ses statistiques.</p><h2>Facteurs influants sur la performance GES</h2><p>Comme indiqué par le texte explicatif présent dans l'interface, vous pouvez ici décrire les changements principaux survenus telle ou telle année, et qui expliquent des variations significatives de votre bilan d'une année sur l'autre.</p><p>Cet aspect est pour votre bonne compréhension interne, mais peut également s'avérer particulièrement utile lorsque vous demandez une vérification externe de vos données: le vérificateur aura en effet accès à ces facteurs afin de mieux comprendre la nature de votre bilan.</p>");$templateCache.put('$/angular/views/littleemitter/TAB_P2.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AP15\"><mm-awac-question ng-optional=\"true\" question-code=\"AP16\"></mm-awac-question><mm-awac-repetition-name question-code=\"AP17\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AP17\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AP17')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP18\" ng-map-index=\"{'AP17':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP19\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP18',itLevel1).value == 'AS_43'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP20\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"((getAnswer('AP19',itLevel1).value == false)||getAnswer('AP18',itLevel1).value != 'AS_43')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP21\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '1' &amp;&amp; (getAnswer('AP18',itLevel1).value == 'AS_1' || getAnswer('AP18',itLevel1).value == 'AS_3' || getAnswer('AP18',itLevel1).value == 'AS_376' || getAnswer('AP18',itLevel1).value == 'AS_377' || getAnswer('AP18',itLevel1).value == 'AS_4')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP22\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '1' &amp;&amp; (getAnswer('AP18',itLevel1).value == 'AS_10' || getAnswer('AP18',itLevel1).value == 'AS_11' || getAnswer('AP18',itLevel1).value == 'AS_13')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP23\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '1' &amp;&amp; getAnswer('AP18',itLevel1).value == 'AS_43'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP24\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '2'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP25\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '3' &amp;&amp; (getAnswer('AP18',itLevel1).value == 'AS_1' || getAnswer('AP18',itLevel1).value == 'AS_3' || getAnswer('AP18',itLevel1).value == 'AS_376' || getAnswer('AP18',itLevel1).value == 'AS_377' || getAnswer('AP18',itLevel1).value == 'AS_4')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP500\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '3' &amp;&amp; (getAnswer('AP18',itLevel1).value == 'AS_10' || getAnswer('AP18',itLevel1).value == 'AS_11' || getAnswer('AP18',itLevel1).value == 'AS_13')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP501\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '3' &amp;&amp; getAnswer('AP18',itLevel1).value == 'AS_43'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP26\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '2' &amp;&amp; getAnswer('AP18',itLevel1).value == 'AS_1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP27\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '2' &amp;&amp; (getAnswer('AP18',itLevel1).value == 'AS_3' || getAnswer('AP18',itLevel1).value == 'AS_4' || getAnswer('AP18',itLevel1).value == 'AS_376' || getAnswer('AP18',itLevel1).value == 'AS_377')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP28\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '2' &amp;&amp; (getAnswer('AP18',itLevel1).value == 'AS_11' || getAnswer('AP18',itLevel1).value == 'AS_13')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP29\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '2' &amp;&amp; getAnswer('AP18',itLevel1).value == 'AS_10'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP30\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '2' &amp;&amp; getAnswer('AP18',itLevel1).value == 'AS_43'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AP24', itLevel1).value | nullToZero)/(getAnswer('AP26', itLevel1).value | nullToZero)\" question-code=\"AP31\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '2' &amp;&amp; getAnswer('AP18',itLevel1).value == 'AS_1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AP24', itLevel1).value | nullToZero)/(getAnswer('AP27', itLevel1).value | nullToZero)\" question-code=\"AP32\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '2' &amp;&amp; (getAnswer('AP18',itLevel1).value == 'AS_3' || getAnswer('AP18',itLevel1).value == 'AS_4' || getAnswer('AP18',itLevel1).value == 'AS_376' || getAnswer('AP18',itLevel1).value == 'AS_377')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AP24', itLevel1).value | nullToZero)/(getAnswer('AP28', itLevel1).value | nullToZero)\" question-code=\"AP33\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '2' &amp;&amp; (getAnswer('AP18',itLevel1).value == 'AS_11' || getAnswer('AP18',itLevel1).value == 'AS_13')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AP24', itLevel1).value | nullToZero)/(getAnswer('AP29', itLevel1).value | nullToZero)\" question-code=\"AP34\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '2' &amp;&amp; getAnswer('AP18',itLevel1).value == 'AS_10'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AP24', itLevel1).value | nullToZero)/(getAnswer('AP30', itLevel1).value | nullToZero)\" question-code=\"AP35\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '2' &amp;&amp; getAnswer('AP18',itLevel1).value == 'AS_43'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AP25', itLevel1).value | nullToZero)*(getAnswer('AP11').value | nullToZero)/(getAnswer('AP12').value | nullToZero)\" question-code=\"AP36\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '3' &amp;&amp; (getAnswer('AP18',itLevel1).value == 'AS_1' || getAnswer('AP18',itLevel1).value == 'AS_3' || getAnswer('AP18',itLevel1).value == 'AS_376' || getAnswer('AP18',itLevel1).value == 'AS_377' || getAnswer('AP18',itLevel1).value == 'AS_4')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AP500', itLevel1).value | nullToZero)*(getAnswer('AP11').value | nullToZero)/(getAnswer('AP12').value | nullToZero)\" question-code=\"AP37\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '3' &amp;&amp; (getAnswer('AP18',itLevel1).value == 'AS_10' || getAnswer('AP18',itLevel1).value == 'AS_11' || getAnswer('AP18',itLevel1).value == 'AS_13')\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"(getAnswer('AP501', itLevel1).value | nullToZero)*(getAnswer('AP11').value | nullToZero)/(getAnswer('AP12').value | nullToZero)\" question-code=\"AP38\" ng-map-index=\"{'AP17':$index}\" ng-condition=\"getAnswer('AP20',itLevel1).value == '3' &amp;&amp; getAnswer('AP18',itLevel1).value == 'AS_43'\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AP17\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AP41\"><mm-awac-question ng-optional=\"true\" question-code=\"AP42\"></mm-awac-question><mm-awac-question question-code=\"AP43\"></mm-awac-question><mm-awac-question question-code=\"AP44\"></mm-awac-question><mm-awac-question question-code=\"AP45\" ng-condition=\"getAnswer('AP44').value == '1'\"></mm-awac-question><mm-awac-question question-code=\"AP46\" ng-condition=\"getAnswer('AP44').value == '2'\"></mm-awac-question><mm-awac-question question-code=\"AP47\" ng-condition=\"getAnswer('AP44').value == '2'\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AP46').value | nullToZero)/(getAnswer('AP47').value | nullToZero)\" question-code=\"AP48\" ng-condition=\"getAnswer('AP44').value == '2'\"></mm-awac-question><mm-awac-question question-code=\"AP49\" ng-condition=\"getAnswer('AP44').value == '3'\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AP49').value | nullToZero)*(getAnswer('AP11').value | nullToZero)/(getAnswer('AP12').value | nullToZero)\" question-code=\"AP50\" ng-condition=\"getAnswer('AP44').value == '3'\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AP51\"><mm-awac-question ng-optional=\"true\" question-code=\"AP52\"></mm-awac-question><mm-awac-repetition-name question-code=\"AP53\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AP53\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AP53')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP54\" ng-map-index=\"{'AP53':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP55\" ng-map-index=\"{'AP53':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AP53\"></mm-awac-repetition-add-button></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AP56\"><mm-awac-question ng-optional=\"true\" question-code=\"AP57\"></mm-awac-question><mm-awac-repetition-name question-code=\"AP58\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AP58\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AP58')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP59\" ng-map-index=\"{'AP58':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP60\" ng-map-index=\"{'AP58':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AP58\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/views/littleemitter/TAB_P3.html', "<div><div class=\"form-error-message\" ng-show=\"errorMessage != null\">{{errorMessage}}</div><div ng-show=\"loading===false &amp;&amp; errorMessage === null\"><mm-awac-section title-code=\"AP62\"><mm-awac-question ng-optional=\"true\" question-code=\"AP63\"></mm-awac-question><mm-awac-question question-code=\"AP64\"></mm-awac-question><mm-awac-sub-title ng-show=\"getAnswer('AP64').value == '1'\" question-code=\"AP65\"></mm-awac-sub-title><mm-awac-question question-code=\"AP66\" ng-condition=\"getAnswer('AP64').value == '1'\"></mm-awac-question><mm-awac-question question-code=\"AP67\" ng-condition=\"getAnswer('AP64').value == '1'\"></mm-awac-question><mm-awac-question question-code=\"AP68\" ng-condition=\"getAnswer('AP64').value == '1'\"></mm-awac-question><mm-awac-sub-title ng-show=\"getAnswer('AP64').value == '2'\" question-code=\"AP69\"></mm-awac-sub-title><mm-awac-block ng-show=\"getAnswer('AP64').value == '2'\"><mm-awac-repetition-name ng-show=\"getAnswer('AP64').value == '2'\" question-code=\"AP70\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AP70\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AP70')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP71\" ng-map-index=\"{'AP70':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP73\" ng-map-index=\"{'AP70':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP76\" ng-map-index=\"{'AP70':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP77\" ng-map-index=\"{'AP70':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-show=\"getAnswer('AP64').value == '2'\" question-set-code=\"AP70\"></mm-awac-repetition-add-button></mm-awac-block></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AP78\"><mm-awac-question ng-optional=\"true\" question-code=\"AP79\"></mm-awac-question><mm-awac-question question-code=\"AP80\"></mm-awac-question><mm-awac-block ng-show=\"getAnswer('AP80').value == '1'\"><!--questions déplacements employés--><mm-awac-repetition-name ng-show=\"getAnswer('AP80').value == '1'\" question-code=\"AP81\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AP81\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AP81')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP82\" ng-map-index=\"{'AP81':$index}\" ng-condition=\"getAnswer('AP80').value == '1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP83\" ng-map-index=\"{'AP81':$index}\" ng-condition=\"getAnswer('AP80').value == '1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" ng-aggregation=\"true\" question-code=\"AP84\" ng-map-index=\"{'AP81':$index}\" ng-condition=\"getAnswer('AP80').value == '1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP85\" ng-map-index=\"{'AP81':$index}\" ng-condition=\"getAnswer('AP83', itLevel1).value == '1' || getAnswer('AP83', itLevel1).value == '2' || getAnswer('AP83', itLevel1).value == '3' || getAnswer('AP83', itLevel1).value == '8'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP86\" ng-map-index=\"{'AP81':$index}\" ng-condition=\"getAnswer('AP80').value == '1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP87\" ng-map-index=\"{'AP81':$index}\" ng-condition=\"getAnswer('AP80').value == '1'\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP90\" ng-map-index=\"{'AP81':$index}\" ng-condition=\"getAnswer('AP83', itLevel1).value == '1' || getAnswer('AP83', itLevel1).value == '2' || getAnswer('AP83', itLevel1).value == '3' || getAnswer('AP83', itLevel1).value == '8'\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button ng-show=\"getAnswer('AP80').value == '1'\" question-set-code=\"AP81\"></mm-awac-repetition-add-button></mm-awac-block><mm-awac-sub-title ng-show=\"getAnswer('AP80').value == '2'\" question-code=\"AP91\"></mm-awac-sub-title><mm-awac-question question-code=\"AP92\" ng-condition=\"getAnswer('AP80').value == '2'\"></mm-awac-question><mm-awac-sub-sub-title ng-show=\"getAnswer('AP80').value == '2'\" question-code=\"AP93\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"AP94\" ng-condition=\"getAnswer('AP80').value == '2'\"></mm-awac-question><mm-awac-sub-sub-title ng-show=\"getAnswer('AP94').value &gt; 0\" question-code=\"AP95\"></mm-awac-sub-sub-title><mm-awac-question question-code=\"AP96\" ng-condition=\"getAnswer('AP94').value &gt; 0\"></mm-awac-question><mm-awac-question question-code=\"AP97\" ng-condition=\"getAnswer('AP94').value &gt; 0\"></mm-awac-question><mm-awac-question question-code=\"AP98\" ng-condition=\"getAnswer('AP94').value &gt; 0\"></mm-awac-question><mm-awac-question question-code=\"AP99\" ng-condition=\"getAnswer('AP80').value == '2'\"></mm-awac-question><mm-awac-question question-code=\"AP100\" ng-condition=\"getAnswer('AP80').value == '2'\"></mm-awac-question><mm-awac-question question-code=\"AP101\" ng-condition=\"getAnswer('AP80').value == '2'\"></mm-awac-question><mm-awac-question question-code=\"AP102\" ng-condition=\"getAnswer('AP80').value == '2'\"></mm-awac-question><mm-awac-question question-code=\"AP103\" ng-condition=\"getAnswer('AP80').value == '2'\"></mm-awac-question><mm-awac-question question-code=\"AP104\" ng-condition=\"getAnswer('AP80').value == '2'\"></mm-awac-question><mm-awac-question ng-aggregation=\"(getAnswer('AP94').value | nullToZero)+(getAnswer('AP99').value | nullToZero)+(getAnswer('AP100').value | nullToZero)+(getAnswer('AP101').value | nullToZero)+(getAnswer('AP102').value | nullToZero)+(getAnswer('AP103').value | nullToZero)+(getAnswer('AP104').value | nullToZero)\" question-code=\"AP105\" ng-condition=\"getAnswer('AP80').value == '2'\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AP106\"><mm-awac-question ng-optional=\"true\" question-code=\"AP107\"></mm-awac-question><mm-awac-sub-sub-title question-code=\"AP108\"></mm-awac-sub-sub-title><mm-awac-repetition-name question-code=\"AP109\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AP109\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AP109')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP110\" ng-map-index=\"{'AP109':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP112\" ng-map-index=\"{'AP109':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP116\" ng-map-index=\"{'AP109':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP117\" ng-map-index=\"{'AP109':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AP109\"></mm-awac-repetition-add-button><mm-awac-question question-code=\"AP118\"></mm-awac-question><mm-awac-question question-code=\"AP119\"></mm-awac-question><mm-awac-question question-code=\"AP120\"></mm-awac-question></mm-awac-section><div class=\"horizontal_separator\"></div><mm-awac-section title-code=\"AP121\"><mm-awac-question ng-optional=\"true\" question-code=\"AP122\"></mm-awac-question><mm-awac-sub-title question-code=\"AP123\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AP124\" ng-condition=\"getAnswer('AP80').value == '1'\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AP124\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AP124')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP125\" ng-map-index=\"{'AP124':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP127\" ng-map-index=\"{'AP124':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP131\" ng-map-index=\"{'AP124':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP132\" ng-map-index=\"{'AP124':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AP124\"></mm-awac-repetition-add-button><mm-awac-sub-title question-code=\"AP900\"></mm-awac-sub-title><mm-awac-question question-code=\"AP133\"></mm-awac-question><mm-awac-sub-title question-code=\"AP600\"></mm-awac-sub-title><mm-awac-repetition-name question-code=\"AP134\" ng-condition=\"getAnswer('AP80').value == '1'\"></mm-awac-repetition-name><mm-awac-repetition-question question-set-code=\"AP134\" ng-repeat=\"itLevel1 in getRepetitionMapByQuestionSet('AP134')\" ng-iteration=\"itLevel1\"><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP135\" ng-map-index=\"{'AP134':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP136\" ng-map-index=\"{'AP134':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP137\" ng-map-index=\"{'AP134':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP138\" ng-map-index=\"{'AP134':$index}\"></mm-awac-question><mm-awac-question ng-repetition-map=\"itLevel1\" question-code=\"AP139\" ng-map-index=\"{'AP134':$index}\"></mm-awac-question></mm-awac-repetition-question><mm-awac-repetition-add-button question-set-code=\"AP134\"></mm-awac-repetition-add-button></mm-awac-section></div><mm-awac-form-navigator></mm-awac-form-navigator></div>");$templateCache.put('$/angular/templates/mm-awac-repetition-name.html', "<div><div class=\"repetition-title\"><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span class=\"glyphicon glyphicon-record\"></span><span ng-bind-html=\"getQuestionCode() | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-question.html', "<div class=\"question_field\" ng-class=\"{'twoanswer':displayOldDatas()===true, 'oneanswer':displayOldDatas()===false,'condition-false':getCondition() === false}\"><div><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-class=\"getIcon()\" class=\"glyphicon\"></span><span ng-click=\"logQuestionCode()\" ng-class=\"{optional : getOptional() === true}\" ng-bind-html=\"getQuestionCode() | translate\"></span></div><div><div class=\"status\" ng-class=\"getStatusClass()\"></div><div class=\"error_message_numbers_only\" ng-show=\"errorMessage.length&gt;0\"><div>{{errorMessage}}</div><img src=\"/assets/images/question_field_error_message_icon_arrow.png\"></div><span class=\"inject-data\"></span><div class=\"edit_comment_icon\"><button class=\"button edit_comment_icon glyphicon glyphicon-pencil\" ng-click=\"editComment(!isDisabled())\" ng-class=\"{edit_comment_icon_selected:getAnswer().comment !=null}\" name=\"{{ getQuestionCode() }}_COMMENT\" ng-hide=\"getAggregation()!=null || isDisabled() &amp;&amp; getAnswer().comment == null\"></button></div><div class=\"user_icon {{getUserClass()}}\" ng-hide=\"getAggregation()!=null || getAnswer().value == null\">{{getUserName(false,true)}}<div><span>{{getUserName(false,false)}}</span><img src=\"/assets/images/user_icon_arrow.png\"></div></div></div><div class=\"question_comparison\" ng-show=\"displayOldDatas() === true &amp;&amp; getAnswer(true) != null\"><div><button class=\"button\" ng-click=\"copyDataToCompare()\" title=\"Copier la valeur\" ng-hide=\"isDisabled()\"><<</button></div><span class=\"inject-data-to-compare\"></span><div class=\"edit_comment_icon\"><button class=\"button edit_comment_icon glyphicon glyphicon-pencil edit_comment_icon_selected\" ng-click=\"editComment(false)\" name=\"OLD_{{ getQuestionCode() }}_COMMENT\" ng-hide=\"getAggregation()!=null || getAnswer(true).comment ==null\"></button></div><div class=\"user_icon\">{{getUserName(true,true)}}<div><div>{{getUserName(true,false)}}</div><img src=\"/assets/images/user_icon_arrow.png\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-sub-title.html', "<div><div class=\"sub_title\"><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-real-question.html', "<input class=\"oneelement\" ng-model=\"getAnswer().value\" numbers-only=\"double\" name=\"{{ getQuestionCode() }}\" style=\"text-align:right;\" type=\"text\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\">");$templateCache.put('$/angular/templates/mm-awac-percentage-question.html', "<span class=\"twoelement\"><input ng-model=\"getAnswer().value\" numbers-only=\"percent\" name=\"{{ getQuestionCode() }}\" style=\"text-align:right;\" type=\"text\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\"><span style=\"margin-left:5px\">%</span></span>");$templateCache.put('$/angular/templates/mm-awac-integer-question.html', "<input class=\"oneelement\" ng-model=\"getAnswer().value\" numbers-only=\"integer\" name=\"{{ getQuestionCode() }}\" style=\"text-align:right;\" type=\"text\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\">");$templateCache.put('$/angular/templates/mm-awac-repetition-add-button.html', "<button class=\"button add-repetition-button\" ng-click=\"addIteration()\" type=\"button\" ng-hide=\"isQuestionLocked() === true\"><span ng-bind-html=\"'ADD_NEW_ITERATION' | translate\" style=\"margin-right : 5px\"></span><span ng-bind-html=\"getQuestionSetCode() + '_LOOPDESC' | translate\"></span></button>");$templateCache.put('$/angular/templates/mm-awac-string-question.html', "<input class=\"oneelement\" ng-model=\"getAnswer().value\" name=\"{{ getQuestionCode() }}\" style=\"text-align:right;\" type=\"text\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\">");$templateCache.put('$/angular/templates/mm-awac-real-with-unit-question.html', "<span class=\"twoelement\"><input ng-model=\"getAnswer().value\" numbers-only=\"double\" name=\"{{ getQuestionCode() }}\" style=\"text-align:right;\" type=\"text\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\"><select ng-model=\"getAnswer().unitCode\" ng-options=\"p.code as p.name for p in getUnits()\" name=\"{{ getQuestionCode() }}_UNIT\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\"></select></span>");$templateCache.put('$/angular/templates/mm-awac-repetition-question.html', "<div ng-class=\"{true:'condition-false', false:''}[getCondition() === false]\"><div class=\"repetition-question\"><div class=\"repetition-question-title\" ng-bind-html=\"getQuestionSetCode() + '_LOOPDESC' | translate\" style=\"display : inline-block; margin-right : 20px\"></div><button class=\"button remove-button\" ng-click=\"removeAnwser()\" ng-bind-html=\"'DELETE' | translate\" type=\"button\" ng-hide=\"isQuestionLocked() === true\"></button><div class=\"repetition-question-container\"><ng-virtual ng-transclude class=\"element_stack\"></ng-virtual></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-boolean-question.html', "<span class=\"twoelement\"><span style=\"text-align:center\"><span style=\"vertical-align:middle;margin-right : 15px;\" ng-bind-html=\"'YES' | translate\"></span><input ng-model=\"getAnswer().value\" name=\"{{radioName}}\" style=\"width :20px !important;margin:0;vertical-align:middle;\" type=\"radio\" value=\"1\" ng-disabled=\"getDataToCompare()===true || getIsAggregation()===true || getDisabled() === true\"></span><span style=\"text-align:center\"><span style=\"vertical-align:middle;margin-right : 15px;\" ng-bind-html=\"'NO' | translate\"></span><input ng-model=\"getAnswer().value\" name=\"{{radioName}}\" style=\"width :20px !important;margin:0;vertical-align:middle;\" type=\"radio\" value=\"0\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true|| getDisabled() === true\"></span></span>");$templateCache.put('$/angular/templates/mm-awac-select-question.html', "<select class=\"oneelement\" ng-model=\"getAnswer().value\" ng-options=\"p.key as p.label for p in getOptions()\" name=\"{{ getQuestionCode() }}\" style=\"text-align:right;\" ng-disabled=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\"></select>");$templateCache.put('$/angular/templates/mm-awac-document-question.html', "<div class=\"oneelement document-question-block document-question\"><div ng-bind-html=\"'QUESTION_FILE_UPLOAD' | translate\" ng-hide=\"getDataToCompare()==true|| getIsAggregation()===true\"></div><div ng-show=\"inDownload=== true &amp;&amp; percent != 100\" class=\"document-question-progress-bar\"><div ng-style=\"style\"><spa></spa></div></div><div class=\"document-question-progress-percentage\" ng-show=\"inDownload=== true &amp;&amp; percent != 100\">{{percent}} %</div><div ng-show=\"inDownload=== true &amp;&amp; percent == 100\" ng-bind-html=\"'QUESTION_FILE_TREATEMENT' | translate\"></div><input ng-file-select=\"onFileSelect($files)\" name=\"{{ getQuestionCode() }}\" type=\"file\" ng-hide=\"getDataToCompare()==true|| getIsAggregation()===true || inDownload === true || getDisabled() === true\"><div ng-show=\"getFileNumber()&gt;0\">{{getFileNumber()}} {{'QUESTION_FILE_COUNT_ALREADY_UPLOAD' | translateText}}</div><button class=\"button\" ng-show=\"getFileNumber()&gt;0\" ng-click=\"openDocumentManager()\" ng-bind-html=\"'QUESTION_FILE_CONSULT' | translate\" type=\"button\"></button></div>");$templateCache.put('$/angular/templates/mm-awac-date-question.html', "<div class=\"oneelement\"><input ng-show=\"getDataToCompare() === true\" ng-model=\"getAnswer().value\" type=\"text\" input-date ng-disabled=\"true\"><a class=\"dropdown-toggle\" data-target=\"#\" data-toggle=\"dropdown\" role=\"button\" id=\"{{id}}\" href=\"\" ng-hide=\"getDataToCompare()==true || getIsAggregation()===true || getDisabled() === true\"><div class=\"input-group\"><input class=\"form-control\" ng-model=\"getAnswer().value\" type=\"text\" input-date><span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-calendar\"></i></span></div><ul class=\"dropdown-menu date_input\" role=\"menu\" aria-labelledby=\"dLabel\"><datetimepicker data-datetimepicker-config=\"{ dropdownSelector: '{{idHtag}}', minView:'hour'}\" data-ng-model=\"result\"></datetimepicker></ul></a><span ng-show=\"ngIsAggregation\"><span>{{ (getAnswer().value / 3600000) | number:0 }}</span><span>&nbsp;</span><span ng-bind-html=\"'HOURS' | translate\"></span></span></div>");$templateCache.put('$/angular/templates/mm-awac-sub-sub-title.html', "<div><div class=\"sub_sub_title\"><div class=\"question_info\" ng-show=\"hasDescription()\"><div class=\"question_info_popup\" ng-bind-html=\"getQuestionCode() + '_DESC' | translate\"></div></div><span ng-bind-html=\"getQuestionCode() | translate\"></span><ng-virtual ng-transclude></ng-virtual></div></div>");$templateCache.put('$/angular/templates/mm-awac-block.html', "<ng-virtual><div ng-transclude ng-class=\"{true:'condition-false', false:''}[getCondition() === false]\"></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-section.html', "<ng-virtual><div class=\"element\"><div class=\"element_header\"><div class=\"title\" ng-bind-html=\"getTitleCode() | translate\"></div><div class=\"title_arrow\"></div></div><div class=\"element_sidebar\" ng-show=\"$root.instanceName === 'enterprise' || $root.instanceName === 'municipality' || $root.instanceName === 'event'\"><div class=\"block_status\"><div class=\"lock_status\" ng-click=\"lock()\" ng-class=\"getLockClass()\"><div ng-show=\"getLocker() != null\" ng-bind-html=\"'LOCK_BY' | translateWithVars: [getLockerName()] \"></div></div><div class=\"validate_status\" ng-click=\"valide()\" ng-class=\"getValidateClass()\"><div ng-show=\"getValidator() != null\" ng-bind-html=\"'VALIDATE_BY' | translateWithVars: [getValidatorName()] \"></div></div><div class=\"verified\" ng-show=\"$root.instanceName == 'enterprise' || $root.instanceName == 'municipality'\" ng-click=\"isDisabled || displayVerificationRejectedMessage()\" ng-class=\"getVerificationClass(false,true)\"><div ng-show=\"getVerifier() != null\" ng-bind-html=\"'VERIFIED_BY' | translateWithVars: [getVerifierName()] \"></div></div></div></div><div class=\"element_sidebar\" ng-show=\"$root.instanceName  === 'verification'\"><div class=\"block_status\"><div class=\"verification_status_approval\" ng-click=\"isDisabled || verification(true)\" ng-class=\"getVerificationClass(true)\" ng-hide=\"isVerificationDisabled() === true\"><div ng-show=\"getVerifier() != null\" ng-bind-html=\"'VERIFIED_BY' | translateWithVars: [getVerifierName()] \"></div></div><div class=\"verification_status_reject\" ng-click=\"isDisabled || verification(false)\" ng-class=\"getVerificationClass(false)\" ng-hide=\"isVerificationDisabled()=== true\"><div ng-show=\"getVerifier() != null\" ng-bind-html=\"'VERIFIED_BY' | translateWithVars: [getVerifierName()] \"></div></div><div class=\"verified\" ng-show=\"isVerificationDisabled()=== true\" ng-class=\"getVerificationClass(false,true)\" ng-click=\"isDisabled || displayVerificationRejectedMessage()\"><div ng-show=\"getVerifier() != null\" ng-bind-html=\"'VERIFIED_BY' | translateWithVars: [getVerifierName()] \"></div></div></div></div><div class=\"element_content\"><div ng-transclude ng-class=\"getMode()\"></div></div></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-result-table-simple.html', "<ng-virtual><table class=\"indicators_table\"><thead><tr><th></th><th><span class=\"period-header wrapped\" style=\"color: {{ ngModel.leftColor }}; border-bottom-color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"('SCOPE_SIMPLE' | translate) + ' ' + ngModel.leftPeriod\"></span><span> (tCO2e)</span></span></th><th ng-show=\"ngModel.rightPeriod!=null\"><span class=\"period-header wrapped\" style=\"color: {{ ngModel.rightColor }}; border-bottom-color: {{ ngModel.rightColor }}\"><span ng-bind-html=\"('SCOPE_SIMPLE' | translate) + ' ' +  ngModel.rightPeriod\"></span><span> (tCO2e)</span></span></th><th ng-show=\"getTypicalResultTotal() &gt; 0\"><span class=\"period-header wrapped\" style=\"color: {{ ngTypeColor }}; border-bottom-color: {{ ngTypeColor }}\"><span ng-bind-html=\"getTypicalResultLabelKey() | translate\"></span><span> (tCO2e)</span></span></th><th ng-show=\"getIdealResultTotal() &gt; 0\"><span class=\"period-header wrapped\" style=\"color: {{ ngIdealColor }}; border-bottom-color: {{ ngIdealColor }}\"><span ng-bind-html=\"getIdealResultLabelKey() | translate\"></span><span> (tCO2e)</span></span></th></tr></thead><tbody><tr ng-show=\"showAll || (rl.leftScope1Value + rl.leftScope2Value + rl.leftScope3Value + rl.leftOutOfScopeValue + rl.rightScope1Value + rl.rightScope2Value + rl.rightScope3Value + rl.rightOutOfScopeValue &gt; 0)\" ng-repeat=\"rl in ngModel.reportLines | orderBy:'order'\"><td><span ng-bind-html=\"rl.indicatorName | translate\"></span></td><td class=\"align-right scope1\" style=\"color: {{ ngModel.leftColor }}\"><span ng-show=\"(rl.leftScope1Value + rl.leftScope2Value + rl.leftScope3Value) &gt; 0\" ng-bind-html=\"(rl.leftScope1Value + rl.leftScope2Value + rl.leftScope3Value) | numberToI18NOrLess\"></span></td><td class=\"align-right scope1\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><span ng-show=\"(rl.rightScope1Value + rl.rightScope2Value + rl.rightScope3Value) &gt; 0\" ng-bind-html=\"(rl.rightScope1Value + rl.rightScope2Value + rl.rightScope3Value) | numberToI18NOrLess\"></span></td><td class=\"align-right scope1\" ng-show=\"getTypicalResultTotal() &gt; 0\" style=\"color: {{ ngTypeColor }}\"><span ng-bind-html=\"ngTypeMap[rl.indicatorName] | numberToI18NOrLess\"></span></td><td class=\"align-right scope1\" ng-show=\"getIdealResultTotal() &gt; 0\" style=\"color: {{ ngIdealColor }}\"><span ng-bind-html=\"ngIdealMap[rl.indicatorName] | numberToI18NOrLess\"></span></td></tr></tbody><tfoot><tr><td style=\"text-align: right\"><span ng-bind-html=\"'RESULTS_TOTAL' | translate\"></span></td><td class=\"align-right scope1\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"getLeftTotal() | numberToI18NOrLess\"></span></td><td class=\"align-right scope1\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><span ng-bind-html=\"getRightTotal() | numberToI18NOrLess\"></span></td><td class=\"align-right scope1\" ng-show=\"getTypicalResultTotal() &gt; 0\" style=\"color: {{ ngTypeColor }}\"><span>{{ getTypicalResultTotal() | numberToI18NOrLess }}</span></td><td class=\"align-right scope1\" ng-show=\"getIdealResultTotal() &gt; 0\" style=\"color: {{ ngIdealColor }}\"><span>{{ getIdealResultTotal() | numberToI18NOrLess }}</span></td></tr><tr><td></td><td class=\"align-right scope1\" style=\"color: {{ ngModel.leftColor }}\"><span class=\"period-footer\" style=\"color: {{ ngModel.leftColor }}; border-top-color: {{ ngModel.leftColor }}\"></span></td><td class=\"align-right scope1\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><span class=\"period-footer\" style=\"color: {{ ngModel.rightColor }}; border-top-color: {{ ngModel.rightColor }}\"></span></td><td class=\"align-right scope1\" ng-show=\"getTypicalResultTotal() &gt; 0\" style=\"color: {{ ngTypeColor }}\"><span class=\"period-footer\" style=\"color: {{ ngTypeColor }}; border-top-color: {{ ngTypeColor }}\"></span></td><td class=\"align-right scope1\" ng-show=\"getIdealResultTotal() &gt; 0\" style=\"color: {{ ngIdealColor }}\"><span class=\"period-footer\" style=\"color: {{ ngIdealColor }}; border-top-color: {{ ngIdealColor }}\"></span></td></tr></tfoot></table><br></ng-virtual>");$templateCache.put('$/angular/templates/mm-button.html', "<button class=\"button\" ng-click=\"ngClick\" ng-class=\"ngClass\" type=\"button\"><span class=\"fa fa-{{ getIcon() }}\"></span><span>&nbsp;</span><span ng-bind-html=\"getCode() | translate\"></span></button>");$templateCache.put('$/angular/templates/mm-field-date.html', "<div class=\"field_row\" ng-hide=\"getInfo().hidden === true\"><div class=\"field_cell\" ng-click=\"logField()\" ng-bind-html=\"getInfo().fieldTitle | translate\"></div><div class=\"field_cell\"><div class=\"dropdown\"></div><a class=\"dropdown-toggle\" data-target=\"#\" data-toggle=\"dropdown\" role=\"button\" id=\"{{id}}\" href=\"\"><div class=\"input-group\"><input class=\"form-control\" ng-model=\"resultFormated\" type=\"text\"><span class=\"input-group-addon\"><i class=\"glyphicon glyphicon-calendar\"></i></span></div><ul class=\"dropdown-menu date_input\" role=\"menu\" aria-labelledby=\"dLabel\"><datetimepicker data-datetimepicker-config=\"{ dropdownSelector: '{{idHtag}}',minView:'day' }\" data-ng-model=\"result\"></datetimepicker></ul></a><div ng-transclude></div></div><div class=\"field_cell\"><img ng-show=\"getInfo().isValid\" src=\"/assets/images/field_valid.png\" ng-if=\"!getInfo().hideIsValidIcon\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/assets/images/field_invalid.png\"><div>{{getInfo().validationMessage | translateText}}</div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-event-menu.html', "<div class=\"nav_tabs\" ng-show=\"displayMenu===true\"><div class=\"nav_entreprise\"><div class=\"site_menu\"><div class=\"menu menu_enterprise\"><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_EV1') == true}\" ng-click=\"navTo('/form/TAB_EV1')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_EV1' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_EV1')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_EV2')  == true}\" ng-click=\"navTo('/form/TAB_EV2')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_EV2' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_EV2')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_EV3') == true}\" ng-click=\"navTo('/form/TAB_EV3')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_EV3' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_EV3')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_EV4') == true}\" ng-click=\"navTo('/form/TAB_EV4')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_EV4' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_EV4')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_EV5') == true}\" ng-click=\"navTo('/form/TAB_EV5')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_EV5' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_EV5')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_EV6') == true}\" ng-click=\"navTo('/form/TAB_EV6')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_EV6' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_EV6')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button></div></div><div class=\"last_menu\"><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/results') == true}\" ng-click=\"navTo('/results')\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_RESULT' | translate\"></div></button><button class=\"button\" ng-show=\"$root.instanceName != 'verification'\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/actions') == true}\" ng-click=\"isDisabled || navTo('/actions')\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_REDUCTION' | translate\"></div></button></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-littleemitter-menu.html', "<div class=\"nav_tabs\" ng-show=\"displayMenu===true\"><div class=\"nav_entreprise\"><div class=\"site_menu\"><div class=\"menu menu_enterprise\"><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_P1') == true}\" ng-click=\"navTo('/form/TAB_P1')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_P1' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_P1')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_P2')  == true}\" ng-click=\"navTo('/form/TAB_P2')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_P2' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_P2')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_P3') == true}\" ng-click=\"navTo('/form/TAB_P3')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_P3' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_P3')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_P4') == true}\" ng-click=\"navTo('/form/TAB_P4')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_P4' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_P4')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_P5') == true}\" ng-click=\"navTo('/form/TAB_P5')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_P5' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_P5')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_P6') == true}\" ng-click=\"navTo('/form/TAB_P6')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_P6' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_P6')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button></div></div><div class=\"last_menu\"><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/results') == true}\" ng-click=\"navTo('/results')\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_RESULT' | translate\"></div></button><button class=\"button\" ng-show=\"$root.instanceName != 'verification'\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/actions') == true}\" ng-click=\"isDisabled || navTo('/actions')\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_REDUCTION' | translate\"></div></button></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-household-menu.html', "<div class=\"nav_tabs\" ng-show=\"displayMenu===true\"><div class=\"nav_entreprise\"><div class=\"site_menu\"><div class=\"menu menu_household\"><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_M1') == true}\" ng-click=\"navTo('/form/TAB_M1')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_M1' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_M1')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div><img class=\"tablogo\" src=\"/assets/images/household/menage.png\" ng-hide=\"isMenuCurrentlySelected('/form/TAB_M1') == true\"><img class=\"tablogo\" ng-show=\"isMenuCurrentlySelected('/form/TAB_M1') == true\" src=\"/assets/images/household/menage_on.png\"></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_M2') == true}\" ng-click=\"navTo('/form/TAB_M2')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_M2' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_M2')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div><img class=\"tablogo\" src=\"/assets/images/household/logement.png\" ng-hide=\"isMenuCurrentlySelected('/form/TAB_M2') == true\"><img class=\"tablogo\" ng-show=\"isMenuCurrentlySelected('/form/TAB_M2') == true\" src=\"/assets/images/household/logement_on.png\"></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_M3')  == true}\" ng-click=\"navTo('/form/TAB_M3')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_M3' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_M3')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div><img class=\"tablogo\" src=\"/assets/images/household/mobilite.png\" ng-hide=\"isMenuCurrentlySelected('/form/TAB_M3') == true\"><img class=\"tablogo\" ng-show=\"isMenuCurrentlySelected('/form/TAB_M3') == true\" src=\"/assets/images/household/mobilite_on.png\"></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_M4') == true}\" ng-click=\"navTo('/form/TAB_M4')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_M4' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_M4')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div><img class=\"tablogo\" src=\"/assets/images/household/dechets.png\" ng-hide=\"isMenuCurrentlySelected('/form/TAB_M4') == true\"><img class=\"tablogo\" ng-show=\"isMenuCurrentlySelected('/form/TAB_M4') == true\" src=\"/assets/images/household/dechets_on.png\"></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_M5') == true}\" ng-click=\"navTo('/form/TAB_M5')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_M5' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_M5')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div><img class=\"tablogo\" src=\"/assets/images/household/consommation.png\" ng-hide=\"isMenuCurrentlySelected('/form/TAB_M5') == true\"><img class=\"tablogo\" ng-show=\"isMenuCurrentlySelected('/form/TAB_M5') == true\" src=\"/assets/images/household/consommation_on.png\"></button></div></div><div class=\"last_menu\"><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/results') == true}\" ng-click=\"navTo('/results')\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_RESULT' | translate\"></div></button><button class=\"button\" ng-show=\"$root.instanceName != 'verification'\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/actions') == true}\" ng-click=\"isDisabled || navTo('/actions')\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_REDUCTION' | translate\"></div></button></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-enterprise-menu.html', "<div class=\"nav_tabs\" ng-show=\"displayMenu===true\"><div class=\"nav_entreprise\"><div class=\"site_menu\"><div class=\"menu menu_enterprise\"><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB2') == true}\" ng-click=\"navTo('/form/TAB2')\"><div class=\"tab-title\" ng-bind-html=\"'TAB2' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB2')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB3')  == true}\" ng-click=\"navTo('/form/TAB3')\"><div class=\"tab-title\" ng-bind-html=\"'TAB3' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB3')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB4') == true}\" ng-click=\"navTo('/form/TAB4')\"><div class=\"tab-title\" ng-bind-html=\"'TAB4' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB4')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB5') == true}\" ng-click=\"navTo('/form/TAB5')\"><div class=\"tab-title\" ng-bind-html=\"'TAB5' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB5')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB6') == true}\" ng-click=\"navTo('/form/TAB6')\"><div class=\"tab-title\" ng-bind-html=\"'TAB6' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB6')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB7') == true}\" ng-click=\"navTo('/form/TAB7')\"><div class=\"tab-title\" ng-bind-html=\"'TAB7' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB7')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button></div></div><div class=\"last_menu\"><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/results') == true}\" ng-click=\"navTo('/results')\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_RESULT' | translate\"></div></button><button class=\"button\" ng-show=\"$root.instanceName != 'verification'\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/actions') == true}\" ng-click=\"isDisabled || navTo('/actions')\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_REDUCTION' | translate\"></div></button></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-municipality-menu.html', "<div class=\"nav_tabs\" ng-show=\"displayMenu===true\"><div class=\"nav_entreprise\"><div class=\"site_menu\"><div class=\"menu menu_municipality\"><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_C1') == true}\" ng-click=\"navTo('/form/TAB_C1')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C1' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C1')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_C2') == true}\" ng-click=\"navTo('/form/TAB_C2')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C2' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C2')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_C3')  == true}\" ng-click=\"navTo('/form/TAB_C3')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C3' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C3')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_C4') == true}\" ng-click=\"navTo('/form/TAB_C4')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C4' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C4')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/form/TAB_C5') == true}\" ng-click=\"navTo('/form/TAB_C5')\"><div class=\"tab-title\" ng-bind-html=\"'TAB_C5' | translate\"></div><mm-awac-tab-progress-bar ng-value=\"getProgress('TAB_C5')\"></mm-awac-tab-progress-bar><div class=\"menu_arrow\"></div></button></div></div><div class=\"last_menu\"><button class=\"button\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/results') == true}\" ng-click=\"navTo('/results')\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_RESULT' | translate\"></div></button><button class=\"button\" ng-show=\"$root.instanceName != 'verification'\" ng-class=\"{'menu_current': isMenuCurrentlySelected('/actions') == true}\" ng-click=\"isDisabled || navTo('/actions')\"><div class=\"tab-title\" ng-bind-html=\"'SURVEY_INTERFACE_REDUCTION' | translate\"></div></button></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-tab-progress-bar.html', "<div class=\"tab-pg-bar\"><div class=\"tab-pg-text\"><span ng-bind-html=\"'FILLED_BY' | translate\"></span><span>&nbsp;</span><span>{{ pg }}%</span></div><div class=\"tab-pg-background tab-pb-{{color}}-bg\"><div style=\"width: {{ pg }}%\" class=\"tab-pg-indicator tab-pb-{{color}}-fg\"></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-graph-donut.html', "<table><tr><td><canvas class=\"holder\" width=\"400\" height=\"200\"></canvas></td><td class=\"chart-legend\"><b ng-bind-html=\"'GRAPH_LEGEND' | translate\"></b><div ng-bind-html=\"legend\"></div></td></tr></table>");$templateCache.put('$/angular/templates/mm-awac-modal-create-or-edit-reduction-action-advice.html', "<div class=\"modal create_or_edit_action\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"(editMode ? 'EDIT_REDUCTION_ACTION_FORM_TITLE' : 'CREATE_REDUCTION_ACTION_FORM_TITLE') | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><!--Interface type (calculator) (select)--><div class=\"field_row\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"interfaceTypeKey.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><select ng-model=\"interfaceTypeKey.field\" ng-options=\"i.key as i.label for i in interfaceTypeOptions\"></select></div></div><!--Type (radio): 'GES Reduction' or 'Better Method'--><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"typeKey.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><div><input ng-model=\"typeKey.field\" name=\"{{typeKey.inputName}}\" type=\"radio\" value=\"1\"><span>&nbsp;</span><span ng-bind-html=\"'REDUCTION_ACTION_TYPE_REDUCING_GES' | translate\"></span><div class=\"field_info\"><div class=\"field_info_popup\" ng-bind-html=\"'REDUCTION_ACTION_TYPE_REDUCING_GES_DESC' | translate\"></div></div></div><div><input ng-model=\"typeKey.field\" name=\"{{typeKey.inputName}}\" type=\"radio\" value=\"2\"><span>&nbsp;</span><span ng-class=\"{selected: typeKey.field == '2'}\" ng-bind-html=\"'REDUCTION_ACTION_TYPE_BETTER_MEASURE' | translate\"></span><div class=\"field_info\"><div class=\"field_info_popup\" ng-bind-html=\"'REDUCTION_ACTION_TYPE_BETTER_MEASURE_DESC' | translate\"></div></div></div></div></div><!--Title (text)--><mm-awac-modal-field-text ng-info=\"title\" class=\"field_form_separator\"></mm-awac-modal-field-text><!--Physical measure (description) (text area)--><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"physicalMeasure.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><textarea ng-model=\"physicalMeasure.field\" ckeditor=\"editorOptions\"></textarea></div></div><!--Web site (text)--><mm-awac-modal-field-text ng-info=\"webSite\" class=\"field_form_separator\"></mm-awac-modal-field-text></div><!--Private informations--><div class=\"form-title\">Champs réservés à l'AWAC</div><div class=\"field_form\"><!--Reducing objectives (reduction action - base indicator associations)--><!---- Available base indicators--><div class=\"field_row field_form_separator\"><div class=\"field_cell\" style=\"vertical-align:bottom; padding-bottom:6px;\"><span ng-bind-html=\"'REDUCTION_ACTION_ADVICE_BASE_INDICATORS_FIELD_TITLE' | translate\"></span></div><div class=\"field_cell field_wide\"><div class=\"wrapper_table\"><div class=\"wrapper_row\"><div class=\"wrapper_cell\" style=\"width: 100%;vertical-align: top;\"><span>&nbsp;&nbsp;</span></div><div class=\"wrapper_cell\" style=\"white-space: nowrap; vertical-align: top; width: 70px;min-width: 70px;\"><span class=\"text\">&nbsp;MIN&nbsp;</span></div><div class=\"wrapper_cell\" style=\"white-space: nowrap; vertical-align: top; width: 70px;min-width: 70px;\"><span class=\"text\">&nbsp;MAX&nbsp;</span></div></div><div class=\"wrapper_row\"><div class=\"wrapper_cell\" style=\"width: 100%;vertical-align: top;\"><select ng-model=\"baseIndicatorAssociationToAdd.baseIndicatorKey\" ng-options=\"bi.key as bi.key + ' - ' + bi.label for bi in baseIndicatorOptions\" ng-disabled=\"typeKey.field == '2'\"></select></div><div class=\"wrapper_cell\" style=\"white-space: nowrap; vertical-align: top; width: 70px;min-width: 70px;\"><input ng-model=\"baseIndicatorAssociationToAdd.percent\" style=\"width: 50px;\" type=\"text\" ng-disabled=\"typeKey.field == '2'\"><span>&nbsp;%&nbsp;</span></div><div class=\"wrapper_cell\" style=\"white-space: nowrap; vertical-align: top; width: 70px;min-width: 70px;\"><input ng-model=\"baseIndicatorAssociationToAdd.percentMax\" style=\"width: 50px;\" type=\"text\" ng-disabled=\"typeKey.field == '2'\"><span>&nbsp;%&nbsp;</span></div><div class=\"wrapper_cell\" style=\"vertical-align: top;\"><button class=\"button\" ng-click=\"addBaseIndicatorAssociation()\" style=\"margin:0; padding:0; min-height:22px; padding-right:10px; padding-left:10px; border-radius:2px;\" type=\"button\" ng-disabled=\"!baseIndicatorAssociationToAdd.isValid()\"><span class=\"fa fa-plus-circle\"></span></button></div></div></div></div><div class=\"field_cell\" style=\"vertical-align:bottom; padding-bottom:2px;\"><div><div class=\"error_message\" ng-hide=\"(typeKey.field == &quot;2&quot;) || (baseIndicatorAssociations.length &gt; 0)\"><img src=\"/assets/images/field_invalid.png\"><div ng-bind-html=\"'REDUCTION_ACTION_ADVICE_NO_REDUCTION_OBJECTIVES_ERROR' | translate\"></div></div></div></div></div><!---- Defined associations--><div class=\"field_row\" ng-show=\"baseIndicatorAssociations.length &gt; 0\"><div class=\"field_cell\"><span>&nbsp</span></div><div class=\"field_cell field_wide\"><div class=\"wrapper_table\" style=\"margin-top:5px;margin-bottom: 10px;\"><div class=\"wrapper_row\" ng-repeat=\"bia in baseIndicatorAssociations\" style=\"font-weight:bold;\"><div class=\"wrapper_cell\" style=\"vertical-align:middle\"><button class=\"button\" ng-click=\"removeBaseIndicatorAssociation(bia.baseIndicatorKey)\" style=\"margin:0; padding:0; min-height:22px; padding-right:10px; padding-left:10px; border-radius:2px;\" type=\"button\"><span class=\"fa fa-remove fa-1g\"></span></button></div><div class=\"wrapper_cell\" style=\"vertical-align:middle; padding-left:10px; padding-right:10px; width:100%;\">{{ getBaseIndicatorLabel(bia.baseIndicatorKey) }}</div><div class=\"wrapper_cell\" style=\"vertical-align:middle; text-align: right;\">{{ (bia.percent| numberToI18N) + \"&nbsp;%&nbsp;&nbsp;-&nbsp;\" }}</div><div class=\"wrapper_cell\" style=\"vertical-align:middle; text-align: right;\">{{ (bia.percentMax| numberToI18N) + \"&nbsp;%\" }}</div></div></div></div></div><!--Comments (text area)--><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"comment.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><textarea ng-model=\"comment.field\" ckeditor=\"editorOptions\"></textarea></div></div><!--Responsible person--><mm-awac-modal-field-text ng-info=\"responsiblePerson\" class=\"field_form_separator\"></mm-awac-modal-field-text><!--Attachments--><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"'REDUCTION_ACTION_ATTACHMENTS_FIELD_TITLE' | translate\"></span></div><div class=\"field_cell field_wide\"><div class=\"document-question-progress-percentage\" ng-show=\"inDownload=== true &amp;&amp; percent != 100\">{{percent}} %</div><div ng-show=\"inDownload=== true &amp;&amp; percent == 100\" ng-bind-html=\"'REDUCTION_ACTION_UPLOAD_IN_TREATMENT' | translate\"></div><input ng-file-select=\"onFileSelect($files)\" type=\"file\"><table style=\"margin-top: 10px;\"><tr ng-repeat=\"f in files\"><td style=\"padding: 5px;\"><button class=\"button\" ng-click=\"download(f.id)\" type=\"button\"><span class=\"fa fa-download\"></span></button><button class=\"button\" ng-click=\"removeFile(f.id)\" type=\"button\"><span class=\"fa fa-remove\"></span></button><span>&nbsp;&nbsp;</span><b>{{ f.name }}</b></td></tr></table></div></div></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-edit-product.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-show=\"createNewProduct === true\" ng-bind-html=\"'EDIT_EVENT_CREATE_TITLE' | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4><h4 ng-bind-html=\"'EDIT_EVENT_EDIT_TITLE' | translate\" id=\"myModalLabel\" ng-hide=\"createNewProduct === true\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.name\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.description\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-confirm-closing.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"valid()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-show=\"$root.closedForms\" ng-bind-html=\"'UNCLOSING_FORM' | translate\" class=\"modal-title\"></h4><h4 ng-bind-html=\"'CLOSING_FORM' | translate\" class=\"modal-title\" ng-hide=\"$root.closedForms\"></h4></div><div class=\"modal-body\"><p ng-show=\"$root.closedForms\" ng-bind-html=\"'MODAL_CONFIRM_UNCLOSING_DESC'| translate\"></p><p ng-bind-html=\"'MODAL_CONFIRM_CLOSING_DESC'| translate\" ng-hide=\"$root.closedForms\"></p><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"valid();\" ng-bind-html=\"'SUBMIT' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-finalization.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'FINALIZE_VERIFICATION_BUTTON' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"desc\" ng-show=\"getParams().verificationSuccess == true\" ng-bind-html=\"'FINALIZATION_VALID_DESC' | translate\"></div><div class=\"desc\" ng-show=\"getParams().verificationSuccess != true\" ng-bind-html=\"'FINALIZATION_UNVALID_DESC' | translate\"></div><div><div class=\"field_form\"><div class=\"field_row\" ng-show=\"getParams().verificationSuccess == true\"><div ng-bind-html=\"'VERIFICATION_REPORT' | translate\"></div><div><div class=\"document-question-progress-percentage\" ng-show=\"inDownload=== true &amp;&amp; percent != 100\">{{percent}} %</div><div ng-show=\"inDownload=== true &amp;&amp; percent == 100\" ng-bind-html=\"'QUESTION_FILE_TREATEMENT' | translate\"></div><input ng-file-select=\"onFileSelect($files)\" type=\"file\"></div></div><mm-awac-modal-field-text ng-info=\"comment\"></mm-awac-modal-field-text></div></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-finalization-visualization.html', "<!--Modal--><!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'FINALIZE_VERIFICATION_BUTTON' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><textarea ng-model=\"comment\" name=\"comment\" focus-me=\"true\" class=\"question-comment-textarea\" ng-disabled=\"true\"></textarea></div></div><div class=\"modal-footer\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-edit-sub-list.html', "<div class=\"modal edit-sub-list\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span class=\"sr-only\">Fermer</span></button><h4 class=\"modal-title\">Edition de la liste \"{{ subList.codeList }}\"</h4></div><div class=\"modal-body\"><div class=\"field_form\"><div class=\"field_row\"><div class=\"field_cell field_wide\"><div class=\"sortable-list header\"><div>Position</div><div>{{ subList.referencedCodeList }}</div></div></div></div><div class=\"field_row\"><div class=\"field_cell field_wide\"><div class=\"sortable-list\" ui-sortable=\"sortableOptions\" ng-model=\"subList.items\"><div class=\"sortable-item\" ng-repeat=\"listItem in subList.items\"><div>[ {{ listItem.orderIndex }} ]&nbsp;&nbsp;</div><div><select ng-model=\"listItem.key\" ng-options=\"i.key as (i.key + ' - ' + i.label) for i in codeLabels\"></select></div></div></div></div></div></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" type=\"button\">Annuler</button><button class=\"button btn btn-primary\" ng-click=\"save();\" type=\"button\" ng-disabled=\"!updated\">Enregistrer</button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-confirmation-exit-form.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;<span</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_MESSAGE' | translate\"></div></div><div class=\"modal-footer\"><button class=\"button\" ng-click=\"continue();\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_CONTINUE' | translate\" type=\"button\"></button><button class=\"button\" ng-click=\"save()\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_SAVE' | translate\" type=\"button\" focus-me=\"true\"></button><button class=\"button\" ng-click=\"close()\" ng-bind-html=\"'MODAL_CONFIRMATION_EXIT_FORM_CANCEL' | translate\" type=\"button\"></button></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-email-change.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'EMAIL_CHANGE_FORM_TITLE' | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text><br><mm-awac-modal-field-text ng-info=\"oldEmailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newEmailInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-edit-event.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-show=\"createNewEvent === true\" ng-bind-html=\"'EDIT_EVENT_TITLE_CREATE' | translate\" class=\"modal-title\"></h4><h4 ng-bind-html=\"'EDIT_EVENT_TITLE_EDIT' | translate\" ng-hide=\"createNewEvent === true\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.name\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.description\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-create-reduction-action.html', "<div class=\"modal create_or_edit_action\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"(editMode ? 'EDIT_REDUCTION_ACTION_FORM_TITLE' : 'CREATE_REDUCTION_ACTION_FORM_TITLE') | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><!--Title (text)--><mm-awac-modal-field-text ng-info=\"title\"></mm-awac-modal-field-text><!--Type (radio): 'GES Reduction' or 'Better Method'--><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"typeKey.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><div><input ng-model=\"typeKey.field\" name=\"{{typeKey.inputName}}\" type=\"radio\" value=\"1\"><span>&nbsp;</span><span ng-bind-html=\"'REDUCTION_ACTION_TYPE_REDUCING_GES' | translate\"></span><div class=\"field_info\"><div class=\"field_info_popup\" ng-bind-html=\"'REDUCTION_ACTION_TYPE_REDUCING_GES_DESC' | translate\"></div></div></div><div><input ng-model=\"typeKey.field\" name=\"{{typeKey.inputName}}\" type=\"radio\" value=\"2\"><span>&nbsp;</span><span ng-class=\"{selected: typeKey.field == '2'}\" ng-bind-html=\"'REDUCTION_ACTION_TYPE_BETTER_MEASURE' | translate\"></span><div class=\"field_info\"><div class=\"field_info_popup\" ng-bind-html=\"'REDUCTION_ACTION_TYPE_BETTER_MEASURE_DESC' | translate\"></div></div></div></div></div><!--Status (radio): 'running', 'done' or 'canceled' (only in edit mode)--><div class=\"field_row field_form_separator\" ng-show=\"editMode\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"statusKey.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><div ng-repeat=\"statusOption in statusOptions\"><input ng-model=\"statusKey.field\" name=\"{{statusKey.inputName}}\" type=\"radio\" value=\"{{statusOption.key}}\"><span>&nbsp;</span><span ng-bind-html=\"statusOption.label\"></span></div></div></div><!--Scope Type (radio): 'organization' or 'site'--><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"scopeTypeKey.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><div><input ng-model=\"scopeTypeKey.field\" name=\"{{scopeTypeKey.inputName}}\" type=\"radio\" value=\"1\"><span>&nbsp;</span><span ng-bind-html=\"'REDUCTION_ACTION_SCOPE_TYPE_ORG' | translate\"></span><span>&nbsp;</span><span ng-bind-html=\" ': &lt;b&gt;' + $root.organizationName + '&lt;/b&gt;' \"></span></div><div ng-show=\"$root.instanceName == 'enterprise'\"><input ng-model=\"scopeTypeKey.field\" name=\"{{scopeTypeKey.inputName}}\" type=\"radio\" value=\"2\"><span>&nbsp;</span><span ng-bind-html=\"'REDUCTION_ACTION_SCOPE_TYPE_SITE' | translate\"></span><span>&nbsp;</span><select ng-model=\"scopeId.field\" ng-options=\"s.id as s.name for s in $root.mySites\" ng-disabled=\"scopeTypeKey.field == '1'\"></select></div></div></div><!--Physical measure (text) (action description: ghg reduction objective, better reporting method description...)--><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"physicalMeasure.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><textarea ng-model=\"physicalMeasure.field\" ckeditor=\"editorOptions\"></textarea></div></div><!--GHG Benefit mi, & max (double with unit) (disabled if Type is not 'GES Reduction')--><mm-awac-modal-field-text ng-info=\"ghgBenefit\" ng-disabled=\"typeKey.field == '2'\" class=\"field_form_separator\"><select ng-model=\"$parent.ghgBenefitUnitKey\" ng-options=\"u.code as u.name for u in $parent.gwpUnits\" ng-disabled=\"$parent.typeKey.field == '2'\"></select></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"ghgBenefitMax\" ng-disabled=\"typeKey.field == '2'\"><select ng-model=\"$parent.ghgBenefitMaxUnitKey\" ng-options=\"u.code as u.name for u in $parent.gwpUnits\" ng-disabled=\"$parent.typeKey.field == '2'\"></select></mm-awac-modal-field-text><!--Cost & Benefits--><mm-awac-modal-field-text ng-info=\"financialBenefit\" class=\"field_form_separator\"><div style=\"line-height: 22px;\">euros</div></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"investmentCost\"><div style=\"line-height: 22px;\">euros</div></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"expectedPaybackTime\"></mm-awac-modal-field-text><!--Due date--><mm-field-date ng-info=\"dueDate\" class=\"field_form_separator\"></mm-field-date><!--Other infos--><mm-awac-modal-field-text ng-info=\"webSite\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"responsiblePerson\"></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"comment.fieldTitle | translate\"></span></div><div class=\"field_cell field_wide\"><textarea ng-model=\"comment.field\" ckeditor=\"editorOptions\"></textarea></div></div><div class=\"field_row\"><div class=\"field_cell\" ng-click=\"logField()\"><span ng-bind-html=\"'REDUCTION_ACTION_ATTACHMENTS_FIELD_TITLE' | translate\"></span></div><div class=\"field_cell field_wide\"><div class=\"document-question-progress-percentage\" ng-show=\"inDownload=== true &amp;&amp; percent != 100\">{{percent}} %</div><div ng-show=\"inDownload=== true &amp;&amp; percent == 100\" ng-bind-html=\"'REDUCTION_ACTION_UPLOAD_IN_TREATMENT' | translate\"></div><input ng-file-select=\"onFileSelect($files)\" type=\"file\"><table style=\"margin-top: 10px;\"><tr ng-repeat=\"f in files\"><td style=\"padding: 5px;\"><button class=\"button\" ng-click=\"download(f.id)\" type=\"button\"><span class=\"fa fa-download\"></span></button><button class=\"button\" ng-click=\"remove(f.id)\" type=\"button\"><span class=\"fa fa-remove\"></span></button><span>&nbsp;&nbsp;</span><b>{{ f.name }}</b></td></tr></table></div></div></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-add-user-site.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'SITE_MANAGER_ADD_USER_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><table class=\"associate-user-table\"><thead><tr><td ng-bind-html=\"'SITE_MANAGER_ADD_NAME_LABEL' | translate\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_LOGIN_LABEL' | translate\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_SELECTED_LABEL' | translate\"></td></tr></thead><tbody><tr ng-repeat=\"account in accounts\"><td> {{account.person.firstName}} {{account.person.lastName}}</td><td> {{account.identifier}}</td><td><input ng-click=\"toggleSelection(account)\" name=\"{{account.identifier}}\" ng-checked=\"selection.indexOf(account) &gt; -1\" type=\"checkbox\" value=\"{{account.identifier}}\"></td></tr></tbody></table><table class=\"associate-user-table\"><thead><tr><td ng-bind-html=\"'SITE_MANAGER_ADD_LIST_LABEL' | translate\"></td></tr></thead><tbody><tr ng-repeat=\"account in selection\"><td>{{account.identifier}}</td></tr></tbody></table></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-agreement-disabled.html', "<!--Modal--><div class=\"modal-fullscreen modal-help\"><div class=\"modal-fullscreen-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button></div><div class=\"modal-fullscreen-body\"><iframe ng-src=\"{{trustSrc('http://www.w3schools.com')}}\" style=\"width:100%;height:100%\"></iframe></div><div class=\"modal-fullscreen-footer\"></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-confirm-dialog.html', "<div class=\"modal confirm-dialog\" ng-escape=\"close()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><h5 ng-bind-html=\"titleKey | translate\" class=\"modal-title\"></h5></div><div class=\"modal-body\"><div class=\"desc\" ng-bind-html=\"messageKey | translate\"></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"confirm();\" ng-bind-html=\"'YES_BUTTON' | translate\" type=\"button\"></button><span>&nbsp;</span><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'NO_BUTTON' | translate\" type=\"button\" autofocus=\"autofocus\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-edit-site.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-show=\"createNewSite === true\" ng-bind-html=\"'EDIT_SITE_TITLE_CREATE' | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4><h4 ng-bind-html=\"'EDIT_SITE_TITLE_EDIT' | translate\" id=\"myModalLabel\" ng-hide=\"createNewSite === true\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.name\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.description\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.nace\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.percentOwned\"></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\" ng-bind-html=\"'SITE_MANAGER_ORGANIZATIONAL_STRUCTURE' | translate\"></div><div class=\"field_cell\"><select ng-model=\"organizationStructure\" ng-options=\"p  as ( p | translateText ) for p  in organizationStructureList \"></select></div></div></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-document-manager.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'MODAL_DOCUMENT_MANAGER_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><table class=\"document-manager-table\"><thead><tr><td ng-bind-html=\"'MODAL_DOCUMENT_MANAGER_DOC_NAME' | translate\"></td><td ng-bind-html=\"'MODAL_DOCUMENT_MANAGER_ACTION' | translate\"></td></tr></thead><tbody><tr ng-repeat=\"(key,value) in listDocuments\"><td>{{value}}</td><td><button class=\"button\" ng-click=\"download(key)\" ng-bind-html=\"'MODAL_DOCUMENT_MANAGER_DOWNLOAD' | translate\" type=\"button\">)</button><button class=\"button\" ng-click=\"removeDoc(key)\" ng-bind-html=\"'MODAL_DOCUMENT_MANAGER_REMOVE' | translate\" type=\"button\" ng-hide=\"getParams().readyOnly === true\">)</button></td></tr></tbody></table></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-question-comment.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'MODAL_QUESTION_COMMENT_TITLE' | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><textarea ng-model=\"comment\" name=\"comment\" focus-me=\"true\" class=\"question-comment-textarea\" ng-disabled=\"getParams().canBeEdited === false\"></textarea></div></div><div class=\"modal-footer\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-hide=\"getParams().canBeEdited === false\"></button></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-help.html', "<!--Modal--><div class=\"modal-fullscreen modal-help\"><div class=\"modal-fullscreen-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button></div><div class=\"modal-fullscreen-body\"><div class=\"modal-help-content\"></div></div><div class=\"modal-fullscreen-footer\"></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-confirmation.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save(true)\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_DESC' | translateWithVars: [$root.verificationRequest.organizationVerifier.name]\"></div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.myPassword\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save(true)\" ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_ACCEPT' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button><button class=\"button btn btn-primary\" ng-click=\"save(false)\" ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_REJECT' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-invite-user.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'USER_INVITATION_FORM_TITLE' | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"inviteEmailInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-reject.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'MODAL_QUESTION_COMMENT_TITLE' | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><textarea ng-model=\"comment\" name=\"comment\" focus-me=\"true\" class=\"question-comment-textarea\" ng-disabled=\"getParams().readOnly === true\"></textarea></div></div><div class=\"modal-footer\"><div ng-hide=\"getParams().readOnly === true\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-hide=\"getParams().canBeEdited === false\"></button></div><div ng-show=\"getParams().readOnly === true\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CLOSE_BUTTON' | translate\" type=\"button\"></button></div></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-request-verification.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'ASK_VERIFICATION' | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.email\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.comment\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.myPhoneNumber\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.myPassword\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CLOSE_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-document.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save(true)\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'VALID_REQUEST' | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"desc\" ng-bind-html=\"'VALID_REQUEST_DESC' | translate\"></div><div class=\"field_form\"><div class=\"field_row\"><div class=\"field_cell\"><div></div></div><div class=\"field_cell\"><button class=\"button\" ng-click=\"download()\" ng-bind-html=\"'DOWNLOAD_VERIFICATION_REPORT' | translate\" type=\"button\"></button></div></div><mm-awac-modal-field-text ng-info=\"fields.myPassword\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CLOSE_BUTTON' | translate\"></button><button class=\"button btn btn-primary\" ng-click=\"save(true)\" ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_ACCEPT' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button><button class=\"button btn btn-primary\" ng-click=\"save(false)\" ng-bind-html=\"'VERIFICATION_CUSTOMER_CONFIRMATION_WINDOW_REJECT' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-consult-event.html', "<!--Modal--><div class=\"modal consult-event\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'ORGANIZATION_EVENT' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><table class=\"admin-table-import-bad table\" ng-show=\"events.length &gt; 0 \" ng-table=\"tableParams\"><tr ng-repeat=\"event in $data\"><td data-title=\"'EVENT' | translateText\" sortable=\"'name'\">{{event.name}}</td><td data-title=\"'SITE_MANAGER_EVENT_PERIOD'| translateText\" sortable=\"'period.label'\">{{event.period.label}}</td><td data-title=\"'SITE_MANAGER_EVENT_DESCRIPTION'| translateText\">{{event.description}}</td></tr></table></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CLOSE_BUTTON' | translate\" type=\"button\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-password-change.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'PASSWORD_CHANGE_FORM_TITLE' | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"oldPasswordInfo\"></mm-awac-modal-field-text><br><mm-awac-modal-field-text ng-info=\"newPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordConfirmInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-send-email.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 class=\"modal-title\">Envoyer un courriel</h4></div><div class=\"modal-body\"><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.title\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.content\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-connection-password-change.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'PASSWORD_CHANGE_FORM_TITLE' | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div ng-bind-html=\"'CONNECTION_PASSWORD_CHANGE_FORM_DESC' | translate\"></div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"newPasswordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"newPasswordConfirmInfo\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-manager.html', "<div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-field-text.html', "<div class=\"field_row\" ng-hide=\"getInfo().hidden === true\"><div class=\"field_cell\" ng-click=\"logField()\"><div class=\"question_info\" ng-show=\"getInfo().description != null\"><div class=\"question_info_popup\" ng-bind-html=\"getInfo().description | translate\"></div></div><span ng-bind-html=\"getInfo().fieldTitle | translate\"></span></div><div class=\"field_cell\"><div class=\"wrapper_table\"><div class=\"wrapper_row\"><div class=\"wrapper_cell wide\"><div class=\"error_message_numbers_only\" ng-show=\"errorMessage.length&gt;0\"><div>{{errorMessage}}</div><img src=\"/assets/images/question_field_error_message_icon_arrow.png\"></div><input ng-attr-numbers-only=\"{{getInfo().numbersOnly}}\" ng-model=\"getInfo().field\" ng-class=\"{input_number: getInfo().numbersOnly === 'integer' || getInfo().numbersOnly === 'double'}\" name=\"{{getInfo().inputName}}\" placeholder=\"{{getInfo().placeholder | translateText}}\" type=\"{{fieldType}}\" focus-me=\"getInfo().focus()\" ng-hide=\"fieldType === 'textarea'\" ng-disabled=\"getInfo().disabled\"><textarea ng-show=\"fieldType === 'textarea'\" ng-attr-numbers-only=\"{{getInfo().numbersOnly}}\" ng-model=\"getInfo().field\" ng-class=\"{input_number: getInfo().numbersOnly === 'integer' || getInfo().numbersOnly === 'double'}\" name=\"{{getInfo().inputName}}\" placeholder=\"{{getInfo().placeholder | translateText}}\" type=\"{{fieldType}}\" focus-me=\"getInfo().focus()\" ng-disabled=\"getInfo().disabled\"></textarea></div><div class=\"wrapper_cell\" ng-transclude></div></div></div></div><div class=\"field_cell\"><div ng-if=\"isValidationDefined\"><img ng-show=\"getInfo().isValid\" src=\"/assets/images/field_valid.png\" ng-if=\"!hideIsValidIcon\"><div class=\"error_message\" ng-hide=\"getInfo().isValid\"><img src=\"/assets/images/field_invalid.png\"><div ng-bind-html=\"getInfo().validationMessage | translate\"></div></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-add-user-product.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'SITE_MANAGER_ADD_USER_TITLE' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><table class=\"associate-user-table\"><thead><tr><td ng-bind-html=\"'SITE_MANAGER_ADD_NAME_LABEL' | translate\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_LOGIN_LABEL' | translate\"></td><td ng-bind-html=\"'SITE_MANAGER_ADD_SELECTED_LABEL' | translate\"></td></tr></thead><tbody><tr ng-repeat=\"account in accounts\"><td> {{account.person.firstName}} {{account.person.lastName}}</td><td> {{account.identifier}}</td><td><input ng-click=\"toggleSelection(account)\" name=\"{{account.identifier}}\" ng-checked=\"selection.indexOf(account) &gt; -1\" type=\"checkbox\" value=\"{{account.identifier}}\"></td></tr></tbody></table><table class=\"associate-user-table\"><thead><tr><td ng-bind-html=\"'SITE_MANAGER_ADD_LIST_LABEL' | translate\"></td></tr></thead><tbody><tr ng-repeat=\"account in selection\"><td>{{account.identifier}}</td></tr></tbody></table></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-loading.html', "<!--Modal--><div class=\"modal\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\" style=\"text-align:center\"><h4 ng-bind-html=\"'LOADING' | translate\"></h4></div><div class=\"modal-body\" style=\"text-align:center\"><img src=\"/assets/images/loading_preorganization.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-password-confirmation.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"getParams().title | translate\" id=\"myModalLabel\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><div class=\"desc\" ng-show=\"getParams().desc != null\" ng-bind-html=\"getParams().desc | translate\"></div><div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.myPassword\"></mm-awac-modal-field-text></div></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CLOSE_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-modal-verification-assign.html', "<!--Modal--><div class=\"modal\" ng-escape=\"close()\" ng-enter=\"save()\"><div class=\"modal-dialog\"><div class=\"modal-content\"><div class=\"modal-header\"><button class=\"button\" ng-click=\"close()\" type=\"button\"><span aria-hidden=\"true\">&times;</span><span ng-bind-html=\"'CLOSE_BUTTON' | translate\" class=\"sr-only\"></span></button><h4 ng-bind-html=\"'VERIFICATION_REQUEST_ASSIGN' | translate\" class=\"modal-title\"></h4></div><div class=\"modal-body\"><table class=\"associate-user-table\"><thead><tr><td><div ng-bind-html=\"'SITE_MANAGER_ADD_SELECTED_LABEL' | translate\"></div></td><td><div ng-bind-html=\"'NAME' | translate\"></div></td></tr></thead><tbody><tr ng-repeat=\"user in users\"><td><input ng-model=\"user.selected\" ng-value=\"user.identifier\" name=\"a\" type=\"checkbox\"></td><td>{{user.firstName}} {{user.lastName}}</td></tr></tbody></table></div><div class=\"modal-footer\"><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"close();\" ng-bind-html=\"'CANCEL_BUTTON' | translate\" type=\"button\"></button><button class=\"button btn btn-primary\" ng-click=\"save();\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-verification-survey.html', "<div><div ng-hide=\"$root.hideHeader()\"><div class=\"survey-header\"><!--user block--><table class=\"survey-header-option\"><tr><td rowspan=\"2\"><div class=\"wallonie_logo\"></div></td><td rowspan=\"2\"><a class=\"awac_logo\" href=\"http://www.awac.be\" target=\"_blank\"></a></td><td class=\"wide align-left\"><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_VERIFICATION' | translate\"></div></td><td><div><select ng-model=\"$root.language\" ng-options=\"l.value as l.label for l in $root.languages\"></select></div></td><td><div ng-bind-html=\"'SURVEY_INTERFACE_MANAGEMENT' | translate\"></div></td><td><div ng-show=\"$root.currentPerson!=null\"><span ng-bind-html=\"'WELCOME' | translate\"></span>,<span class=\"username\">{{$root.currentPerson.firstName}} {{$root.currentPerson.lastName}}</span></div><div ng-show=\"$root.currentPerson==null\">Your are currently not connected</div></td></tr><tr><td><div class=\"entreprise_name\">{{ $root.organizationName }}</div></td><td><button class=\"button help\" ng-click=\"$root.showHelp()\" ng-bind-html=\"'SURVEY_INTERFACE_ASSISTANCE' | translate\" type=\"button\"></button></td><td><!--organization manager button--><button class=\"button user_manage\" ng-click=\"isDisabled || $root.nav('/organization_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/organization_manager') == true}\" ng-bind-html=\"'ORGANIZATION_MANAGER_BUTTON' | translate\" type=\"button\" ng-disabled=\"$root.currentPerson.isAdmin === false\"></button><!--user manager button--><button class=\"button user_manage\" ng-click=\"isDisabled || $root.nav('/user_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_manager') == true}\" ng-bind-html=\"'USER_MANAGER_BUTTON' | translate\" type=\"button\" ng-disabled=\"$root.currentPerson.isAdmin === false\"></button></td><td><!--user data button--><button class=\"button user_manage\" ng-click=\"$root.nav('/user_data')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_data') == true}\" ng-bind-html=\"'USER_DATA_BUTTON' | translate\" type=\"button\"></button><!--logout button--><button class=\"button user_manage\" ng-show=\"$root.currentPerson!=null\" ng-click=\"$root.logout();\" ng-bind-html=\"'LOGOUT_BUTTON' | translate\" type=\"button\"></button></td></tr></table></div><div class=\"verification-menu-background\"><div class=\"verification-menu\"><button class=\"button\" ng-click=\"isDisabled || $root.nav('/manage')\" ng-class=\"{'selected': isMenuCurrentlySelected('/manage') == true}\" ng-bind-html=\"'VERIFICATION_SURVEY_MANAGE' | translate\" type=\"button\" ng-disabled=\"$root.currentPerson.isAdmin !== true\"></button><button class=\"button\" ng-click=\"isDisabled || $root.nav('/verification')\" ng-class=\"{'selected': isMenuCurrentlySelected('/verification') == true}\" ng-bind-html=\"'VERIFICATION_SURVEY_VERIF' | translate\" type=\"button\"></button><button class=\"button\" ng-click=\"isDisabled || $root.nav('/submit')\" ng-class=\"{'selected': isMenuCurrentlySelected('/submit') == true}\" ng-bind-html=\"'VERIFICATION_SURVEY_SUBMIT' | translate\" type=\"button\" ng-disabled=\"$root.currentPerson.isAdmin !== true &amp;&amp; $root.currentPerson.isMainVerifier !== true\"></button><button class=\"button\" ng-click=\"isDisabled || $root.nav('/archive')\" ng-class=\"{'selected': isMenuCurrentlySelected('/archive') == true}\" ng-bind-html=\"'VERIFICATION_SURVEY_ARCHIVE' | translate\" type=\"button\"></button></div></div></div><div ng-view class=\"{{getClassContent()}}\"></div><div class=\"footer\"></div></div>");$templateCache.put('$/angular/templates/mm-awac-admin-survey.html', "<div><div ng-hide=\"$root.hideHeader()\"><div class=\"survey-header\"><!--user block--><table class=\"survey-header-option\"><tr><td rowspan=\"2\"><div class=\"wallonie_logo\"></div></td><td rowspan=\"2\"><div class=\"awac_logo\"></div></td><td class=\"wide align-left\"><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_ADMIN' | translate\"></div></td><td class=\"survey-interface-management\"><div><select ng-model=\"$root.language\" ng-options=\"l.value as l.label for l in $root.languages\"></select></div></td><td class=\"survey-interface-management\"><div ng-bind-html=\"'SURVEY_INTERFACE_MANAGEMENT' | translate\"></div></td><td class=\"survey-interface-management\"><div ng-show=\"$root.currentPerson!=null\"><span ng-bind-html=\"'WELCOME' | translate\"></span>,<span class=\"username\">{{$root.currentPerson.firstName}} {{$root.currentPerson.lastName}}</span></div><div ng-show=\"$root.currentPerson==null\">Your are currently not connected</div></td></tr><tr><td></td><td></td><td><!--user manager button--><button class=\"button user_manage\" ng-click=\"isDisabled || $root.nav('/user_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_manager') == true}\" ng-bind-html=\"'USER_MANAGER_BUTTON' | translate\" type=\"button\" ng-disabled=\"$root.currentPerson.isAdmin === false\"></button></td><td><!--user data button--><button class=\"button user_manage\" ng-click=\"$root.nav('/user_data')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_data') == true}\" ng-bind-html=\"'USER_DATA_BUTTON' | translate\" type=\"button\"></button><!--logout button--><button class=\"button user_manage\" ng-show=\"$root.currentPerson!=null\" ng-click=\"$root.logout();\" ng-bind-html=\"'LOGOUT_BUTTON' | translate\" type=\"button\"></button></td></tr></table></div><div class=\"admin-menu-background\"><div class=\"admin-menu\"><button class=\"button\" ng-click=\"isDisabled || $root.nav('/driver')\" ng-class=\"{'selected': isMenuCurrentlySelected('/driver') == true}\" ng-bind-html=\"'ADMIN_SURVEY_DRIVER' | translate\" type=\"button\"></button></div><div class=\"admin-menu\"><button class=\"button\" ng-click=\"isDisabled || $root.nav('/advice')\" ng-class=\"{'selected': isMenuCurrentlySelected('/advice') == true}\" ng-bind-html=\"'REDUCING_ACTION_ADVICES_MENU' | translate\" type=\"button\"></button></div><div class=\"admin-menu\"><button class=\"button\" ng-click=\"isDisabled || $root.nav('/translation')\" ng-class=\"{'selected': isMenuCurrentlySelected('/translation') == true}\" type=\"button\">Traduction</button></div><div class=\"admin-menu\"><button class=\"button\" ng-click=\"isDisabled || $root.nav('/average')\" ng-class=\"{'selected': isMenuCurrentlySelected('/average') == true}\" type=\"button\">Statistiques</button></div><div class=\"admin-menu\"><button class=\"button\" ng-click=\"isDisabled || $root.nav('/organization_data')\" ng-class=\"{'selected': isMenuCurrentlySelected('/organization_data') == true}\" type=\"button\">Informations sur les calculateurs</button></div><div class=\"admin-menu\"><button class=\"button\" ng-click=\"isDisabled || $root.nav('/factors')\" ng-class=\"{'selected': isMenuCurrentlySelected('/factors') == true}\" ng-bind-html=\"'FACTORS' | translate\" type=\"button\"></button></div></div></div><div ng-view class=\"{{getClassContent()}}\"></div><div class=\"footer\"></div></div>");$templateCache.put('$/angular/templates/mm-awac-calculator-survey.html', "<div><div ng-hide=\"$root.hideHeader()\"><div class=\"survey-header\"><!--user block--><table class=\"survey-header-option\"><tr><td rowspan=\"2\"><div class=\"wallonie_logo\"></div></td><td rowspan=\"2\"><a class=\"awac_logo\" href=\"http://www.awac.be\" target=\"_blank\"></a></td><td class=\"wide align-left\"><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_ENTERPRISE' | translate\" ng-if=\"$root.instanceName == 'enterprise'\"></div><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_MUNICIPALITY' | translate\" ng-if=\"$root.instanceName == 'municipality'\"></div><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_HOUSEHOLD' | translate\" ng-if=\"$root.instanceName == 'household'\"></div><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_EVENT' | translate\" ng-if=\"$root.instanceName == 'event'\"></div><div class=\"calculateur_type\" ng-bind-html=\"'TITLE_LITTLE_EMITTER' | translate\" ng-if=\"$root.instanceName == 'littleemitter'\"></div></td><td><select class=\"language-chooser\" ng-model=\"$root.language\" ng-options=\"l.value as l.label for l in $root.languages\"></select></td><td><div class=\"survey-interface-management\" ng-bind-html=\"'SURVEY_INTERFACE_MANAGEMENT' | translate\"></div></td><td><div class=\"login-info\"><div class=\"connected\" ng-show=\"$root.currentPerson!=null\"><span ng-bind-html=\"'WELCOME' | translate\"></span>,<span class=\"username\">{{$root.currentPerson.firstName}} {{$root.currentPerson.lastName}}</span></div><div class=\"not-connected\" ng-show=\"$root.currentPerson==null\">Your are currently not connected</div></div></td></tr><tr><td><div class=\"entreprise_name\">{{ $root.organizationName }}</div></td><td><button class=\"button confidentiality\" ng-click=\"$root.showConfidentiality()\" ng-bind-html=\"'SURVEY_INTERFACE_CONFIDENTIALITY' | translate\" type=\"button\"></button><button class=\"button help\" ng-click=\"$root.showHelp()\" ng-bind-html=\"'SURVEY_INTERFACE_ASSISTANCE' | translate\" type=\"button\"></button></td><td><!--organization manager button => for all calculator--><button class=\"button user_manage\" ng-click=\"isDisabled || $root.nav('/organization_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/organization_manager') == true}\" ng-bind-html=\"'ORGANIZATION_MANAGER_BUTTON' | translate\" type=\"button\" ng-hide=\"$root.instanceName == 'household'\" ng-disabled=\"$root.currentPerson.isAdmin === false\"></button><button class=\"button user_manage\" ng-show=\"$root.instanceName == 'household'\" ng-click=\"isDisabled || $root.nav('/organization_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/organization_manager') == true}\" ng-bind-html=\"'HOUSEHOLD_MANAGER_BUTTON' | translate\" type=\"button\" ng-disabled=\"$root.currentPerson.isAdmin === false\"></button><!--site manager button => only for enterprise--><button class=\"button user_manage\" ng-show=\"$root.instanceName == 'enterprise'\" ng-click=\"isDisabled || $root.nav('/site_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/site_manager') == true}\" ng-bind-html=\"'SITE_MANAGER_BUTTON' | translate\" type=\"button\" ng-disabled=\"$root.currentPerson.isAdmin === false\"></button><!--product manager button => only for event--><button class=\"button user_manage\" ng-show=\"$root.instanceName == 'event'\" ng-click=\"isDisabled || $root.nav('/product_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/product_manager') == true}\" ng-bind-html=\"'EVENT_MANAGER_BUTTON' | translate\" type=\"button\" ng-disabled=\"$root.currentPerson.isAdmin === false\"></button><!--user manager button => not for littleemitter or household--><button class=\"button user_manage\" ng-click=\"isDisabled || $root.nav('/user_manager')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_manager') == true}\" ng-bind-html=\"'USER_MANAGER_BUTTON' | translate\" type=\"button\" ng-hide=\"$root.instanceName == 'household' || $root.instanceName == 'littleemitter'\" ng-disabled=\"$root.currentPerson.isAdmin === false\"></button></td><td><!--user data button--><button class=\"button user_manage\" ng-click=\"$root.nav('/user_data')\" ng-class=\"{'selected': isMenuCurrentlySelected('/user_data') == true}\" ng-bind-html=\"'USER_DATA_BUTTON' | translate\" type=\"button\"></button><!--logout button--><button class=\"button user_manage\" ng-show=\"$root.currentPerson!=null\" ng-click=\"$root.logout();\" ng-bind-html=\"'LOGOUT_BUTTON' | translate\" type=\"button\"></button></td></tr></table></div><div class=\"data_menu\" ng-show=\"displayLittleMenu===true || displayMenu===true\"><div class=\"data_date\"><div ng-bind-html=\"'PERIOD_DATA' | translate\"></div><select ng-model=\"$root.periodSelectedKey\" ng-options=\"p.key as p.label for p in $root.availablePeriods\"></select></div><!--select site => only for entreprise--><!--select site => only for event--><div class=\"big_separator\" ng-show=\"$root.instanceName == 'enterprise'\"></div><div class=\"data_date_compare\" ng-show=\"$root.instanceName == 'enterprise' || $root.instanceName == 'event'\"><div ng-show=\"$root.instanceName == 'enterprise'\" ng-bind-html=\"'SURVEY_INTERFACE_SELECTED_SITE' | translate\"></div><div ng-show=\"$root.instanceName == 'event'\" ng-bind-html=\"'SURVEY_INTERFACE_SELECTED_EVENT' | translate\"></div><select ng-model=\"$root.scopeSelectedId\" ng-options=\"s.id as s.name for s in $root.mySites\"></select></div><div class=\"big_separator\"></div><div class=\"data_date_compare\"><div ng-bind-html=\"'SURVEY_INTERFACE_COMPARE_TO' | translate\"></div><select ng-model=\"$root.periodToCompare\" ng-options=\"p.key as p.label for p in periodsForComparison\"></select></div><div class=\"big_separator\"></div><!--verification and closed => only for enterprise and municipality--><div ng-show=\"$root.currentPerson.isAdmin == true &amp;&amp; ($root.instanceName == 'municipality' || $root.instanceName == 'enterprise')\"><!--verification button => start verification--><button class=\"button verification\" ng-show=\"!$root.closedForms &amp;&amp; $root.getVerificationRequestStatus()==null\" ng-click=\"isDisabled || requestVerification()\" ng-bind-html=\"'ASK_VERIFICATION' | translate\" type=\"button\" ng-disabled=\"$root.closeableForms !== true\"></button><!--re-submit => is case of correction--><button class=\"button verification\" ng-show=\"$root.getVerificationRequestStatus()==='VERIFICATION_STATUS_CORRECTION'\" ng-click=\"isDisabled || resubmitVerification()\" ng-bind-html=\"'VERIFICATION_RE_SUBMIT_BUTTON' | translate\" type=\"button\" ng-disabled=\"$root.closeableForms !== true\"></button><!--consult verificaiont => is verified--><button class=\"button verification\" ng-show=\"$root.getVerificationRequestStatus()==='VERIFICATION_STATUS_WAIT_CUSTOMER_VERIFIED_CONFIRMATION'\" ng-click=\"isDisabled || consultVerification()\" ng-bind-html=\"'SURVEY_INTERFACE_VERIFICATION_CONSULT' | translate\" type=\"button\"></button><!--accept / reject validation--><button class=\"button verification\" ng-show=\"$root.getVerificationRequestStatus()==='VERIFICATION_STATUS_WAIT_CUSTOMER_CONFIRMATION'\" ng-click=\"isDisabled || confirmVerifier()\" ng-bind-html=\"'SURVEY_INTERFACE_VERIFICATION_CONFIRM_VERIFIER' | translate\" type=\"button\"></button><!--cancel verification => other case--><button class=\"button verification\" ng-show=\"$root.getVerificationRequestStatus()!=null &amp;&amp; $root.getVerificationRequestStatus()!='VERIFICATION_STATUS_WAIT_CUSTOMER_VERIFIED_CONFIRMATION' &amp;&amp; $root.getVerificationRequestStatus()!='VERIFICATION_STATUS_VERIFIED' &amp;&amp; $root.getVerificationRequestStatus()!='VERIFICATION_STATUS_CORRECTION' &amp;&amp; $root.getVerificationRequestStatus()!='VERIFICATION_STATUS_WAIT_CUSTOMER_CONFIRMATION'\" ng-click=\"isDisabled || cancelVerification()\" ng-bind-html=\"'VERIFICATION_CANCEL_BUTTON' | translate\" type=\"button\"></button><!--consult verification faild comment--><button class=\"button verification\" ng-show=\"$root.getVerificationRequestStatus()==='VERIFICATION_STATUS_CORRECTION' \" ng-click=\"isDisabled || consultVerificationFinalComment()\" ng-bind-html=\"'VERIFICATION_FINALIZATION_CONSULT_COMMENT' | translate\" type=\"button\"></button><button class=\"button verification\" ng-show=\"$root.closedForms\" ng-click=\"isDisabled || $root.closeForms()\" ng-bind-html=\"'UNCLOSING_FORM' | translate\" type=\"button\"></button><button class=\"button verification\" ng-show=\"!$root.closedForms &amp;&amp; $root.getVerificationRequestStatus()==null\" ng-click=\"isDisabled || $root.closeForms()\" ng-bind-html=\"'CLOSING_FORM' | translate\" type=\"button\" ng-disabled=\"$root.closeableForms !== true\"></button></div><div class=\"big_separator\"></div><div class=\"data_save\"><div class=\"last_save\" ng-hide=\"lastSaveTime===null\"><span ng-bind-html=\"'LAST_SAVE' | translate\"></span><br>{{lastSaveTime | date: 'medium' }}</div><div class=\"small_separator\"></div><div class=\"save_button\"><button class=\"button save\" ng-click=\"save()\" ng-bind-html=\"'SAVE_BUTTON' | translate\" type=\"button\"></button></div></div></div><div class=\"inject-menu\"></div></div><div ng-view class=\"{{getClassContent()}}\"></div><div class=\"footer\"></div></div>");$templateCache.put('$/angular/templates/mm-awac-form-navigator.html', "<table class=\"form-navigator\"><tr><td><button class=\"button previous\" ng-show=\"previousFormIdentifier != null\" ng-click=\"$root.nav('/form/' + previousFormIdentifier)\" type=\"button\"><span ng-bind-html=\"previousFormIdentifier | translate\"></span></button></td><td><button class=\"button save\" ng-click=\"save()\" type=\"button\"><span ng-bind-html=\"'SAVE_BUTTON' | translate\"></span></button></td><td><button class=\"button next\" ng-show=\"nextFormIdentifier != null\" ng-click=\"$root.nav('/form/' + nextFormIdentifier)\" type=\"button\"><span ng-bind-html=\"nextFormIdentifier | translate\"></span></button><button class=\"button next\" ng-show=\"nextFormIdentifier == null\" ng-click=\"$root.nav('/results')\" type=\"button\"><span ng-bind-html=\"'SURVEY_INTERFACE_RESULT' | translate\"></span></button></td></tr></table>");$templateCache.put('$/angular/templates/mm-plus-minus-toggle-button.html', "<button class=\"plus-minus\" ng-click=\"toggle()\"><div class=\"plus\" ng-hide=\"ngModel\"><span class=\"fa fa-plus\"></span></div><div class=\"minus\" ng-show=\"ngModel\"><span class=\"fa fa-minus\"></span></div></button>");$templateCache.put('$/angular/templates/mm-awac-result-table.html', "<ng-virtual><table class=\"indicators_table\"><thead><tr><th></th><th colspan=\"4\"><span class=\"period-header\" style=\"color: {{ ngModel.leftColor }}; border-bottom-color: {{ ngModel.leftColor }}\">{{ngModel.leftPeriod}}</span></th><th colspan=\"4\" ng-show=\"ngModel.rightPeriod!=null\"><span class=\"period-header\" style=\"color: {{ ngModel.rightColor }}; border-bottom-color: {{ ngModel.rightColor }}\">{{ngModel.rightPeriod}}</span></th></tr><tr><th></th><th class=\"align-right scope1\" style=\"color: {{ ngModel.leftColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_1' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right scope2\" style=\"color: {{ ngModel.leftColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_2' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right scope3\" style=\"color: {{ ngModel.leftColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_3' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right out-of-scope\" ng-show=\"$root.instanceName == 'enterprise' || $root.instanceName == 'municipality'\" style=\"color: {{ ngModel.leftColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'OUT_OF_SCOPE' | translate\"></span><span> (tCO2)</span></span></div></th><th class=\"align-right scope1\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_1' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right scope2\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_2' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right scope3\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'SCOPE_3' | translate\"></span><span> (tCO2e)</span></span></div></th><th class=\"align-right out-of-scope\" ng-show=\"$root.instanceName == 'enterprise' || $root.instanceName == 'municipality'\" style=\"color: {{ ngModel.rightColor }}\"><div><span class=\"wrapped\"><span ng-bind-html=\"'OUT_OF_SCOPE' | translate\"></span><span> (tCO2)</span></span></div></th></tr></thead><tbody><tr ng-show=\"showAll || (rl.leftScope1Value + rl.leftScope2Value + rl.leftScope3Value + rl.leftOutOfScopeValue + rl.rightScope1Value + rl.rightScope2Value + rl.rightScope3Value + rl.rightOutOfScopeValue &gt; 0)\" ng-repeat=\"rl in ngModel.reportLines | orderBy:'order'\"><td><span ng-bind-html=\"rl.indicatorName | translate\"></span></td><td class=\"align-right scope1\" style=\"color: {{ ngModel.leftColor }}\"><span ng-show=\"rl.leftScope1Value &gt; 0\" ng-bind-html=\"rl.leftScope1Value | numberToI18NOrLess\"></span></td><td class=\"align-right scope2\" style=\"color: {{ ngModel.leftColor }}\"><span ng-show=\"rl.leftScope2Value &gt; 0\" ng-bind-html=\"rl.leftScope2Value | numberToI18NOrLess\"></span></td><td class=\"align-right scope3\" style=\"color: {{ ngModel.leftColor }}\"><span ng-show=\"rl.leftScope3Value &gt; 0\" ng-bind-html=\"rl.leftScope3Value | numberToI18NOrLess\"></span></td><td class=\"align-right out-of-scope\" ng-show=\"$root.instanceName == 'enterprise' || $root.instanceName == 'municipality'\" style=\"color: {{ ngModel.leftColor }}\"><span ng-show=\"rl.leftOutOfScopeValue &gt; 0\" ng-bind-html=\"rl.leftOutOfScopeValue | numberToI18NOrLess\"></span></td><td class=\"align-right scope1\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><span ng-show=\"rl.rightScope1Value &gt; 0\" ng-bind-html=\"rl.rightScope1Value | numberToI18NOrLess\"></span></td><td class=\"align-right scope2\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><span ng-show=\"rl.rightScope2Value &gt; 0\" ng-bind-html=\"rl.rightScope2Value | numberToI18NOrLess\"></span></td><td class=\"align-right scope3\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><span ng-show=\"rl.rightScope3Value &gt; 0\" ng-bind-html=\"rl.rightScope3Value | numberToI18NOrLess\"></span></td><td class=\"align-right out-of-scope\" ng-show=\"$root.instanceName == 'enterprise' || $root.instanceName == 'municipality'\" style=\"color: {{ ngModel.rightColor }}\"><span ng-show=\"rl.rightOutOfScopeValue &gt; 0\" ng-bind-html=\"rl.rightOutOfScopeValue | numberToI18NOrLess\"></span></td></tr></tbody><tfoot><tr><td style=\"text-align: right\"><span ng-bind-html=\"'RESULTS_TOTAL' | translate\"></span></td><td class=\"align-right scope1\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"getLeftTotalScope1() | numberToI18NOrLess\"></span></td><td class=\"align-right scope2\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"getLeftTotalScope2() | numberToI18NOrLess\"></span></td><td class=\"align-right scope3\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"getLeftTotalScope3() | numberToI18NOrLess\"></span></td><td class=\"align-right out-of-scope\" ng-show=\"$root.instanceName == 'enterprise' || $root.instanceName == 'municipality'\" style=\"color: {{ ngModel.leftColor }}\"><span ng-bind-html=\"getLeftTotalOutOfScope() | numberToI18NOrLess\"></span></td><td class=\"align-right scope1\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><span ng-bind-html=\"getRightTotalScope1() | numberToI18NOrLess\"></span></td><td class=\"align-right scope2\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><span ng-bind-html=\"getRightTotalScope2() | numberToI18NOrLess\"></span></td><td class=\"align-right scope3\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><span ng-bind-html=\"getRightTotalScope3() | numberToI18NOrLess\"></span></td><td class=\"align-right out-of-scope\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><span ng-show=\"$root.instanceName == 'enterprise' || $root.instanceName == 'municipality'\" ng-bind-html=\"getRightTotalOutOfScope() | numberToI18NOrLess\"></span></td></tr><tr><td></td><td class=\"align-right scope1\" colspan=\"4\" style=\"color: {{ ngModel.leftColor }}\"><span class=\"period-footer\" style=\"color: {{ ngModel.leftColor }}; border-top-color: {{ ngModel.leftColor }}\"></span></td><td class=\"align-right scope1\" colspan=\"4\" ng-show=\"ngModel.rightPeriod!=null\" style=\"color: {{ ngModel.rightColor }}\"><span class=\"period-footer\" style=\"color: {{ ngModel.rightColor }}; border-top-color: {{ ngModel.rightColor }}\"></span></td></tr></tfoot></table><div><input ng-model=\"showAll\" type=\"checkbox\"><label><span ng-bind-html=\"'SHOW_ALL_INDICATORS' | translate\"></span></label></div></ng-virtual>");$templateCache.put('$/angular/templates/mm-awac-result-legend.html', "<div class=\"chart-legend\"><!--<span>{{ ngModel.reportLines }}</span>--><div ng-show=\"(ngModel.reportLines | filter:{color: '!!'}).length == 0\"><span ng-bind-html=\"'RESULTS_LEGENDS_NODATA' | translate\"></span></div><table ng-hide=\"(ngModel.reportLines | filter:{color: '!!'}).length == 0\"><thead ng-show=\"ngModel.rightPeriod != null\"><tr><th></th><th></th><th colspan=\"2\"><span class=\"period-header\" style=\"color: {{ {false: ngModel.leftColor, true: ''}[getMode()!='numbers'] }}; border-bottom-color: {{ {false: ngModel.leftColor, true: ''}[getMode()!='numbers'] }}\">{{ngModel.leftPeriod}}</span></th><th colspan=\"2\"><span class=\"period-header\" style=\"color: {{ {false: ngModel.rightColor, true: ''}[getMode()!='numbers'] }}; border-bottom-color: {{ {false: ngModel.rightColor, true: ''}[getMode()!='numbers'] }}\">{{ngModel.rightPeriod}}</span></th></tr></thead><tbody><tr ng-repeat=\"line in getReportLines()\"><td><span class=\"circled-number\" ng-show=\"getMode()=='numbers' &amp;&amp; getNumber(line) != null\">{{ getNumber(line) }}</span><span class=\"chart-legend-bullet-color\" style=\"background: {{ line.color }}\" ng-hide=\"getMode()=='numbers'\"></span></td><td><span ng-bind-html=\"line.indicatorName | translate\"></span></td><td class=\"align-right data-cell\" style=\"color: {{ {false: ngModel.leftColor, true: ''}[getMode()!='numbers'] }}\"><span>{{ (line.leftScope1Value + line.leftScope2Value + line.leftScope3Value + line.leftOutOfScopeValue) | numberToI18NRoundedOrFullIfLessThanOne }}&nbsp;tCO2e</span></td><td class=\"align-right data-cell opacity-50\" style=\"color: {{ {false: ngModel.leftColor, true: ''}[getMode()!='numbers'] }}\"><span>{{ line.leftPercentage | numberToI18N }}&nbsp;%&nbsp;</span></td><td class=\"align-right data-cell\" ng-show=\"ngModel.rightPeriod != null\" style=\"color: {{ {false: ngModel.rightColor, true: ''}[getMode()!='numbers'] }}\"><span>{{ (line.rightScope1Value + line.rightScope2Value + line.rightScope3Value + line.rightOutOfScopeValue) | numberToI18NRoundedOrFullIfLessThanOne }}&nbsp;tCO2e</span></td><td class=\"align-right data-cell opacity-50\" ng-show=\"ngModel.rightPeriod != null\" style=\"color: {{ {false: ngModel.rightColor, true: ''}[getMode()!='numbers'] }}\"><span>{{ line.rightPercentage | numberToI18N }}&nbsp;%&nbsp;</span></td></tr></tbody></table><br ng-show=\"getMode()=='numbers'\"><br ng-show=\"getMode()=='numbers'\"><table ng-show=\"getMode()=='numbers' &amp;&amp; !isSimple()\"><tr><td ng-bind-html=\"'SCOPE_1' | translate\"></td><td><span class=\"chart-legend-bullet-color\" style=\"background: {{ ngModel.leftScope1Color }}\"></span></td><td ng-show=\"ngModel.rightPeriod != null\"><span class=\"chart-legend-bullet-color\" style=\"background: {{ ngModel.rightScope1Color }}\"></span></td></tr><tr><td ng-bind-html=\"'SCOPE_2' | translate\"></td><td><span class=\"chart-legend-bullet-color\" style=\"background: {{ ngModel.leftScope2Color }}\"></span></td><td ng-show=\"ngModel.rightPeriod != null\"><span class=\"chart-legend-bullet-color\" style=\"background: {{ ngModel.rightScope2Color }}\"></span></td></tr><tr><td ng-bind-html=\"'SCOPE_3' | translate\"></td><td><span class=\"chart-legend-bullet-color\" style=\"background: {{ ngModel.leftScope3Color }}\"></span></td><td ng-show=\"ngModel.rightPeriod != null\"><span class=\"chart-legend-bullet-color\" style=\"background: {{ ngModel.rightScope3Color }}\"></span></td></tr></table></div>");$templateCache.put('$/angular/templates/mm-awac-result-legend-simple.html', "<div class=\"chart-legend\"><div ng-show=\"(ngModel.reportLines | filter:{color: '!!'}).length == 0\"><span ng-bind-html=\"'RESULTS_LEGENDS_NODATA' | translate\"></span></div><table><thead><tr><th></th><th ng-hide=\"(ngModel.reportLines | filter:{color: '!!'}).length == 0\"><span class=\"period-header\" style=\"color: {{ ngModel.leftColor }}; border-bottom-color: {{ ngModel.leftColor }}\">{{ngModel.leftPeriod}}</span></th><th ng-show=\"ngModel.rightPeriod != null\"><span class=\"period-header\" style=\"color: {{ ngModel.rightColor }}; border-bottom-color: {{ ngModel.rightColor }}\">{{ngModel.rightPeriod}}</span></th><th ng-show=\"isTypicalShown()\"><span class=\"period-header\" style=\"color: {{ ngTypeColor }}; border-bottom-color: {{ ngTypeColor }}\"><span ng-show=\"$root.instanceName == 'household'\" ng-bind-html=\"'TYPICAL_HOUSEHOLD_TITLE' | translate\"></span><span ng-show=\"$root.instanceName == 'event'\" ng-bind-html=\"'TYPICAL_EVENT_TITLE' | translate\"></span><span ng-show=\"$root.instanceName == 'littleemitter'\" ng-bind-html=\"'TYPICAL_LITTLEEMITTER_TITLE' | translate\"></span></span></th><th ng-show=\"isIdealShown()\"><span class=\"period-header\" style=\"color: {{ ngIdealColor }}; border-bottom-color: {{ ngIdealColor }}\"><span ng-show=\"$root.instanceName == 'household'\" ng-bind-html=\"'IDEAL_HOUSEHOLD_TITLE' | translate\"></span><span ng-show=\"$root.instanceName == 'event'\" ng-bind-html=\"'IDEAL_EVENT_TITLE' | translate\"></span><span ng-show=\"$root.instanceName == 'littleemitter'\" ng-bind-html=\"'IDEAL_LITTLEEMITTER_TITLE' | translate\"></span></span></th></tr></thead><tbody><tr ng-repeat=\"(indicator, value) in ngType\"><td><span class=\"circled-number\" ng-show=\"getNumber(indicator) != null\">{{ getNumber(indicator) }}</span><span ng-bind-html=\"indicator | translate\"></span></td><td class=\"align-right data-cell\" ng-hide=\"(ngModel.reportLines | filter:{color: '!!'}).length == 0\"><span>{{ getLeftTotal(indicator) | numberToI18NRoundedOrFullIfLessThanOne }}&nbsp;tCO2e</span></td><td class=\"align-right data-cell\" ng-show=\"ngModel.rightPeriod != null\"><span>{{ getRightTotal(indicator) | numberToI18NRoundedOrFullIfLessThanOne }}&nbsp;tCO2e</span></td><td class=\"align-right data-cell\" ng-show=\"isTypicalShown()\"><span>{{ ngType[indicator] | numberToI18NRoundedOrFullIfLessThanOne }}&nbsp;tCO2e</span></td><td class=\"align-right data-cell\" ng-show=\"isIdealShown()\"><span>{{ ngIdeal[indicator] | numberToI18NRoundedOrFullIfLessThanOne }}&nbsp;tCO2e</span></td></tr></tbody></table></div>");$templateCache.put('$/angular/templates/mm-awac-registration-household.html', "<div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.emailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordConfirmInfo\"></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\" ng-bind-html=\"'REGISTRATION_AGREEMENT_VALIDATION' | translateText\"></div><div class=\"field_cell\"><div><input ng-model=\"aggrement_validation\" style=\"width: 18px;height: 18px;\" type=\"checkbox\"><button class=\"button btn btn-primary\" ng-click=\"displayAgreement()\" ng-bind-html=\"'REGISTRATION_AGREEMENT_CONSULTATION' | translate\" type=\"button\"></button></div></div><div class=\"field_cell\"><img ng-show=\"aggrement_validation == true\" src=\"/assets/images/field_valid.png\"><div class=\"error_message\" ng-hide=\"aggrement_validation == true\"><img src=\"/assets/images/field_invalid.png\"><div ng-bind-html=\"'ACCEPT_AGREEMENT' | translateText\"></div></div></div></div><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-bind-html=\"'ORGANIZATION_STATISTICS_ALLOWED' | translateText\"></div><div class=\"field_cell\" style=\"text-align: left;\"><input ng-model=\"organizationStatisticsAllowed\" style=\"width: 18px;height: 18px;margin-top: 2px;\" type=\"checkbox\"></div></div><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"registration()\" ng-bind-html=\"'REGISTRATION_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div>");$templateCache.put('$/angular/templates/mm-awac-registration-municipality.html', "<div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"emailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordConfirmInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"municipalityNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\" ng-bind-html=\"'REGISTRATION_AGREEMENT_VALIDATION' | translateText\"></div><div class=\"field_cell\"><div><input ng-model=\"aggrement_validation\" style=\"width: 18px;height: 18px;\" type=\"checkbox\"><button class=\"button btn btn-primary\" ng-click=\"displayAgreement()\" ng-bind-html=\"'REGISTRATION_AGREEMENT_CONSULTATION' | translate\" type=\"button\"></button></div></div><div class=\"field_cell\"><img ng-show=\"aggrement_validation == true\" src=\"/assets/images/field_valid.png\"><div class=\"error_message\" ng-hide=\"aggrement_validation == true\"><img src=\"/assets/images/field_invalid.png\"><div ng-bind-html=\"'ACCEPT_AGREEMENT' | translateText\"></div></div></div></div><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-bind-html=\"'ORGANIZATION_STATISTICS_ALLOWED' | translateText\"></div><div class=\"field_cell\" style=\"text-align: left;\"><input ng-model=\"organizationStatisticsAllowed\" style=\"width: 18px;height: 18px;margin-top: 2px;\" type=\"checkbox\"></div></div><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"registration()\" ng-bind-html=\"'REGISTRATION_BUTTON' | translate\" type=\"button\" ng-disabled=\"!registrationFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div>");$templateCache.put('$/angular/templates/mm-awac-registration-littleemitter.html', "<div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.emailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordConfirmInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.organizationNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\" ng-bind-html=\"'REGISTRATION_AGREEMENT_VALIDATION' | translateText\"></div><div class=\"field_cell\"><div><input ng-model=\"aggrement_validation\" style=\"width: 18px;height: 18px;\" type=\"checkbox\"><button class=\"button btn btn-primary\" ng-click=\"displayAgreement()\" ng-bind-html=\"'REGISTRATION_AGREEMENT_CONSULTATION' | translate\" type=\"button\"></button></div></div><div class=\"field_cell\"><img ng-show=\"aggrement_validation == true\" src=\"/assets/images/field_valid.png\"><div class=\"error_message\" ng-hide=\"aggrement_validation == true\"><img src=\"/assets/images/field_invalid.png\"><div ng-bind-html=\"'ACCEPT_AGREEMENT' | translateText\"></div></div></div></div><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-bind-html=\"'ORGANIZATION_STATISTICS_ALLOWED' | translateText\"></div><div class=\"field_cell\" style=\"text-align: left;\"><input ng-model=\"organizationStatisticsAllowed\" style=\"width: 18px;height: 18px;margin-top: 2px;\" type=\"checkbox\"></div></div><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"registration()\" ng-bind-html=\"'REGISTRATION_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div>");$templateCache.put('$/angular/templates/mm-awac-registration-enterprise.html', "<div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"emailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"passwordConfirmInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"organizationNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"firstSiteNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\" ng-bind-html=\"'REGISTRATION_AGREEMENT_VALIDATION' | translateText\"></div><div class=\"field_cell\"><div><input ng-model=\"aggrement_validation\" style=\"width: 18px;height: 18px;\" type=\"checkbox\"><button class=\"button btn btn-primary\" ng-click=\"displayAgreement()\" ng-bind-html=\"'REGISTRATION_AGREEMENT_CONSULTATION' | translate\" type=\"button\"></button></div></div><div class=\"field_cell\"><img ng-show=\"aggrement_validation == true\" src=\"/assets/images/field_valid.png\"><div class=\"error_message\" ng-hide=\"aggrement_validation == true\"><img src=\"/assets/images/field_invalid.png\"><div ng-bind-html=\"'ACCEPT_AGREEMENT' | translateText\"></div></div></div></div><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-bind-html=\"'ORGANIZATION_STATISTICS_ALLOWED' | translateText\"></div><div class=\"field_cell\" style=\"text-align: left;\"><input ng-model=\"organizationStatisticsAllowed\" style=\"width: 18px;height: 18px;margin-top: 2px;\" type=\"checkbox\"></div></div><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"registration()\" ng-bind-html=\"'REGISTRATION_BUTTON' | translate\" type=\"button\" ng-disabled=\"!registrationFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div>");$templateCache.put('$/angular/templates/mm-awac-registration-event.html', "<div class=\"field_form\"><mm-awac-modal-field-text ng-info=\"fields.firstNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.lastNameInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.emailInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.identifierInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordInfo\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.passwordConfirmInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.organizationNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><mm-awac-modal-field-text ng-info=\"fields.firstProductNameInfo\" class=\"field_form_separator\"></mm-awac-modal-field-text><div class=\"field_row\"><div class=\"field_cell\" ng-bind-html=\"'REGISTRATION_AGREEMENT_VALIDATION' | translateText\"></div><div class=\"field_cell\"><div><input ng-model=\"aggrement_validation\" style=\"width: 18px;height: 18px;\" type=\"checkbox\"><button class=\"button btn btn-primary\" ng-click=\"displayAgreement()\" ng-bind-html=\"'REGISTRATION_AGREEMENT_CONSULTATION' | translate\" type=\"button\"></button></div></div><div class=\"field_cell\"><img ng-show=\"aggrement_validation == true\" src=\"/assets/images/field_valid.png\"><div class=\"error_message\" ng-hide=\"aggrement_validation == true\"><img src=\"/assets/images/field_invalid.png\"><div ng-bind-html=\"'ACCEPT_AGREEMENT' | translateText\"></div></div></div></div><div class=\"field_row field_form_separator\"><div class=\"field_cell\" ng-bind-html=\"'ORGANIZATION_STATISTICS_ALLOWED' | translateText\"></div><div class=\"field_cell\" style=\"text-align: left;\"><input ng-model=\"organizationStatisticsAllowed\" style=\"width: 18px;height: 18px;margin-top: 2px;\" type=\"checkbox\"></div></div><div ng-hide=\"isLoading\"><button class=\"button btn btn-primary\" ng-click=\"registration()\" ng-bind-html=\"'REGISTRATION_BUTTON' | translate\" type=\"button\" ng-disabled=\"!allFieldValid()\"></button></div><img ng-show=\"isLoading\" src=\"/assets/images/modal-loading.gif\"></div>");$templateCache.put('$/angular/templates/mm-admin-translations-navigation-bar.html', "<div class=\"admin-translation-nav\"><ul class=\"nav nav-pills navbar-inverse\"><li class=\"dropdown\" role=\"presentation\"><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\">Interface et Messages<span class=\"caret\"></span></a><ul class=\"dropdown-menu\" role=\"menu\"><li><a href=\"#/translation/interface\">Interface</a></li><li><a href=\"#/translation/error-messages\">Messages d'erreurs</a></li><li><a href=\"#/translation/emails\">E-Mails</a></li></ul></li><li><a href=\"#/translation/forms\">Questionnaires</a></li><li class=\"dropdown\" role=\"presentation\"><a class=\"dropdown-toggle\" data-toggle=\"dropdown\" role=\"button\" aria-expanded=\"false\">Listes de choix<span class=\"caret\"></span></a><ul class=\"dropdown-menu\" role=\"menu\"><li><a href=\"#/translation/base-lists\">Listes de base</a></li><li><a href=\"#/translation/linked-lists\">Listes liées</a></li><li><a href=\"#/translation/sub-lists\">Sous-listes</a></li></ul></li><li><a href=\"#/translation/helps\">Textes d'aide</a></li></ul></div>");$templateCache.put('$/angular/templates/mm-awac-question-set-tree.html', "<div class=\"question-set-tree\"><div class=\"question-set\"><div class=\"question-key\" ng-style=\"{'padding-left': (getPadding()-30) + 'px'}\">[QS] {{ ngModel.label.key}}</div><div class=\"question-labels\" ng-if=\"!!ngModel.label.labelEn\"><div class=\"question-label\"><textarea ng-model=\"ngModel.label.labelEn\" type=\"text\"></textarea></div><div class=\"question-label\"><textarea ng-model=\"ngModel.label.labelFr\" type=\"text\"></textarea></div><div class=\"question-label\"><textarea ng-model=\"ngModel.label.labelNl\" type=\"text\"></textarea></div></div><div class=\"question-labels\" ng-if=\"!ngModel.label.labelEn\"><span>[NO LABELS]</span></div></div><div class=\"question-set tip\" ng-if=\"!!ngModel.label.labelEn\"><div class=\"question-key\" ng-style=\"{'padding-left': (getPadding()-30) + 'px'}\">[QS] {{ ngModel.associatedTipLabel.key}}</div><div class=\"question-labels\"><div class=\"question-label\"><textarea ng-model=\"ngModel.associatedTipLabel.labelEn\" type=\"text\"></textarea></div><div class=\"question-label\"><textarea ng-model=\"ngModel.associatedTipLabel.labelFr\" type=\"text\"></textarea></div><div class=\"question-label\"><textarea ng-model=\"ngModel.associatedTipLabel.labelNl\" type=\"text\"></textarea></div></div></div><div class=\"question-set-questions\"><div class=\"question-set\" ng-repeat=\"question in ngModel.questions\"><div class=\"question-key\" ng-style=\"{'padding-left': ($parent.getPadding()) + 'px'}\">[Q] {{ question.label.key }}</div><div class=\"question-labels\" ng-if=\"!!question.label.labelEn\"><div class=\"question-label\"><textarea ng-model=\"question.label.labelEn\" type=\"text\"></textarea></div><div class=\"question-label\"><textarea ng-model=\"question.label.labelFr\" type=\"text\"></textarea></div><div class=\"question-label\"><textarea ng-model=\"question.label.labelNl\" type=\"text\"></textarea></div></div><div class=\"question-labels\" ng-if=\"!question.label.labelEn\"><span>[NO LABELS]</span></div><div class=\"question-set tip\" ng-if=\"!!question.label.labelEn\"><div class=\"question-key\" ng-style=\"{'padding-left': ($parent.getPadding()) + 'px'}\">[Q] {{ question.associatedTipLabel.key}}</div><div class=\"question-labels\"><div class=\"question-label\"><textarea ng-model=\"question.associatedTipLabel.labelEn\" type=\"text\"></textarea></div><div class=\"question-label\"><textarea ng-model=\"question.associatedTipLabel.labelFr\" type=\"text\"></textarea></div><div class=\"question-label\"><textarea ng-model=\"question.associatedTipLabel.labelNl\" type=\"text\"></textarea></div></div></div></div></div><div class=\"question-set-children\"><div class=\"question-set-child\" ng-repeat=\"child in ngModel.children\" style=\"padding-top:10px;\" ng-if=\"ngModel.children.length &gt; 0\"><mm-awac-question-set-tree ng-model=\"child\"></mm-awac-question-set-tree></div></div></div>");$templateCache.put('$/angular/templates/mm-awac-admin-bad-importer.html', "<div><button class=\"btn btn-default\" ng-click=\"import()\">Import BAD</button><tabset style=\"margin-top:20px\"><tab class=\"tab-color-lightgreen\"><tab-heading style=\"margin-left:25px\"><span>BAD (Erros :  {{total_bad_error}} )</span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><table class=\"table\" style=\"width: 200px;\"><tr><td>Total BAd imported</td><td>{{total_bad}}</td></tr><tr><td>Bad with info</td><td>{{total_bad_info}}</td></tr><tr><td>Bad with warning</td><td>{{total_bad_warning}}</td></tr><tr><td>Bad with error</td><td>{{total_bad_error}}</td></tr></table><div class=\"width:100%\" loading-container=\"tableParams.settings().$loading\"><table class=\"table admin-table-import-bad width:100%\" ng-table=\"tableParams\"><tr ng-repeat=\"logLine in $data\"><td data-title=\"'Line nb'\" style=\"width:12.5%\" sortable=\"'lineNb'\">{{logLine.lineNb}}</td><td data-title=\"'Code'\" style=\"width:12.5%\" sortable=\"'name'\">{{logLine.name}}</td><td data-title=\"'Info'\" style=\"width:25%\" sortable=\"'messagesInfoNb'\"><ul><li ng-repeat=\"message in logLine.messages['INFO']\">{{message}}</li></ul></td><td data-title=\"'Warinig'\" style=\"width:25%\" sortable=\"'messagesWarningNb'\"><ul><li ng-repeat=\"message in logLine.messages['WARNING']\">{{message}}</li></ul></td><td data-title=\"'Error'\" style=\"width:25%\" sortable=\"'messagesErrorNb'\"><ul><li ng-repeat=\"message in logLine.messages['ERROR']\">{{message}}</li></ul></td></tr></table></div></div></div></tab><tab class=\"tab-color-lightgreen\"><tab-heading style=\"margin-left:25px\"><span>Questions (Erros :  {{total_question_error}} )</span></tab-heading><div style=\"border-top : 1px solid black\"><div class=\"element_table\"><table class=\"table\" style=\"width: 200px;\"><tr><td>Total Question with data imported</td><td>{{total_question}}</td></tr><tr><td>question with info</td><td>{{total_question_info}}</td></tr><tr><td>question with warning</td><td>{{total_question_warning}}</td></tr><tr><td>question with error</td><td>{{total_question_error}}</td></tr></table><div class=\"width:100%\" loading-container=\"tableParams2.settings().$loading\"><table class=\"table admin-table-import-bad width:100%\" ng-table=\"tableParams2\"><tr ng-repeat=\"logLine in $data\"><td data-title=\"'Line nb'\" style=\"width:12.5%\" sortable=\"'lineNb'\">{{logLine.lineNb}}</td><td data-title=\"'Code'\" style=\"width:12.5%\" sortable=\"'name'\">{{logLine.name}}</td><td data-title=\"'Info'\" style=\"width:25%\" sortable=\"'messagesInfoNb'\"><ul><li ng-repeat=\"message in logLine.messages['INFO']\">{{message}}</li></ul></td><td data-title=\"'Warinig'\" style=\"width:25%\" sortable=\"'messagesWarningNb'\"><ul><li ng-repeat=\"message in logLine.messages['WARNING']\">{{message}}</li></ul></td><td data-title=\"'Error'\" style=\"width:25%\" sortable=\"'messagesErrorNb'\"><ul><li ng-repeat=\"message in logLine.messages['ERROR']\">{{message}}</li></ul></td></tr></table></div></div></div></tab></tabset></div>");});