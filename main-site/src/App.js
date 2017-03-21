import React, { Component } from 'react'
import { cyan500, cyan700, darkBlack, fullBlack, grey100, grey300, grey400, grey500, pinkA200, white } from 'material-ui/styles/colors'

import LandingPage from './landing/LandingPage'
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider'
import ThankYouPage from './landing/ThankYouPage'
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

function getUrlParams(search) {
    let hashes = search.slice(search.indexOf('?') + 1).split('&')
    let params = {}
    hashes.map(hash => {
        let [key, val] = hash.split('=');
        params[key] = decodeURIComponent(val);
        return "";
    })

    return params
}

const params = getUrlParams(window.location.search);

class App extends Component {
  render() {
    var component;
    if (!params.id) {
      component = <LandingPage />;
    } else {
      component = <ThankYouPage />;
    }

    return (
      <MuiThemeProvider muiTheme={muiTheme}>
        <div>
          {component}
        </div>
      </MuiThemeProvider>
    )
  }
}

export default App
