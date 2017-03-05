import React, { Component } from 'react'
import { cyan100, cyan200, cyan500, cyan700, darkBlack, fullBlack, grey100, grey300, grey400, grey500, grey800, pinkA200, white } from 'material-ui/styles/colors'

import AppBar from 'material-ui/AppBar'
import Divider from 'material-ui/Divider'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import Paper from 'material-ui/Paper'
import { Parallax } from 'react-parallax'
import { fade } from 'material-ui/utils/colorManipulator'
import getMuiTheme from 'material-ui/styles/getMuiTheme'
import spacing from 'material-ui/styles/spacing'

const muiTheme = getMuiTheme({
  palette: {
    primary1Color: cyan500,
    primary2Color: cyan700,
    primary3Color: grey400,
    accent1Color: pinkA200,
    accent2Color: grey100,
    accent3Color: grey500,
    textColor: darkBlack,
    alternateTextColor: white,
    canvasColor: white,
    borderColor: grey300,
    disabledColor: fade(darkBlack, 0.3),
    pickerHeaderColor: cyan500,
    clockCircleColor: fade(darkBlack, 0.07),
    shadowColor: fullBlack
  }
})

const appBarStyle = {
  backgroundColor: grey100,
  color: darkBlack,
  textAlign: 'left'
}

const style = {
  width: '90%',
  margin: 20,
  textAlign: 'right',
  //color: white
}

const carrolStyle = {
  width: '30%',
  float: 'right',
  margin: 20,
  backgroundColor: grey800,
  color: white,
}

const leftSubheaderStyle = {
  color: darkBlack,
  textAlign: 'left',
//  fontSize: '16px'
}

const leftTextStyle = {
  color: darkBlack,
  textAlign: 'left',
//  fontSize: '14px',
}

const rightSubheaderStyle = {
  color: darkBlack,
  textAlign: 'right',
//  fontSize: '16px'
}

const rightTextStyle = {
  color: darkBlack,
  textAlign: 'right'
}

const commentBoxStyle = {
  backgroundColor: cyan100,
  overflow: 'auto',
  width: '100%',
}

const commentStyle = {
  width: '33%',
  textAlign: 'left',
  float: 'left',
}

const commentNameStyle = {
  textAlign: 'right',
}

const labelStyle = {
  textAlign: 'center',
  width: '100px',
  backgroundColor: grey800,
  color: white,
  margin: 20,
  float: 'right',
  fontWeight: 'bold',
}

const mainMessageStyle = {
  fontSize: 'larger',
  fontWeight: 'bold',
  margin: 50,
  width: '30%',
  float: 'right',
  clear: 'right',
  color: white,
}

const boxStyle = {
  clear: 'both',
  float: 'none',
}

const commentInset = {
  margin: 20,
}

const topStyle = {
  display: 'block',
  color: darkBlack,
  backgroundImage: 'url(image/photo-man-with-book.jpeg)',

}

class App extends Component {
  render () {
    return (
      <MuiThemeProvider muiTheme={muiTheme}>
        <div>
          <Paper style={style}>

            <Paper style={appBarStyle} zDepth={1}>
              <img src='image/bilbo-logo-dark.png' height="56px" alt="Bilbo Logo" />
            </Paper>
            <div style={topStyle}>
              <div style={labelStyle}>
                w tworzeniu!
              </div>

              <div style={mainMessageStyle}>
              Prosty sposób na pożyczanie książek od i swoim znajomym
              </div>
            </div>
            <Parallax bgImage='image/photo-man-with-book.jpeg' strength={300}>
            </Parallax>
            <br />
            <Divider inset={true} />
            <br />

            <div style={boxStyle}>
              <div style={leftSubheaderStyle}>
                Odbądź podróż po świecie Twoich znajomych
              </div>

              <div style={leftTextStyle}>
                Widząc co czytają i jakie książki posiadają, jeszcze lepiej poznasz swoich znajomych 
                i odkryjesz tytuły, które wiesz że chcesz przeczytać.
              </div>
              <div style={carrolStyle}>
                <div>
                  "Czytanie książki jest, jak podróż po świecie drugiego człowieka. "
                </div>
                <div>
                  Jonathan Carroll
                </div>
              </div>
            </div>

            <br />
            <Divider inset={true} />
            <br />
            <div style={rightSubheaderStyle}>
              Wzmacniaj więzi ze znajomymi
            </div>
            <div style={rightTextStyle}>
              Lubimy pomagać nawet nieznajomym, ponieważ zawsze wtedy czujemy uczucie wdzięczności, które ma do nas osoba której pomogliśmy. Pożyczając książki naszym przyjaciołom,
              pokazujemy że nam na nich zależy, przez co i oni z radością będą chcieli pomóc i Tobie.
            </div>
            <br />
            <Divider inset={true} />
            <br />
            <div style={leftSubheaderStyle}>
              Odkryj na nowo, przeczytane przez Ciebie książki
            </div>
            <div style={leftTextStyle}>
              Dzięki temu że Ty i twój znajomy przeczytaliście tę samą książkę i o tym wiecie, na pewno będzie to okazja dla Was do rozmowy o niej. Ciekawe refleksje i zobaczenie
              książki z perspektywy innej osoby, to coś dzięki czemu nie tylko zawsze będziesz mieć pasjonujący wspólny temat ze znajomymi, ale także poznasz swoje książki
              zupełnie od nowa.
            </div>
            <div style={leftSubheaderStyle}>
              Poznawaj nowe osoby
            </div>
            <div style={leftSubheaderStyle}>
              Zawsze pamiętaj co komu pożyczyłeś/aś
            </div>
            <div style={commentBoxStyle}>
              <div style={commentStyle}>
                <div style={commentInset}>
                  "Jeśli tylko coś takiego wejdzie w życie będę zachwycona. Umożliwi to poszerzenie dostępności książek w krótkim czasie. Jestem zachwycona"
                  <div style={commentNameStyle}>
                    Natalia
                  </div>
                </div>
              </div>
              <div style={commentStyle}>
                <div style={commentInset}>
                  "Bardzo ciekawe pomysł i myślę, że dzięki tej usłudze sięgałabym po książki częściej niż dotychczas. Jest tyle książek, że nigdy nie wiem co jest ciekawe, co ktoś
                  może mi polecić, gdzie mogę to znaleźć"
                  <div style={commentNameStyle}>
                    Aleksandra
                  </div>
                </div>
              </div>
              <div style={commentStyle}>
                <div style={commentInset}>
                  "Mysle ze to fajny pomysl. Ja prawie nie pozyczam ksiazek od kogos i komus poniewaz nie znamy na wzajem swoich zbiorow. "
                  <div style={commentNameStyle}>
                    Kinga
                  </div>
                </div>
              </div>
            </div>
            <Paper>
              <div>
                Zapisz się teraz, a jak tylko wystartujemy, to dostaniesz od nas w nim coś fajnego - niespodziankę :)
              </div>
            </Paper>
          </Paper>
        </div>
      </MuiThemeProvider>
    )
  }
}

export default App
