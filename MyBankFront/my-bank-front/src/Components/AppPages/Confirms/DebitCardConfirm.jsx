import React from "react";

const DebitCardConfirm = (val) => {
    return(
        <div style={{color: "white", textAlign: "left", marginLeft:"2%", marginTop:"2%"}}>
            <h2>Дополнительная информация:</h2>
            <ul style={{marginTop:"5%"}}>
                <li>Стоимость обслуживания: 99 рублей.</li>
                <li>Кешбек на все покупки: 1%.</li>
            </ul>
        </div>
    )
}

export default DebitCardConfirm;