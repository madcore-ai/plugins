pipelineJob('madcore.plugin.flasker.status') {
    parameters {
	    stringParam('APP_NAME', 'flasker', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                build job: 'madcore.docker.registry.status', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Kubernetes: status'
                build job: 'madcore.kubectl.describe', parameters: [string(name: 'FILENAME', value: 'flasker/kub')]
                }
            """.stripIndent())
	    }
    }
}
