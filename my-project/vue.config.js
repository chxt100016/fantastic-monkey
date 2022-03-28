module.exports = {
  // outputDir: 'dist',   //build输出目录
  // assetsDir: 'assets', //静态资源目录（js, css, img）
  // lintOnSave: false, //是否开启eslint
  devServer: {
    // open: true, //是否自动弹出浏览器页面
    // host: "localhost",
    // port: '8081',
    // https: false,   //是否使用https协议
    // hotOnly: false, //是否开启热更新
    // proxy: null,
    proxy: {
      '/alarm': {
        // target: 'http://2202.com', //API服务器的地址
        target: 'http://192.168.1.230:8888', //API服务器的地址
        ws: true,  //代理websockets
        changeOrigin: true, // 虚拟的站点需要更管origin
        pathRewrite: {   //重写路径 比如'/api/aaa/ccc'重写为'/aaa/ccc'
          // '^/ea55_api': ''
        }
      },
      '/kanban': {
        // target: 'http://2202.com', //API服务器的地址
        target: 'http://192.168.1.230:8888', //API服务器的地址
        ws: true,  //代理websockets
        changeOrigin: true, // 虚拟的站点需要更管origin
        pathRewrite: {   //重写路径 比如'/api/aaa/ccc'重写为'/aaa/ccc'
          // '^/ea55_api': ''
        }
      }
    },
  }
}
