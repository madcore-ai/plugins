pipelineJob('madcore.plugin.pdfkit.deploy') {
    parameters {
      stringParam('APP_NAME', 'pdfkit', '')
      stringParam('SERVICE_PORT', '9019', '')
      stringParam('S3BUCKETNAME', '', 'S3 bucket name for backup')
      booleanParam('MADCORE_INGRESS_FLAG', false, 'ingress flag ')
      stringParam('APP_NAMESPACE', 'pdfkit-plugin', 'Plugin namespase')
      stringParam('APP_SERVICE_NAME', 'pdfkit-service', 'Plugin service name')

    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Kubernetes: create') {
                  build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'pdfkit/kub')]
                }
                stage ('Update app base') {
                  build job: 'madcore.redis.app.update', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_PORT', value: params.SERVICE_PORT), string(name: 'APP_NAMESPACE', value: params.APP_NAMESPACE), string(name: 'APP_SERVICE_NAME', value: params.APP_SERVICE_NAME) ]
                }
                stage ('Update CSR') {
                  build job: 'madcore.ssl.csr.generate'
                }
                stage ('Update certificate and haproxy') {
                  build job: 'madcore.ssl.letsencrypt.getandinstall', parameters: [string(name: 'S3BucketName', value: params.S3BUCKETNAME)]
                }
                stage ('add to ingress controller') {
                  if (params.MADCORE_INGRESS_FLAG == true) {
                    build job: 'madcore.plugin.ingress.add.service', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'SERVICE_NAME', value: params.APP_SERVICE_NAME), string(name: 'SERVICE_PORT', value: '9019'), string(name: 'SERVICE_NAMESPACE', value: params.APP_NAMESPACE ) ]
                  }
                  else {println "not need add to ingress controller"}
                }
              }
            """.stripIndent())
	    }
    }
}
