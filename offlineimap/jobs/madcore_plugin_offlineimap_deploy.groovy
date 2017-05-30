pipelineJob('madcore.plugin.offlineimap.deploy') {
    parameters {
      stringParam('APP_NAME', 'offlineimap', '')
      stringParam('APP_NAMESPACE', 'offlineimap-plugin', 'Plugin namespase')
      stringParam('ACCOUNT', 'mysecret', '')
      stringParam('DOCKER_IMAGE', 'madcore/offlineimap', '')


    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage ('Generate rc yaml') {
                  build job: 'madcore.plugin.offlineimap.render.template', parameters: [
                    string(name: 'APP_NAME', value: params.APP_NAME),
                    string(name: 'ACCOUNT', value: params.ACCOUNT),
                    string(name: 'DOCKER_IMAGE', value: params.DOCKER_IMAGE)
                    ]
                }
                stage ('Kubernetes: create') {
                  build job: 'madcore.kubectl.create', parameters: [string(name: 'FILENAME', value: 'offlineimap/kub')]
                }
              }
            """.stripIndent())
	    }
    }
}
