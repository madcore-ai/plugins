pipelineJob('madcore.plugin.kafka.deploy') {
    parameters {
      stringParam('CHART', 'kafka/helm', '')
      stringParam('RELEASE_NAME', 'kafka', '')
      stringParam('NAMESPACE', 'kafka', '')
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
              }
            """.stripIndent())
	    }
    }
}
