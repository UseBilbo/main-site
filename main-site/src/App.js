import React, { Component } from 'react'
import { cyan500, cyan700, darkBlack, fullBlack, grey100, grey300, grey400, grey500, pinkA200, white } from 'material-ui/styles/colors'

import Comments from './Comments'
import Invitation from './Invitation'
import LandingFooter from './LandingFooter'
import LandingPageTop from './LandingPageTop'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import RegisterForm from './RegisterForm'
import { fade } from 'material-ui/utils/colorManipulator'
import getMuiTheme from 'material-ui/styles/getMuiTheme'

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

class App extends Component {
  render () {
    return (
      <MuiThemeProvider muiTheme={muiTheme}>
        <div>
          <LandingPageTop />
          <Invitation />
          <Comments />
          <RegisterForm />
          <LandingFooter />
        </div>
      </MuiThemeProvider>
    )
  }
}

export default App
