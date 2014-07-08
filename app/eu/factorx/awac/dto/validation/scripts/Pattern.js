function validate(value, args) {
    var re = new RegExp(eval(args.regexp));
    return re.test(value);
}