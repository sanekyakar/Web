<template>
  <div>
    <MHeader></MHeader>
    <Nuxt />
    <MFooter></MFooter>
  </div>
</template>

<style>
html {
  font-family:
    'Source Sans Pro',
    -apple-system,
    BlinkMacSystemFont,
    'Segoe UI',
    Roboto,
    'Helvetica Neue',
    Arial,
    sans-serif;
  font-size: 16px;
  word-spacing: 1px;
  -ms-text-size-adjust: 100%;
  -webkit-text-size-adjust: 100%;
  -moz-osx-font-smoothing: grayscale;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
}

*,
*::before,
*::after {
  box-sizing: border-box;
  margin: 0;
}

.background {
  background-image: url("http://localhost:3001/img/back.png");
  background-color: black;
  background-size: auto 100%;
  background-position: center;
  padding-top: 40%;
  width: 100%;
  height: 100%;
  position: fixed;
  top: 0px;
  bottom: 0px;
  left: 0px;
  right: 0px;
  z-index: -100;
  background-repeat: no-repeat;

}

</style>
<script>
  import MHeader from "./Mheader";
  import MFooter from "./Mfooter";
  export default {
    components: {MFooter, MHeader}
  }
</script>
