function validate(value, args) {
    var re = value.length>=args.min && value.length <= args.max;
    return re;
}