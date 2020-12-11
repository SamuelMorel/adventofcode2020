const fs = require('fs');
const readline = require('readline');

const fileName = 'input.txt';

// create an interface to stream lines
const rl = readline.createInterface({
    input: fs.createReadStream(fileName),
    crlfDelay: Infinity
});

// prerequisite
const requieredFields = new Map([
    ['byr', {min: 1920, max: 2002, regex: '^[0-9]{4}$'}],
    ['iyr', {min: 2010, max: 2020, regex: '^[0-9]{4}$'}],
    ['eyr', {min: 2020, max: 2030, regex: '^[0-9]{4}$'}],
    ['hgt', {validator: validateHeight}],
    ['hcl', {regex: '^#[0-9a-f]{6}$'}],
    ['ecl', {regex: '^(amb|blu|brn|gry|grn|hzl|oth)$'}],
    ['pid', {regex: '^[0-9]{9}$'}]
]);

// init result count
var validPassports = 0;

// init passport with empty string
let passport = '';

// read each line of the input file
rl.on('line', (line) => {
    // if line is empty, this is the end of previous passport
    if (line.length === 0) {
        validPassports += processPassort(passport);
        // reinit current passport
        passport = '';
    } else {
        // we are in a middle of a passport
        passport +=`${line} `;
    }
}).addListener('close', () => { // on closing the file
    validPassports += processPassort(passport);
    // print the result
    console.log(validPassports)
});

/**
 * Validate the passport against 
 * 
 * @param {*} passport 
 */
function processPassort(passport) {
    // create a map from passport input
    let tuples = new Map(passport
        // split on space
        .split(' ')
        // split on column
        .map(tuple=> tuple.split(':'))
    );

    // iterate over requiered fields
     for (let [field, rule] of requieredFields) {
        let fieldValue = tuples.get(field);
        // test the field validity
        if(fieldValue === undefined 
            || (rule.validator && !rule.validator(fieldValue))
            || (rule.min && rule.min > fieldValue)
            || (rule.max && rule.max < fieldValue)
            || (rule.regex && !fieldValue.match(new RegExp(rule.regex, 'g')))) {
            // one of the rule is broken
            return false;
        }
    };
    // all rules are ok !
    return true;
}


/**
 * Method to validate height
 * 
 * @param {*} inputHeight the input to test
 */
function validateHeight(inputHeight) {
    const matches = /(?<height>[0-9]{2,3})(?<unit>(in|cm))/.exec(inputHeight);
    if (matches == null) {
        return false;
    }
    // retreive groups
    const { groups: { unit, height } } = matches;
    // check agains rules
    return (unit === 'cm' && height <= 193 && height >= 150)
        || (unit=== 'in' && height <= 76 && height >= 59)
}
