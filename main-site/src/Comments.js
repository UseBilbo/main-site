import React from 'react'

const leftDividerStyle = {
  display: 'block',
  border: 0,
  borderTop: '3px solid #000',
  margin: '1em 0',
  padding: 0,
  width: '70%',
  align: 'left'
}

const leftDividerStyle2 = {
  display: 'block',
  border: 0,
  borderTop: '3px solid #7F7FFF',
  margin: '1em 0',
  padding: 0,
  width: '70%',
  align: 'left'
}


const rightDividerStyle = {
  display: 'block',
  border: 0,
  borderTop: '3px solid #009F00',
  margin: '1em 0',
  padding: 0,
  width: '70%',
  align: 'right',
  textAlign: 'right',
  float: 'right',
}

const containerStyle = {
  backgroundColor: 'white',
  color: 'black',
  width: '100%',
  fontSize: '24px',
  lineHeight: '1.5em',
}

const leftContainerStyle = {
  align: 'left',
  textAlign: 'left',
  verticalAlign: 'center',
  width: '100%',
}

const rightContainerStyle = {
  textAlign: 'right',
  verticalAlign: 'center',
  width: '100%',
}

const titleStyle = {
  fontSize: '28px',
  verticalAlign:'center',
}

const cytingStyle = {
  padding: '30px',
  color: 'white',
  backgroundColor: 'gray',
  align: 'left',
  borderRadius: '15px',
  verticalAlign: 'center',
}
const authorStyle = {
  textAlign: 'right'
}

const pictureStyle = {
  verticalAlign: 'bottom',
}

const iconStyle = {
  verticalAlign: 'bottom',
}

const feedbackStyle = {
  textAlign: 'left',
  backgroundColor: 'cyan',
  borderSpacing: '20px',
}

const feedbackNameStyle = {
  textAlign: 'right',
}

const Comments = () => (
  <div style={containerStyle}>
    <hr style={leftDividerStyle} />
    <table style={leftContainerStyle}>
      <tbody>
      <tr>
        <td><img src='image/account-search.png' alt="" style={iconStyle}/></td>
        <td style={titleStyle}>Odbądź podróż po świecie<br/>Twoich znajomych</td>
        <td rowSpan='2'>
          <div style={cytingStyle}>
            "Czytanie książki jest, jak podróż po świecie drugiego człowieka. "<br/>
            <div style={authorStyle}>
              Jonathan Carroll
            </div>
          </div>
        </td>
      </tr>
      <tr>
        <td></td>
        <td>
          Widząc co czytają i jakie książki posiadają,
          jeszcze lepiej poznasz swoich znajomych
          i odkryjesz tytuły, które wiesz że chcesz przeczytać.
        </td>
      </tr>
      </tbody>
    </table>
    <hr style={rightDividerStyle} />
    <table style={rightContainerStyle}>
      <tbody>
      <tr>
        <td style={titleStyle}>Wzmacniaj więzi ze znajomymi</td>
        <td><img src='image/account-switch.png' alt="" style={iconStyle}/></td>
      </tr>
      <tr>
        <td>Lubimy pomagać nawet nieznajomym, ponieważ zawsze wtedy czujemy
            uczucie wdzięczności, które ma do nas osoba której pomogliśmy.
            Pożyczając książki naszym przyjaciołom, pokazujemy że nam na nich
            zależy, przez co i oni z radością będą chcieli pomóc i Tobie.</td>
        <td></td>
      </tr>
      </tbody>
    </table>
    <hr style={leftDividerStyle2} />
    <table style={leftContainerStyle}>
      <tbody>
      <tr>
        <td><img src='image/buffer.png' alt="" style={iconStyle}/></td>
        <td style={titleStyle}>Odkryj na nowo, przeczytane przez Ciebie książki</td>
        <td rowSpan="2" style={pictureStyle}><img src='image/photo-reading-guy.png' width="400px" height="300px" alt=""/></td>
      </tr>
      <tr>
        <td></td>
        <td>Dzięki temu że Ty i twój znajomy przeczytaliście tę samą książkę
            i o tym wiecie, na pewno będzie to okazja dla Was do rozmowy o niej.
            Ciekawe refleksje i zobaczenie książki z perspektywy innej osoby, to
            coś dzięki czemu nie tylko zawsze będziesz mieć pasjonujący wspólny
            temat ze znajomymi, ale także poznasz swoje książki zupełnie od nowa.
        </td>
      </tr>
      </tbody>
    </table>
    <div style={leftContainerStyle}>
      <span><img src='image/account-multiple-plus.png' alt="" style={iconStyle}/>&nbsp;<span style={titleStyle}>Poznawaj nowe osoby</span></span>
    </div>
    <div style={leftContainerStyle}>
      <span><img src='image/calendar-multiple-check.png' alt="" style={iconStyle}/>&nbsp;<span style={titleStyle}>Zawsze pamiętaj co komu pożyczyłeś/aś</span></span>
    </div>
    <table style={feedbackStyle}>
      <tbody>
      <tr>
        <td>
        "Jeśli tylko coś takiego wejdzie w życie będę zachwycona. Umożliwi to poszerzenie dostępności 
        książek w krótkim czasie. Jestem zachwycona"
        </td>
        <td>
        "Bardzo ciekawe pomysł i myślę, że dzięki tej usłudze sięgałabym po książki częściej 
        niż dotychczas. Jest tyle książek, że nigdy nie wiem co jest ciekawe, co ktoś
        może mi polecić, gdzie mogę to znaleźć"
        </td>
        <td>
        "Mysle ze to fajny pomysl. Ja prawie nie pozyczam ksiazek od kogos i komus 
        poniewaz nie znamy na wzajem swoich zbiorow. "
        </td>
      </tr>
      <tr style={feedbackNameStyle}>
        <td>Natalia</td>
        <td>Aleksandra</td>
        <td>Kinga</td>
      </tr>
      </tbody>
    </table>
  </div>
)

export default Comments
