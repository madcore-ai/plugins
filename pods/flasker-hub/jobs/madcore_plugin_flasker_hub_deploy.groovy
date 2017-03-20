pipelineJob('madcore.plugin.flasker-hub.deploy') {
    parameters {
      stringParam('APP_NAME', 'flasker-hub', '')
      stringParam('PORT', '9019', '')
      stringParam('S3BUCKETNAME', '', 'S3 bucket name for backup')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Kubernetes: create') {
                  build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'pods/flasker-hub/kub')]
                }
                stage ('Update app base') {
                  build job: 'madcore.redis.app.update', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'APP_PORT', value: params.PORT)]
                }
                stage ('Update CSR') {
                  build job: 'madcore.ssl.csr.generate'
                }
                stage ('Update certificate and haproxy') {
                  build job: 'madcore.ssl.letsencrypt.getandinstall', parameters: [string(name: 'S3BucketName', value: params.S3BUCKETNAME)]
                }
              }
            """.stripIndent())
	    }
    }
}
