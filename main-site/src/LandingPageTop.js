import AppBar from 'material-ui/AppBar'
import React from 'react'

const appBarStyle = {
  backgroundColor: 'white'
}

const titleStyle = {
  textAlign: 'right',
  color: 'black',
  fontSize: '24px',
}

const LandingPageTop = () => (
  <AppBar style={appBarStyle}
  iconElementLeft={<img src='image/bilbo-logo-dark.png' height="48px" alt="Bilbo Logo" />}
  title="Dołącz do grupy na facebooku >&nbsp;&nbsp;"
  titleStyle={titleStyle}
  iconElementRight={<img src='image/fb-logo-blue-50.png' height="48px" alt="FB Logo" />}
  zDepth={0}
  />
);

export default LandingPageTop;