function validate(value, args) {
    if(parseFloat(value)==null || parseFloat(value)==NaN)
        return false;
    return parseFloat(value)>=parseInt(args.min) && parseFloat(value)<=parseInt(args.max);
}