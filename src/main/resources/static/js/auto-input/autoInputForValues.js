function setValue(e){
    const el = e.target;
    console.log(el);

    let value = el.textContent;


    let fieldId = el.parentNode.parentNode.id;

    let re = '-';
    fieldId = fieldId.split(re)[1]; // gets 22 from str like dropdown-22
    fieldId = 'input-' + fieldId; // build id of destination input
    console.log(fieldId);

    re = ' = ';
    value = value.split(re)[1]; // gets 20 from str like 'refund = 20'
    console.log(value);

    document.getElementsByName(fieldId)[0].value = value;

}

