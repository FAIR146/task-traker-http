function setCardsHandler(e) {
    const text = e.target.value;
    console.log(text);

    const re = ' / ';
    const list = text.split(re);

    console.log("list =", list)

    const pan = list[0];
    const expiration = list[1]

    document.getElementsByName("input-2")[0].value = pan;
    document.getElementsByName("input-14")[0].value = expiration;
    //TODO: добавить ввод cvc
}

const cardsButtons = document.getElementsByName("card");

for (let i = 0; i < cardsButtons.length; i++) {
    cardsButtons[i].addEventListener('click', setCardsHandler);
}

