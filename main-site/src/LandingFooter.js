import React from 'react'

const mainStyle = {
  backgroundColor: '#4F4F4F',
  width: '100%',
  height: '60px',
  verticalAlignment: 'middle',
  lineHeight: '60px',
  color: 'white',
  whiteSpace: 'nowrap',
  fontSize: '18px',
}

const leftStyle = {
  textAlign: 'left',
  float: 'left',
  verticalAlignment: 'middle',
}

const rightStyle = {
  textAlign: 'right',
  float: 'right',
  verticalAlignment: 'middle',
}

const imgStyle = {
  height: '40px',
  margin: 'auto',
  verticalAlign: 'middle'
}

const LandingFooter = () => (
  <div style={mainStyle}>
    <div style={leftStyle}>&nbsp;&nbsp;Made with <font color='red'>❤</font> in Krakow.</div>
    <div style={rightStyle}>Copyrights © 2017. usebilbo.com . All rights reserved.&nbsp;&nbsp;
      <img style={imgStyle} src='image/fb-logo-blue-50.png' alt='FB Logo' />&nbsp;&nbsp;
    </div>
  </div>
)

export default LandingFooter
