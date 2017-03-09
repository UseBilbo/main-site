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

const heartStyle = {
  fontSize: '24px',
  color: 'red',
}

const LandingFooter = () => (
  <div style={mainStyle}>
    <div style={leftStyle}>&nbsp;&nbsp;Made with <span style={heartStyle}>❤</span> in Krakow.</div>
    <div style={rightStyle}>Copyrights © 2017. usebilbo.com . All rights reserved.&nbsp;&nbsp;
      <a href="https://www.facebook.com/groups/400811693627030/" target="_blank"><img style={imgStyle} src='image/fb-logo-blue-50.png' alt='FB Logo'/></a>&nbsp;&nbsp;
    </div>
  </div>
)

export default LandingFooter
