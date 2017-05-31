pipelineJob('madcore.plugin.kafka.deploy') {
    parameters {
      stringParam('CHART', 'kafka/helm', '')
      stringParam('RELEASE_NAME', 'kafka', '')
      stringParam('NAMESPACE', 'kafka', '')
      stringParam('SERVICE_PORT', '2181', '')
      stringParam('S3BUCKETNAME', '', 'S3 bucket name for backup')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Install Helm chart') {
                  build job: 'madcore.helm.install', parameters: [string(name: 'CHART', value: params.CHART), string(name: 'RELEASE_NAME', value: params.RELEASE_NAME), string(name: 'NAMESPACE', value: params.NAMESPACE)]
                }
                stage ('Update app base') {
                  APP_SERVICE_NAME = params.RELEASE_NAME + "zookeeper" + "-zk"
                  build job: 'madcore.redis.app.update', parameters: [string(name: 'APP_NAME', value: params.RELEASE_NAME), string(name: 'SERVICE_PORT', value: params.SERVICE_PORT), string(name: 'APP_NAMESPACE', value: params.NAMESPACE), string(name: 'APP_SERVICE_NAME', value: APP_SERVICE_NAME) ]
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
