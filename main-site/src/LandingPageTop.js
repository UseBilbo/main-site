import AppBar from 'material-ui/AppBar'
import React from 'react'

const appBarStyle = {
  backgroundColor: 'white'
}

const titleStyle = {
  textAlign: 'right',
  color: 'black',
  fontSize: '20px',
  fontWeight: '500',
}

const LandingPageTop = () => (
  <AppBar style={appBarStyle}
  iconElementLeft={<img src='image/bilbo-logo-dark.png' height="48px" alt="Bilbo Logo" />}
  title="Dołącz do grupy na facebooku >&nbsp;&nbsp;"
  titleStyle={titleStyle}
  iconElementRight={<a href="https://www.facebook.com/groups/400811693627030/" target="_blank"><img src='image/fb-logo-blue-50.png' height="48px" alt="FB Logo" /></a>}
  zDepth={0}
  />
);

export default LandingPageTop;