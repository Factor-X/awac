function validate(value, args) {
    var re = value.length >= parseInt(args.min) && value.length <= parseInt(args.max);
    return re;
}