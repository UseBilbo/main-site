import AppBar from 'material-ui/AppBar'
import React from 'react'

const titleStyle = {
  textAlign: 'right',
  color: '#454545',
  fontSize: '20px',
  fontWeight: '500',
}

const tyTitleStyle = {
  textAlign: 'center',
  color: 'white', //'#a1b4b6'
  fontSize: '20px',
  fontWeight: '500',
}

const LandingTop = () => (
  <AppBar style={{backgroundColor: 'white'}}
  iconElementLeft={<img src='image/bilbo-logo-dark.png' height="48px" alt="Bilbo Logo" />}
  title="Dołącz do grupy na facebooku >&nbsp;&nbsp;"
  titleStyle={titleStyle}
  iconElementRight={<a href="https://www.facebook.com/groups/400811693627030/" target="_blank"><img src='image/fb-logo-blue-50.png' height="48px" alt="FB Logo" /></a>}
  zDepth={0}
  />
);
const TYTop = () => (
  <AppBar style={{backgroundColor: '#00114f'}}
  iconElementLeft={<img src='image/bilbo-logo-white.png' height="48px" alt="Bilbo Logo" />}
  title="DZIĘKUJEMY ZA ZAPISANIE SIĘ!"
  titleStyle={tyTitleStyle}
  zDepth={0}
  />
);

const LandingPageTop = (props) => (
  <div>
  {props.landing ? <LandingTop /> : <TYTop />}
  </div>
);

export default LandingPageTop;