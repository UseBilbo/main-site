import Paper from 'material-ui/Paper';
import React from 'react'

const leftDividerStyle = {
  display: 'block',
  border: 0,
  borderTop: '3px solid #000',
  margin: '1em 0',
  padding: 0,
  width: '50%',
  align: 'left'
}

const rightDividerStyle = {
  display: 'block',
  border: 0,
  borderTop: '3px solid #000',
  margin: '1em 0',
  padding: 0,
  width: '50%',
  align: 'right'
}

const containerStyle = {
  backgroundColor: 'white',
  color: 'black',
  width: '100%',
  fontSize: '24px',
  lineHeight: '1.5em',
  position: 'relative',
}

const leftContainerStyle = {
  align: 'left',
  verticalAlign: 'top',
  border: '1px solid black',
}

const rightContainerStyle = {
  align: 'right',
  verticalAlign: 'top',
  border: '1px solid black',
}

const titleStyle = {
  fontSize: '28px'
}

const cytingStyle = {
  padding: '30px',
  color: 'white',
  backgroundColor: 'gray',
  align: 'left',
  whiteSpace: 'nowrap',
  borderRadius: '15px',
}

//TODO: move styles to CSS, finish page

const Comments = () => (
  <div style={containerStyle}>
    <hr style={leftDividerStyle} />
    <table style={leftContainerStyle}>
      <tr>
        <td><img src='image/account-search.png' /></td>
        <td style={titleStyle}>Odbądź podróż po świecie<br/>Twoich znajomych</td>
        <td rowspan='2'>
          <div style={cytingStyle}>
            "Czytanie książki jest, jak podróż<br/>po świecie drugiego człowieka. "<br/>
            <div style={rightContainerStyle}>
              Jonathan Carroll
            </div>
          </div>
        </td>
      </tr>
      <tr>
        <td></td>
        <td>
          Widząc co czytają i jakie książki posiadają, <br />
          jeszcze lepiej poznasz swoich znajomych <br />
          i odkryjesz tytuły, które wiesz że chcesz przeczytać.
        </td>
      </tr>
    </table>
    <hr style={rightDividerStyle} />
    <table style={rightContainerStyle}>
      <tr>
        <td style={titleStyle}>Wzmacniaj więzi ze znajomymi</td>
        <td><img src='image/account-switch.png' alt=""/></td>
      </tr>
      <tr>
        <td>Lubimy pomagać nawet nieznajomym, ponieważ zawsze wtedy czujemy <br />
            uczucie wdzięczności, które ma do nas osoba której pomogliśmy.<br />
            Pożyczając książki naszym przyjaciołom, pokazujemy że nam na nich<br />
            zależy, przez co i oni z radością będą chcieli pomóc i Tobie.</td>
        <td></td>
      </tr>
    </table>
    <hr style={leftDividerStyle} />
    <table style={leftContainerStyle}>
      <tr>
        <td><img src='image/buffer.png' /></td>
        <td style={titleStyle}>Odkryj na nowo, przeczytane przez Ciebie książki</td>
        <td rowspan="2"><img src='image/photo-reading-guy.png' width="300px" alt=""/></td>
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
    </table>
  </div>
)

export default Comments
