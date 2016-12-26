pipelineJob('madcore.plugin.cuda.delete') {
    parameters {
	    stringParam('APP_NAME', 'cuda', '')
    }

    definition {
	    cps {
            sandbox()
            script("""
            node {
                stage 'Docker: registry delete'
                build job: 'madcore.docker.registry.delete', parameters: [string(name: 'APP_NAME', value: params.APP_NAME)]
                stage 'Helm: delete'
                build job: 'madcore.helm.delete', parameters: [string(name: 'RELEASE_NAME', value: params.APP_NAME)]
                }
            """.stripIndent())
	    }
    }
}
