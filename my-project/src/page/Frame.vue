<template>
  <iframe class="frame"  :src="this.srcMap[this.$route.params.type]"></iframe>
</template>

<script>
export default {
  data() {
    return {
      srcMap: {
        docsify: "/docsify.html",
        transmission: "http://2202.com:9091",
        homeAssistant: "http://2202.com:8123/",
        photoPrism: "http://2202.com:2342/",
        portainer: "http://2202.com:9000/",
        fileBrowser: "http://2202.com:8082/",
        openWrt: "http://192.168.1.1/cgi-bin/luci/"
      }
    }
  },
  name: "Frame",
  methods: {

  }
}
</script>

<style scoped>
.frame {
  height: 100%;
  width: 100%;
  border: 0;
  padding: 0;
}
</style>