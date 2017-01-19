pipelineJob('madcore.plugin.flasker-hub.deploy') {
    parameters {
      stringParam('APP_NAME', 'flasker-hub', '')
      stringParam('PORT', '9019', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Kubernetes: create') {
                  build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'flasker-hub/kub')]
                }
                stage ('Update app base') {
                  build job: 'madcore.redis.app.update', parameters: [string(name: 'APP_NAME', value: params.APP_NAME), string(name: 'APP_PORT', value: params.PORT)]
                }
                stage ('Update certificate and haproxy') {
                  build job: 'madcore.ssl.letsencrypt.getandinstall'
                }
              }
            """.stripIndent())
	    }
    }
}
