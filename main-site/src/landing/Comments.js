import React from 'react'

const leftDividerStyle = {
  display: 'block',
  border: 0,
  borderTop: '3px solid #6e323d',
  margin: '1em 0',
  padding: 0,
  width: '70%',
  align: 'left'
}

const leftDividerStyle2 = {
  display: 'block',
  border: 0,
  borderTop: '3px solid #7ed9e3',
  margin: '1em 0',
  padding: 0,
  width: '70%',
  align: 'left'
}

const rightDividerStyle = {
  display: 'block',
  border: 0,
  borderTop: '3px solid #eb955b',
  margin: '1em 0',
  padding: 0,
  width: '75%',
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
  borderSpacing: '30px 10px',
}

const rightContainerStyle = {
  textAlign: 'right',
  verticalAlign: 'center',
  width: '100%',
  borderSpacing: '30px 10px',
}

const titleStyle = {
  fontSize: '28px',
  verticalAlign:'center',
}

const aloneTitleStyle = {
  fontSize: '28px',
  verticalAlign:'center',
  whiteSpace: 'nowrap',
}

const cytingStyle = {
  padding: '30px',
  color: 'white',
  backgroundColor: '#6e323d',
  align: 'left',
  borderRadius: '15px',
  verticalAlign: 'center',
}
const authorStyle = {
  textAlign: 'right'
}

const iconStyle = {
  verticalAlign: 'bottom',
}

const feedbackStyle = {
  textAlign: 'left',
  backgroundColor: '#f0eeed',
  color: '#00114f',
  borderSpacing: '40px',
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
          <div style={{width:'75%'}}>
          Widząc co czytają i jakie książki posiadają,
          jeszcze lepiej poznasz swoich znajomych
          i odkryjesz tytuły, które wiesz że chcesz przeczytać.
          </div>
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
        <td>
          <div style={{width:'75%', align: 'right', float:'right'}}>
            Lubimy pomagać nawet nieznajomym, ponieważ zawsze wtedy czujemy
            uczucie wdzięczności, które ma do nas osoba której pomogliśmy.
            Pożyczając książki naszym przyjaciołom, pokazujemy że nam na nich
            zależy, przez co i oni z radością będą chcieli pomóc i Tobie.
          </div>
        </td>

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
      </tr>
      <tr>
        <td></td>
        <td style={{verticalAlign:'top'}}>Dzięki temu że Ty i twój znajomy przeczytaliście tę samą książkę
            i o tym wiecie, na pewno będzie to okazja dla Was do rozmowy o niej.
            Ciekawe refleksje i zobaczenie książki z perspektywy innej osoby, to
            coś dzięki czemu nie tylko zawsze będziesz mieć pasjonujący wspólny
            temat ze znajomymi, ale także poznasz swoje książki zupełnie od nowa.
        </td>
        <td style={{width:'30%'}}>&nbsp;</td>
      </tr>
      </tbody>
    </table>
    <table style={leftContainerStyle}>
      <tbody>
      <tr>
        <td><img src='image/account-multiple-plus.png' alt="" style={iconStyle}/></td>
        <td style={aloneTitleStyle}>Poznawaj nowe osoby</td>
        <td style={{width: '90%'}}>
          &nbsp;
        </td>
      </tr>
      </tbody>
    </table>
    <table style={leftContainerStyle}>
      <tbody>
      <tr>
        <td><img src='image/calendar-multiple-check.png' alt="" style={iconStyle}/></td>
        <td style={aloneTitleStyle}>Zawsze pamiętaj co komu pożyczyłeś/aś</td>
        <td style={{width: '90%'}}>
          &nbsp;
        </td>
      </tr>
      </tbody>
    </table>
    <br />
    <br />
    <table style={feedbackStyle}>
      <tbody>
      <tr>
        <td style={{verticalAlign: 'top'}}>
        "Jeśli tylko coś takiego wejdzie w życie będę zachwycona. Umożliwi to poszerzenie dostępności 
        książek w krótkim czasie. Jestem zachwycona"
        </td>
        <td style={{width: '30%', verticalAlign: 'top'}}>
        "Bardzo ciekawe pomysł i myślę, że dzięki tej usłudze sięgałabym po książki częściej 
        niż dotychczas. Jest tyle książek, że nigdy nie wiem co jest ciekawe, co ktoś
        może mi polecić, gdzie mogę to znaleźć"
        </td>
        <td style={{verticalAlign: 'top'}}>
        "Mysle ze to fajny pomysl. Ja prawie nie pozyczam ksiazek od kogos i komus 
        poniewaz nie znamy na wzajem swoich zbiorow. "
        </td>
      </tr>
      <tr style={feedbackNameStyle}>
        <td>Natalia&nbsp;&nbsp;&nbsp;</td>
        <td>Aleksandra&nbsp;</td>
        <td>Kinga&nbsp;</td>
      </tr>
      </tbody>
    </table>
  </div>
)

export default Comments
