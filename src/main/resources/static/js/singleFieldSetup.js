const timeLocalTransaction = document.getElementsByName("input-12")[0]
timeLocalTransaction.value = 'The current time will be used'
timeLocalTransaction.disabled = true;



// ------- fields that should not be shown -------

excludingFields = ['64'];

for (let i of excludingFields){
    const id = 'tr-' + i;
    const field = document.getElementById(id);
    field.remove();
}

