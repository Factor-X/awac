# == DEPENDENCIES =========================================================== #

# == CODE =================================================================== #

class Bad

    attr_accessor :key,
                  :name,
                  :method,
                  :rank,
                  :specificPurpose,
                  :activityCatKey,
                  :activityCat,
                  :activitySubCatKey,
                  :activitySubCat,
                  :activityTypeKey,
                  :activityType,
                  :activitySourceKey,
                  :activitySource,
                  :activityOwner,
                  :unit,
                  :value


    def initialize(key,
                   name,
                   method,
                   rank,
                   specificPurpose,
                   activityCatKey,
                   activityCat,
                   activitySubCatKey,
                   activitySubCat,
                   activityTypeKey,
                   activityType,
                   activitySourceKey,
                   activitySource,
                   activityOwner,
                   unit,
                   value)


        @key               = key
        @name              = name
        @method            = method
        @rank              = rank
        @specificPurpose   = specificPurpose
        @activityCatKey    = activityCatKey
        @activityCat       = activityCat
        @activitySubCatKey = activitySubCatKey
        @activitySubCat    = activitySubCat
        @activityTypeKey   = activityTypeKey
        @activityType      = activityType
        @activitySourceKey = activitySourceKey
        @activitySource    = activitySource
        @activityOwner     = activityOwner
        @unit              = unit
        @value             = value


    end

    def toString
        return "BAD["+
            "key:"+(@key != nil ? @key.to_s : "null") +
            ",name:"+(@name != nil ? @name.to_s : "null") +
            ",method:"+(@method != nil ? @method.to_s : "null") +
            ",rank:"+(@rank != nil ? @rank.to_s : "null") +
            ",specificPurpose:"+(@specificPurpose != nil ? @specificPurpose.to_s : "null") +
            ",activityCatKey:"+(@activityCatKey != nil ? @activityCatKey.to_s : "null") +
            ",activityCat:"+(@activityCat != nil ? @activityCat.to_s : "null") +
            ",activitySubCatKey:"+(@activitySubCatKey != nil ? @activitySubCatKey.to_s : "null") +
            ",activitySubCat:"+(@activitySubCat != nil ? @activitySubCat.to_s : "null") +
            ",activityTypeKey:"+(@activityTypeKey != nil ? @activityTypeKey.to_s : "null") +
            ",activityType:"+(@activityType != nil ? @activityType.to_s : "null") +
            ",activitySourceKey:"+(@activitySourceKey != nil ? @activitySourceKey.to_s : "null") +
            ",activitySource:"+(@activitySource != nil ? @activitySource.to_s : "null") +
            ",activityOwner:"+(@activityOwner != nil ? @activityOwner.to_s : "null") +
            ",unit:"+(@unit != nil ? @unit.to_s : "null") +
            ",value:"+(@value != nil ? @value.to_s : "null") +
            "]"
    end


end