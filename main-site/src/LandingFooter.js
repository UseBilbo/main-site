import React from 'react'

const mainStyle = {
  backgroundColor: '#454545',
  width: '100%',
  height: '60px',
  verticalAlignment: 'middle',
  lineHeight: '60px',
  whiteSpace: 'nowrap',
  fontSize: '16px',
  fontWeight: '500',
}

const leftStyle = {
  textAlign: 'left',
  float: 'left',
  verticalAlignment: 'middle',
  color: '#f0eeed',
}

const rightStyle = {
  textAlign: 'right',
  float: 'right',
  verticalAlignment: 'middle',
  color: '#f0eeed',
}

const imgStyle = {
  height: '40px',
  margin: 'auto',
  verticalAlign: 'middle'
}

const heartStyle = {
  fontSize: '24px',
  color: '#6e323d',
}

const LandingFooter = () => (
  <div style={mainStyle}>
    <div style={leftStyle}>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Made with &nbsp;<span style={heartStyle}>❤</span>&nbsp; in Krakow.</div>
    <div style={rightStyle}>Copyrights © 2017. usebilbo.com . All rights reserved.&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
      <a href="https://www.facebook.com/groups/400811693627030/" target="_blank"><img style={imgStyle} src='image/fb-logo-blue-50.png' alt='FB Logo'/></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    </div>
  </div>
)

export default LandingFooter
