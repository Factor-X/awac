function validate(value, args) {
    if(value == null)
        if(args.min > 0)
            return false;
        return true;
    var re = value.length >= parseInt(args.min) && value.length <= parseInt(args.max);
    return re;
}