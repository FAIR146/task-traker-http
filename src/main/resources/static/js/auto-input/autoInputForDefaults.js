function input1200() {
    document.getElementsByName("input-2")[0].value = '2201382000000013';
    document.getElementsByName("input-3")[0].value = '930000';
    document.getElementsByName("input-4")[0].value = '345';
    document.getElementsByName("input-11")[0].value = '890999';
    document.getElementsByName("input-12")[0].value = 'now';
    document.getElementsByName("input-14")[0].value = '2609';
    document.getElementsByName("input-22")[0].value = '810';
    document.getElementsByName("input-25")[0].value = '59';
    document.getElementsByName("input-28")[0].value = 'D00000001000';
    document.getElementsByName("input-41")[0].value = '1183';
    document.getElementsByName("input-42")[0].value = '11866611183';
    document.getElementsByName("input-49")[0].value = '643';
}

function defaultHandler(e){
    const messageType = document.getElementsByName("messageType")[0].value;

    switch(messageType) {
        case '1200':
            input1200();
            break;
        default:
            break;
    }
}

const defaultButtons = document.getElementsByName("default");

console.log(defaultButtons);

for (let i = 0; i < defaultButtons.length; i++) {
    defaultButtons[i].addEventListener('click', defaultHandler)
}